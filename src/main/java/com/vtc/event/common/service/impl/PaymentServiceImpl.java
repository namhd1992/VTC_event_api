/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vtc.event.EnvironmentKey;
import com.vtc.event.common.Constant;
import com.vtc.event.common.MessageConstant;
import com.vtc.event.common.dao.entity.TransactionHistory;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.repository.TransactionHistoryRepository;
import com.vtc.event.common.dao.repository.UserVTCRepository;
import com.vtc.event.common.dto.request.ScoinExchangeRequest;
import com.vtc.event.common.dto.request.XuExchangeRequest;
import com.vtc.event.common.dto.response.ScoinBalanceResponse;
import com.vtc.event.common.dto.response.ScoinBasicResponse;
import com.vtc.event.common.dto.response.ScoinExchangeBasicResponse;
import com.vtc.event.common.dto.response.UserXuInfoResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.exception.ScoinUnAuthorizationException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.service.PaymentService;
import com.vtc.event.common.utils.ApiExchangeServiceUtil;
import com.vtc.event.common.utils.StringUtils;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 14, 2019
 */
@Service("exchangeMoneyScoinService")
public class PaymentServiceImpl
        extends AbstractService<TransactionHistory, Long, TransactionHistoryRepository>
        implements PaymentService {
    
    @Autowired
    UserVTCRepository userVTCRepo;
    
    private String SCOIN_XU_API_URL;
    private String SCOIN_XU_API_KEY;
    private String SCOIN_XU_SECRET_KEY;
    private String SCOIN_BALANCE_SCOIN_URL;
    private String SCOIN_SCOIN_API_URL;
    private String SCOIN_SCOIN_API_KEY;
    private String SCOIN_SCOIN_SECRET_KEY;

    public PaymentServiceImpl(Environment env) {
        SCOIN_XU_API_URL = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_XU_API_URL.getKey());
        SCOIN_XU_API_KEY = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_XU_API_KEY.getKey());
        SCOIN_XU_SECRET_KEY = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_XU_SECRET_KEY.getKey());
        SCOIN_BALANCE_SCOIN_URL = env
                .getProperty(EnvironmentKey.SANDBOX_SCOIN_BALANCE_SCOIN_URL.getKey());
        SCOIN_SCOIN_API_URL = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_SCOIN_API_URL.getKey());
        SCOIN_SCOIN_API_KEY = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_SCOIN_API_KEY.getKey());
        SCOIN_SCOIN_SECRET_KEY = env.getProperty(EnvironmentKey.SANDBOX_SCOIN_SCOIN_SECRET_KEY.getKey());
    }
    
    @Override
    public long updateBalanceSoin(UserInfo userInfo) throws ScoinBusinessException {
        String url = SCOIN_BALANCE_SCOIN_URL + userInfo.getScoinToken();
        ScoinBasicResponse<ScoinBalanceResponse> getScoinBalanceResponse = ApiExchangeServiceUtil
                .get(url, new TypeReference<ScoinBasicResponse<ScoinBalanceResponse>>() {});
        if (ObjectUtils.isEmpty(getScoinBalanceResponse)
                || getScoinBalanceResponse.getCode() < 1) {
            throw new ScoinUnAuthorizationException("Don't get Scoin Balance access_token");
        }
        
        long balanceSoin = getScoinBalanceResponse.getData().getTotalGameBalance();
        userInfo.getUserVTC().setScoin(balanceSoin);
        userVTCRepo.save(userInfo.getUserVTC());
        
        return balanceSoin;
    }
    
    @Override
    public void exchangeScoin(UserInfo userInfo, String typeExchange, long amount, String description) {
        if (userInfo.getUserVTC().getScoinId() 
                != verifyAccessTokenUser().getUserVTC().getScoinId()) {
            throw new ScoinUnAuthorizationException();
        }
        
        if (amount == 0 || description.isBlank())
            throw new ScoinInvalidDataRequestException("Invalid data request Exchange Scoin");
        
        String urlScoinExchange = null;
        if (typeExchange.equals(Constant.SCOIN_TYPE_TOPUP)) {
            urlScoinExchange = SCOIN_SCOIN_API_URL + Constant.SCOIN_URL_TOPUP;
        } else if (typeExchange.equals(Constant.SCOIN_TYPE_DEDUCT)) {
            urlScoinExchange = SCOIN_SCOIN_API_URL + Constant.SCOIN_URL_DEDUCT;
            
        }
        
        Long unixTime = new Date().getTime();
        String sign = StringUtils.toMD5(SCOIN_SCOIN_API_KEY 
                                        + SCOIN_SCOIN_SECRET_KEY 
                                        + userInfo.getScoinToken() 
                                        + String.valueOf(amount) 
                                        + unixTime.toString());
        ScoinExchangeRequest scoinExchangeRequest = new ScoinExchangeRequest();
        scoinExchangeRequest.setApiKey(SCOIN_SCOIN_API_KEY);
        scoinExchangeRequest.setAccessToken(userInfo.getScoinToken());
        scoinExchangeRequest.setDescription(description);
        scoinExchangeRequest.setAmount(amount);
        scoinExchangeRequest.setTime(unixTime);
        scoinExchangeRequest.setSign(sign);
        
        ScoinExchangeBasicResponse<Object> response = ApiExchangeServiceUtil. post(
                                    urlScoinExchange, 
                                    scoinExchangeRequest, 
                                    new TypeReference<ScoinExchangeBasicResponse<Object>>() {});
        if (ObjectUtils.isEmpty(response)
                || !response.isStatus()) {
            throw new ScoinFailedToExecuteException(response.getErrorDesc());
        }
    }

    @Override
    public UserXuInfoResponse getBalanceXu(Long scoinId) throws ScoinBusinessException {
        if (ObjectUtils.isEmpty(scoinId)) throw new ScoinInvalidDataRequestException();
        
        Long unixTime = new Date().getTime();
        String sign = StringUtils.toMD5(SCOIN_XU_SECRET_KEY + scoinId + unixTime);
        String url = SCOIN_XU_API_URL + "GetBalanceDt"
                     + "?api_key=" + SCOIN_XU_API_KEY 
                     + "&scoin_id=" + scoinId 
                     + "&time=" + unixTime
                     + "&sign=" + sign;

        ScoinExchangeBasicResponse<UserXuInfoResponse> response = ApiExchangeServiceUtil.get(url,
                new TypeReference<ScoinExchangeBasicResponse<UserXuInfoResponse>>() {});
        if (ObjectUtils.isEmpty(response)
                || !response.isStatus()) {
            throw new ScoinFailedToExecuteException(response.getErrorDesc());
        }
        return response.getData();
    }

    @Override
    public UserXuInfoResponse exchangeXu(XuExchangeRequest request, String type)
            throws ScoinBusinessException {
        if (StringUtils.isEmpty(type)
                || ObjectUtils.isEmpty(request)
                || ObjectUtils.isEmpty(request.getScoin_id())
                || ObjectUtils.isEmpty(request.getAmount())
                || StringUtils.isEmpty(request.getContent())
                || ObjectUtils.isEmpty(request.getTransid())) {
            return null;
        }

        Long unixTime = new Date().getTime();
        String token = request.getScoin_id() +"@" + unixTime.toString();
        String sign = StringUtils.toMD5(SCOIN_XU_SECRET_KEY 
                                        + request.getScoin_id()
                                        + request.getTransid()
                                        + request.getAmount()
                                        + token
                                        + unixTime);
        
        request.setApi_key(SCOIN_XU_API_KEY);
        request.setToken(token);
        request.setTime(unixTime);
        request.setSign(sign);
        String url = null;
        if (type.equals(Constant.XU_TOPUP)) url = SCOIN_XU_API_URL + "CDt";
        if (type.equals(Constant.XU_DEDUCT)) {
            UserXuInfoResponse balanceXU = getBalanceXu(request.getScoin_id());
            if (ObjectUtils.isEmpty(balanceXU)
                    && request.getAmount() > balanceXU.getTotalBalance()) {
                throw new ScoinInvalidDataRequestException(MessageConstant.INVALID_PARKAGE_XU);
            }
            url = SCOIN_XU_API_URL + "TDt";
        }
        
        if (StringUtils.isEmpty(type)) return new UserXuInfoResponse();
        
        ScoinExchangeBasicResponse<UserXuInfoResponse> response = ApiExchangeServiceUtil
            .post(url, request, new TypeReference<ScoinExchangeBasicResponse<UserXuInfoResponse>>() {});
        if (ObjectUtils.isEmpty(response)
                || !response.isStatus()) {
          throw new ScoinFailedToExecuteException(response.getErrorDesc());
        }
        return response.getData();
    }

}
