/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vtc.event.EnvironmentKey;
import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.FundsCardScoin;
import com.vtc.event.common.dao.repository.FundsCardScoinRepository;
import com.vtc.event.common.dao.repository.LuckySpinSettingRepository;
import com.vtc.event.common.dao.repository.TopupCardHistoryRepository;
import com.vtc.event.common.dto.request.ScoinCardXMLRequest;
import com.vtc.event.common.dto.response.BuyCardScoinResponse;
import com.vtc.event.common.dto.response.GetCardScoinResponse;
import com.vtc.event.common.dto.response.TopupCardHistoryResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.service.CommonCardScoinService;
import com.vtc.event.common.service.CommonUserInfoService;
import com.vtc.event.common.utils.ApiExchangeServiceUtil;
import com.vtc.event.common.utils.DateUtils;
import com.vtc.event.common.utils.EncryptAndDecryptUtils;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.common.utils.StringUtils;

/**
 * Author : Dat Le Quang
 * Email: Quan

import org.springframework.stereotype.Service;gdat0993@gmail.com
 * Jul 9, 2019
 */
@Service("commonCardScoinService")
public class CommonCardScoinServiceImpl extends
        AbstractService<FundsCardScoin, Long, FundsCardScoinRepository> implements CommonCardScoinService {
    
    @Autowired
    TopupCardHistoryRepository topupCardHistoryRepo;
    
    @Autowired
    CommonUserInfoService userInfoService;
    
    @Autowired
    LuckySpinSettingRepository luckySpinSettingRepo;
    
    private transient String CARD_API_URL;
    private transient String CARD_API_KEY_DECODE_TRIPLEDES;
    private transient String TOPUP_CARD_HISTORY_API_URL;
    private transient String TOPUP_CARD_HISTORY_API_KEY;
    
    public CommonCardScoinServiceImpl(Environment env) {
        CARD_API_URL = env.getProperty(EnvironmentKey.SANDBOX_FUND_REQUEST_CARD_API_URL.getKey());
        CARD_API_KEY_DECODE_TRIPLEDES = env
                .getProperty(EnvironmentKey.SANDBOX_CARD_API_KEY_DECODE_TRIPLEDES.getKey());
        TOPUP_CARD_HISTORY_API_URL = env.getProperty(EnvironmentKey.TOPUP_CARD_HISTORY_API_URL.getKey());
        TOPUP_CARD_HISTORY_API_KEY = env.getProperty(EnvironmentKey.TOPUP_CARD_HISTORY_API_KEY.getKey());
    }

    @Override
    public FundsCardScoin buyCard(String valueCard, Integer quantityCard) throws ScoinBusinessException {
        
        //Create requestData in request call api to scoin
        String urlprivatekey = Constant.PROJECT_RESOURCES + Constant.FILE_PRIVATE_KEY_SCOIN_CARD;
        String transDate = DateUtils.toStringFormDate(new Date(), DateUtils.DATE_TIME_CODE_SCOIN);
        
        String partnerCode = Constant.SCOIN_CARD_PARTNER_CODE;
        String serviceCode = Constant.SCOIN_CARD_SERVICE_CODE_SCOIN;
        String amount = valueCard;
        String quantity = quantityCard.toString();
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        String orgTransID = transDate + n;
        String DataSign = CreateSignRSAFileKeyPartner("" + serviceCode + 
                                                      "-" + amount + 
                                                      "-" + quantity + 
                                                      "-" + partnerCode + 
                                                      "-" + transDate + 
                                                      "-" + orgTransID, 
                                                      urlprivatekey);
        String requestData = createRequestDataBuyCard(serviceCode, "", amount, quantity, transDate, orgTransID, DataSign);
        
        //Create request of call api to Scoin 
        ScoinCardXMLRequest scoinCardXMLRequest = new ScoinCardXMLRequest();
        scoinCardXMLRequest.setRequesData(requestData);
        scoinCardXMLRequest.setPartnerCode(partnerCode);
        scoinCardXMLRequest.setCommandType(Constant.SCOIN_CARD_COMMAND_TYPE_BUYCARD);
        scoinCardXMLRequest.setVersion(Constant.SCOIN_CARD_VERSION);
        String requestCallApiBuyCard = createRequestScoinCardXML(scoinCardXMLRequest);
        
        //Call api buy card of Scoin
        String dataResponse = ApiExchangeServiceUtil.postXML(CARD_API_URL, 
                requestCallApiBuyCard);
        
        // convert string xml response to object
        String textStartResponse = Constant.SCOIN_CARD_TEXT_START_RESPONSE;
        int startIndex = dataResponse.indexOf(textStartResponse) + textStartResponse.length();
        int endIndex = dataResponse.indexOf(textStartResponse.replace("<", "</"));
        
        // Định dạng response : <ResponseCode>|<OrgTransID>|<VTCTransID>|<PartnerBalance>|<DataSign>
        String[] responseStrings = dataResponse.substring(startIndex, endIndex).split("[|]");
        if(!responseStrings[0].equals("1")) {
            throw new ScoinFailedToExecuteException("Call to api BUYCARD Scoin Unsuccess with repose code : " + responseStrings[0]);
        }
        
        // Convert Object from string xml response
        BuyCardScoinResponse response = new BuyCardScoinResponse();
        response.setResponseCode(responseStrings[0]);
        response.setOrgTransId(responseStrings[1]);
        response.setVTCTransId(responseStrings[2]);
        response.setPartnerBalance(responseStrings[3]);
        response.setDataSign(responseStrings[4]);
        
        GetCardScoinResponse cardScoin = getCard(amount, response.getVTCTransId());
        
        // Save FundsCardScoin
        FundsCardScoin fundsCardScoin = new FundsCardScoin();
        fundsCardScoin.setFundsAccount(partnerCode);
        fundsCardScoin.setFundsBalance(Long.parseLong(response.getPartnerBalance()));
        fundsCardScoin.setOrgTransID(response.getOrgTransId());
        fundsCardScoin.setVtcTransID(response.getVTCTransId());
        fundsCardScoin.setDataSign(response.getDataSign());
        fundsCardScoin.setMainCodeCard(cardScoin.getMainCode());
        fundsCardScoin.setSeriCard(cardScoin.getSeri());
        fundsCardScoin.setExpirationDateCard(cardScoin.getExpirationDate());
        fundsCardScoin.setValueCard(Integer.parseInt(amount));
        fundsCardScoin.setStatus(Constant.STATUS_SUCCESS);
        fundsCardScoin = repo.save(fundsCardScoin);
        
        return fundsCardScoin;
    }
    
    @Override
    public GetCardScoinResponse getCard(String valueCard, String VTCTranID) throws ScoinBusinessException {
        String partnerCode = Constant.SCOIN_CARD_PARTNER_CODE;
        String serviceCode = Constant.SCOIN_CARD_SERVICE_CODE_SCOIN;
        String amount = valueCard;
        
        String dataSign = CreateSignRSAFileKeyPartner(serviceCode + 
                                            "-" + amount + 
                                            "-" + partnerCode + 
                                            "-" + VTCTranID,
                Constant.PROJECT_RESOURCES + Constant.FILE_PRIVATE_KEY_SCOIN_CARD);
        String requestData = createRequestDataGetCard(serviceCode, amount, VTCTranID, dataSign);
        
        ScoinCardXMLRequest scoinCardXMLRequest = new ScoinCardXMLRequest();
        scoinCardXMLRequest.setRequesData(requestData);
        scoinCardXMLRequest.setPartnerCode(partnerCode);
        scoinCardXMLRequest.setCommandType(Constant.SCOIN_CARD_COMMAND_TYPE_GETCARD);
        scoinCardXMLRequest.setVersion(Constant.SCOIN_CARD_VERSION);
        String resquestCallApiXML = createRequestScoinCardXML(scoinCardXMLRequest);
        String dataResponse = ApiExchangeServiceUtil.postXML(CARD_API_URL, 
                                                 resquestCallApiXML);
        // convert string xml response to object
        String textStartResponse = Constant.SCOIN_CARD_TEXT_START_RESPONSE;
        int startIndex = dataResponse.indexOf(textStartResponse) + textStartResponse.length();
        int endIndex = dataResponse.indexOf(textStartResponse.replace("<", "</"));
        String responseString = dataResponse.substring(startIndex, endIndex);
        String response = null;
        try {
            //Decrypt response tripleDES
            response = EncryptAndDecryptUtils.tripleDESDecrypt(CARD_API_KEY_DECODE_TRIPLEDES,responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Đinh dạng response : <ResponseCode>|<OrgTranID|<ListCard>
        String[] responseStrings = response.split("[|]");
        
        if(!responseStrings[0].equals("1")) {
            throw new ScoinFailedToExecuteException("Call to api GETCARD Scoin Unsuccess with repose code : " + responseStrings[0]);
        }
        
        // <ListCard> = <CardCode1>:<CardSerial1>:<ExpriceDate1>|<CardCoden>:<CardSerialn>:<ExpriceDaten>
        //Convert response to Object
        String [] listCardProperties = responseStrings[2].split("[:]");
        GetCardScoinResponse getCardScoinResponse = new GetCardScoinResponse();
        getCardScoinResponse.setMainCode(listCardProperties[0]);
        getCardScoinResponse.setSeri(listCardProperties[1]);
        getCardScoinResponse.setExpirationDate(DateUtils.toDateFromStr(listCardProperties[2], DateUtils.DATE_DEFAULT_FORMAT));
        
        return getCardScoinResponse;
    }
    
    @Override
    public void topupScoin(long valueScoin, String userName) throws ScoinBusinessException {
        String urlprivatekey = Constant.PROJECT_RESOURCES + Constant.FILE_PRIVATE_KEY_SCOIN_CARD;
        String transDate = DateUtils.toStringFormDate(new Date(), DateUtils.DATE_TIME_CODE_SCOIN);
        
        String partnerCode = Constant.SCOIN_CARD_PARTNER_CODE;
        String serviceCode = Constant.SCOIN_TOPUP_SERVICE_CODE_SCOIN;
        String account = userName;
        String amount = String.valueOf(valueScoin);
        String quantity = "1";
        
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        String orgTransID = transDate + n;
        
        String DataSign = CreateSignRSAFileKeyPartner("" + serviceCode +
                                                      "-" + account + 
                                                      "-" + amount + 
                                                      "-" + quantity + 
                                                      "-" + partnerCode + 
                                                      "-" + transDate + 
                                                      "-" + orgTransID, 
                                                      urlprivatekey);
        String requestData = createRequestDataBuyCard(serviceCode, account, amount, quantity, transDate, orgTransID, DataSign);
        
        //Create request of call api to Scoin 
        ScoinCardXMLRequest scoinCardXMLRequest = new ScoinCardXMLRequest();
        scoinCardXMLRequest.setRequesData(requestData);
        scoinCardXMLRequest.setPartnerCode(partnerCode);
        scoinCardXMLRequest.setCommandType(Constant.SCOIN_TOPUP_COMMAND_TYPE_BUYCARD);
        scoinCardXMLRequest.setVersion(Constant.SCOIN_CARD_VERSION);
        String requestCallApiBuyCard = createRequestScoinCardXML(scoinCardXMLRequest);
        
        //Call api buy card of Scoin
        String dataResponse = ApiExchangeServiceUtil.postXML(CARD_API_URL, 
                requestCallApiBuyCard);
        
        // convert string xml response to object
        String textStartResponse = Constant.SCOIN_CARD_TEXT_START_RESPONSE;
        int startIndex = dataResponse.indexOf(textStartResponse) + textStartResponse.length();
        int endIndex = dataResponse.indexOf(textStartResponse.replace("<", "</"));
        
        // Định dạng response : <ResponseCode>|<OrgTransID>|<VTCTransID>|<PartnerBalance>|<DataSign>
        String[] responseStrings = dataResponse.substring(startIndex, endIndex).split("[|]");
        LOGGER.info("responseStrings ============== : {}", JsonMapperUtils.toJson(responseStrings));
        if(!responseStrings[0].equals("1")) {
            throw new ScoinFailedToExecuteException("Call to api TOPUP Scoin Unsuccess with repoNse code : " + responseStrings[0]);
        }
        
        // Convert Object from string xml response
        BuyCardScoinResponse response = new BuyCardScoinResponse();
        response.setResponseCode(responseStrings[0]);
        response.setOrgTransId(responseStrings[1]);
        response.setVTCTransId(responseStrings[2]);
        response.setPartnerBalance(responseStrings[3]);
        response.setDataSign(responseStrings[4]);
        
    }
    
    @Override
    public List<TopupCardHistoryResponse> getTopupCardHistory(String scoinToken, Date topupDate, Long serviceId) {
        if (StringUtils.isEmpty(scoinToken)
                || ObjectUtils.isEmpty(topupDate)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        String url = TOPUP_CARD_HISTORY_API_URL
                + "?api_key=" + TOPUP_CARD_HISTORY_API_KEY
                + "&access_token=" + scoinToken
                + "&service_id=" + serviceId.toString()
                + "&date=" + DateUtils.toStringFormDate(topupDate, DateUtils.DATE_DEFAULT_SCOIN);
        List<TopupCardHistoryResponse> response = ApiExchangeServiceUtil.get(url,
                new TypeReference<List<TopupCardHistoryResponse>>() {});
        return response;
    }
    
//=================================COMPONENT======================================
    
    private String createRequestDataBuyCard(String ServiceCode,String account, String Amount,String Quantity,String TransDate,String OrgTransID,String DataSign){
        String accountRequest = "";
        accountRequest = (!account.isBlank()) ? "  &lt;Account&gt;" + account + "&lt;/Account&gt;\n" : "";
        String xmlre=  "&lt;?xml version=\"1.0\" encoding=\"utf-16\"?&gt;\n" +
                       "&lt;RequestData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"&gt;\n" +
                       "  &lt;ServiceCode&gt;"+ServiceCode+"&lt;/ServiceCode&gt;\n" +
                          accountRequest +
                       "  &lt;Amount&gt;"+Amount+"&lt;/Amount&gt;\n" +
                       "  &lt;Quantity&gt;"+Quantity+"&lt;/Quantity&gt;\n" +
                       "  &lt;TransDate&gt;"+TransDate+"&lt;/TransDate&gt;\n" +
                       "  &lt;OrgTransID&gt;"+OrgTransID+"&lt;/OrgTransID&gt;\n" +
                       "  &lt;DataSign&gt;"+DataSign+"&lt;/DataSign&gt;\n" +
                       "&lt;/RequestData&gt;";
           return xmlre;
    }
    
    public static  String createRequestDataGetCard(String ServiceCode, String Amount,String OrgTransID,String DataSign){
        try { 
        String xmlre=  "&lt;?xml version=\"1.0\" encoding=\"utf-16\"?&gt;\n" +
                       "&lt;RequestData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"&gt;\n" +
                       "  &lt;ServiceCode&gt;"+ServiceCode+"&lt;/ServiceCode&gt;\n" +
                       "  &lt;Amount&gt;"+Amount+"&lt;/Amount&gt;\n" +
                       "  &lt;OrgTransID&gt;"+OrgTransID+"&lt;/OrgTransID&gt;\n" +
                       "  &lt;DataSign&gt;"+DataSign+"&lt;/DataSign&gt;\n" +
                       "&lt;/RequestData&gt;";
           return xmlre;
       } catch (Exception ex) {
           return  ex.toString();
       }
    }
      
      private String createRequestScoinCardXML(ScoinCardXMLRequest request) {
          return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><RequestTransaction xmlns=\"http://tempuri.org/\"><requesData>" 
                      + request.getRequesData()
                      + "</requesData><partnerCode>" + request.getPartnerCode()
                      + "</partnerCode><commandType>" + request.getCommandType()
                      + "</commandType><version>" + request.getVersion()
                      + "</version></RequestTransaction></soap:Body></soap:Envelope>";
      }
    
    @SuppressWarnings("cast")
    public static String CreateSignRSAFileKeyPartner(String data, String filePath) {
        try {
            final File privKeyFile = new File(filePath);
            final byte[] privKeyBytes = readFile(privKeyFile);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKeyBytes);
            final PrivateKey pk = (PrivateKey) keyFactory.generatePrivate(privSpec);

            final Signature sg = Signature.getInstance("SHA1withRSA");

            sg.initSign(pk);
            sg.update(data.getBytes());
            final byte[] bDS = sg.sign();
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(bDS));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public static byte[] readFile(final File file)
            throws FileNotFoundException, IOException {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(file));
            final byte[] data = new byte[dis.available()];
            dis.readFully(data);
            dis.close();
            return data;
        } finally {
            if (dis != null) {
                dis.close();
            }
        }
    }

}
