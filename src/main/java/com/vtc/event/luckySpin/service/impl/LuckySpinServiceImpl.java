/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.FundsCardScoin;
import com.vtc.event.common.dao.entity.LuckyNumber;
import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinGift;
import com.vtc.event.common.dao.entity.LuckySpinHistory;
import com.vtc.event.common.dao.entity.LuckySpinItem;
import com.vtc.event.common.dao.entity.LuckySpinItemOfLuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinItemOfUser;
import com.vtc.event.common.dao.entity.LuckySpinRadiusPersonalTopup;
import com.vtc.event.common.dao.entity.LuckySpinSetting;
import com.vtc.event.common.dao.entity.LuckySpinUser;
import com.vtc.event.common.dao.entity.TopupCardHistory;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.repository.GiftcodeRepository;
import com.vtc.event.common.dao.repository.LuckyNumberRepository;
import com.vtc.event.common.dao.repository.LuckySpinBuyTurnHistoryRepository;
import com.vtc.event.common.dao.repository.LuckySpinGiftRepository;
import com.vtc.event.common.dao.repository.LuckySpinHistoryRepository;
import com.vtc.event.common.dao.repository.LuckySpinItemOfLuckySpinRepository;
import com.vtc.event.common.dao.repository.LuckySpinItemOfUserRepository;
import com.vtc.event.common.dao.repository.LuckySpinItemRepository;
import com.vtc.event.common.dao.repository.LuckySpinRadiusPersonalTopupRepository;
import com.vtc.event.common.dao.repository.LuckySpinRepository;
import com.vtc.event.common.dao.repository.LuckySpinSettingRepository;
import com.vtc.event.common.dao.repository.LuckySpinUserRepository;
import com.vtc.event.common.dao.repository.TopupCardHistoryRepository;
import com.vtc.event.common.dto.request.LuckySpinExchangeItemRequest;
import com.vtc.event.common.dto.request.LuckySpinGetRequest;
import com.vtc.event.common.dto.request.LuckySpinOpenItemRequest;
import com.vtc.event.common.dto.response.ExchangeItemResponse;
import com.vtc.event.common.dto.response.LuckySpinDetailResponse;
import com.vtc.event.common.dto.response.LuckySpinGiftResponse;
import com.vtc.event.common.dto.response.UserBuyTurnResponse;
import com.vtc.event.common.dto.response.UserTurnSpinDetailResponse;
import com.vtc.event.common.dto.response.UserXuInfoResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.exception.ScoinNotEnoughtException;
import com.vtc.event.common.exception.ScoinNotFoundEntityException;
import com.vtc.event.common.exception.ScoinTimingStartAndEndException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.service.CommonCardScoinService;
import com.vtc.event.common.service.PaymentService;
import com.vtc.event.common.service.TransactionHistoryService;
import com.vtc.event.common.utils.DateUtils;
import com.vtc.event.common.utils.StringUtils;
import com.vtc.event.luckySpin.ConditionTurnover;
import com.vtc.event.luckySpin.RamdomItems;
import com.vtc.event.luckySpin.service.LuckySpinService;
import com.vtc.event.userInfo.service.UserInfoService;
import com.vtc.event.userInfo.service.UserVTCService;

//import app.lib.vtcpay.BASE64Decoder;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 7, 2019
 */
@Service("luckySpinService")
public class LuckySpinServiceImpl extends AbstractService<LuckySpin, Long, LuckySpinRepository>
        implements LuckySpinService {
    
    @Autowired
    LuckySpinSettingRepository luckySpinSettingRepo;

    @Autowired
    LuckySpinItemRepository       itemOfSpinRepo;

    @Autowired
    LuckySpinItemOfLuckySpinRepository    spinItemOfLuckySpinRepo;

    @Autowired
    LuckySpinUserRepository     luckySpinUserRepo;

    @Autowired
    UserInfoService            userInfoService;

    @Autowired
    PaymentService             exchangeMoneyScoinService;

    @Autowired
    LuckySpinHistoryRepository spinHistoryRepo;

    @Autowired
    GiftcodeRepository         giftcodeRepo;

    @Autowired
    TransactionHistoryService  transactionHistoryService;

    @Autowired
    PaymentService             exchangeCoinService;

    @Autowired
    UserVTCService             userVTCService;

    @Autowired
    CommonCardScoinService     cardScoinService;

    @Autowired
    LuckyNumberRepository      luckyNumberRepo;

    @Autowired
    TopupCardHistoryRepository topupCardHistoryRepo;

    @Autowired
    ConditionTurnover          conditionTurnover;

    @Autowired
    PaymentService             paymentService;
    
    @Autowired
    LuckySpinBuyTurnHistoryRepository buyTurnHistoryRepo;
    
    @Autowired
    LuckySpinRadiusPersonalTopupRepository radiusPersonalTopupRepo;
    
    @Autowired
    LuckySpinItemOfUserRepository luckySpinItemOfUserRepo;
    
    @Autowired
    LuckySpinGiftRepository luckySpinGiftRepo;
    
//    private String             TUDO_URL;
//    private String             TUDO_API_KEY;
//    private String             TUDO_API_SECRET;
//
//    public LuckySpinServiceImpl(Environment env) {
//        TUDO_URL = env.getProperty(EnvironmentKey.LIVE_TUDO_URL.getKey());
//        TUDO_API_KEY = env.getProperty(EnvironmentKey.LIVE_TUDO_API_KEY.getKey());
//        TUDO_API_SECRET = env.getProperty(EnvironmentKey.LIVE_TUDO_API_SECRET.getKey());
//    }
    
    @Override
    public List<LuckySpin> getLuckySpinActive(LuckySpinGetRequest request) {
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        return repo.findLuckySpin(request.getSpinId(),
                Constant.STATUS_ACTIVE, pageable);
    }

    @Override
    public int countLuckySpin(LuckySpinGetRequest request) {
        return repo.countLuckySpinGet(request.getSpinId(), Constant.STATUS_ACTIVE);
    }

    @Override
    public Optional<LuckySpinDetailResponse> getLuckySpinDetail(Long luckySpinId, String role)
            throws ScoinBusinessException {
      
        UserInfo userInfo = null;
        if (StringUtils.isEmpty(role))
            userInfo = verifyAccessTokenUser();
        
        if(ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        //get LuckySpin
        List<LuckySpin> luckySpins = repo.findAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        if (CollectionUtils.isEmpty(luckySpins)) {
            throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin id");
        }
        
        LuckySpin luckySpin = luckySpins.get(0);
        
        //get all spin item of lucky spin
//        List<LuckySpinItemOfLuckySpin> itemsOfLuckySpin = luckySpin.getSpinItems();
        
        UserTurnSpinDetailResponse userTurnSpinDetailResponse = new UserTurnSpinDetailResponse();
        List<LuckySpinItemOfUser> itemOfUser = new ArrayList<LuckySpinItemOfUser>();
        List<LuckySpinGift> luckySpinGifts = luckySpinGiftRepo.findAll();
        List<LuckySpinGiftResponse> luckySpinGiftResponses = new ArrayList<LuckySpinGiftResponse>();
        
        luckySpinGifts.forEach(luckySpinGift -> {
            LuckySpinGiftResponse luckySpinGiftResponse = new LuckySpinGiftResponse();
            luckySpinGiftResponse.setLuckySpinGiftId(luckySpinGift.getId());
            luckySpinGiftResponse.setName(luckySpinGift.getName());
            luckySpinGiftResponse.setSetPoint(luckySpinGift.getSetPoint());
            luckySpinGiftResponse.setTypeGift(luckySpinGift.getTypeGift());
            luckySpinGiftResponse.setValue(luckySpinGift.getValue());
            luckySpinGiftResponse.setQuantity(luckySpinGift.getQuantity());
            
            luckySpinGiftResponses.add(luckySpinGiftResponse);
        });
        
        if (!ObjectUtils.isEmpty(userInfo)) {
            //update UserTurnSpin
            LuckySpinUser userTurnSpin = updateUserTurnSpin(userInfo, luckySpin);
            
            //get user buy turn info
            long totalTopupCardToGame = 0;
            UserBuyTurnResponse turnsBuyInfo = new UserBuyTurnResponse();
            
            List<TopupCardHistory> topupCardToGames = topupCardHistoryRepo.
                    findByScoinIdAndPaymentTypeAndCreateOnAfterAndCreateOnBefore(userInfo.getUserVTC().getScoinId(), 
                                                            Constant.SCOIN_CARD_TYPE_CARD_SCOIN_TO_GAME,
                                                            luckySpin.getStartDate(), 
                                                            luckySpin.getEndDate());
            if (!CollectionUtils.isEmpty(topupCardToGames)) {
                for (TopupCardHistory topupScoinToGame : topupCardToGames) {
                    totalTopupCardToGame += topupScoinToGame.getTotalPayment();
                }
            }
            
            turnsBuyInfo.setScoinTopupCardToGame(totalTopupCardToGame);
            
            if (userTurnSpin.getBalance() != 0) {
                turnsBuyInfo.setScoinBalanceRounding(50000 - userTurnSpin.getBalance());
            } else {
                turnsBuyInfo.setScoinBalanceRounding(50000);
            }
            
            //get item of user
            itemOfUser = luckySpinItemOfUserRepo.findByLuckySpinAndUserInfo(luckySpin, userInfo);
            
            //get userTurnSpin response
            userTurnSpinDetailResponse.setUserId(userInfo.getId());
            userTurnSpinDetailResponse.setSpinId(luckySpin.getId());
            userTurnSpinDetailResponse.setTurnsBuy(userTurnSpin.getTurnsBuy());
            userTurnSpinDetailResponse.setTurnsFree(userTurnSpin.getTurnsFree());
            if (!StringUtils.isEmpty(userInfo.getFullName()))
                userTurnSpinDetailResponse.setCurrName(userInfo.getFullName());
            userTurnSpinDetailResponse.setScoin(userInfo.getUserVTC().getScoin());
            userTurnSpinDetailResponse.setCurrName(userInfo.getUserVTC().getUsername());
            userTurnSpinDetailResponse.setTurnsBuyInfo(turnsBuyInfo);
            
            //get luckySpinGift response
            int maxQuantity = -1;
            for (LuckySpinGiftResponse luckySpinGiftResponse : luckySpinGiftResponses) {
                for (LuckySpinGift luckySpinGift : luckySpinGifts) {
                    if (luckySpinGiftResponse.getLuckySpinGiftId() == luckySpinGift.getId()) {
                        Set<LuckySpinItem> luckySpinItems = luckySpinGift.getLuckySpinItems();
                        for(LuckySpinItem luckySpinItem : luckySpinItems) {
                            LuckySpinItemOfUser luckySpinItemOfUser = luckySpinItemOfUserRepo
                                    .findByLuckySpinAndUserInfoAndLuckySpinItem(luckySpin, userInfo, luckySpinItem);
                            int itemQuantity = 0;
                            if (!ObjectUtils.isEmpty(luckySpinItemOfUser)) 
                                itemQuantity = luckySpinItemOfUser.getQuantity();
                            if (maxQuantity < 0
                                    || maxQuantity > itemQuantity) {
                                maxQuantity = itemQuantity;
                            }
                        }
                    }
                    
                    luckySpinGiftResponse.setMaxQuantity(maxQuantity);
                }
            }
        }
        
        LuckySpinDetailResponse response = new LuckySpinDetailResponse();
//        response.setLuckySpinItemOfLuckySpin(itemsOfLuckySpin);
        response.setLuckySpin(luckySpin);
        response.setLuckySpinGifts(luckySpinGiftResponses);
        response.setItemOfUser(itemOfUser);
        response.setSettings(null);
        response.setUserTurnSpin(userTurnSpinDetailResponse);
        
        return Optional.of(response);
    }

    @Override
    public List<LuckySpinItemOfLuckySpin> luckySpinOpenItem(LuckySpinOpenItemRequest request) throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();
        
        if (ObjectUtils.isEmpty(request)
                || ObjectUtils.isEmpty(request.getLuckySpinId())
                || request.getTurnNumber() < 1) {
            throw new ScoinInvalidDataRequestException();
        }
        
        Pageable pageable = PageRequest.of(0, 1);
        
        //get LuckySpin
        List<LuckySpin> luckySpins = findLuckySpin(request.getLuckySpinId(), Constant.STATUS_ACTIVE,pageable);
        if (CollectionUtils.isEmpty(luckySpins)) {
            throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin");
        }
        LuckySpin luckySpin = luckySpins.get(0);
        
        if (luckySpin.getStartDate().after(new Date())
                || luckySpin.getEndDate().before(new Date())) {
            throw new ScoinTimingStartAndEndException();
        }

        // get User Turn Spin and update turn enough
        LuckySpinUser luckySpinUser = updateUserTurnSpin(userInfo, luckySpin);
        
        if (luckySpinUser.getTurnsFree() < 1
               && luckySpinUser.getTurnsBuy() < 1) {
            throw new ScoinNotEnoughtException("Không đủ lượt quay");
        }

        // Make ratio item to group user
        List<RamdomItems> ratioItems = makeRatioItemToUser(luckySpin, luckySpinUser.getPersonalTopup(), userInfo);
        
        ratioItems.forEach(ratioItem -> {
            LOGGER.info("Item ======= : {}", ratioItem.getSpinItemId() + ", ratio =====: " + ratioItem.getRadioItem());
        });

        // quantity item can play
        int turnNumber = request.getTurnNumber();
        if (luckySpinUser.getTurnsFree() < request.getTurnNumber()) turnNumber = luckySpinUser.getTurnsFree();
        
        //pick item follow event
        List<LuckySpinItemOfLuckySpin> responses = new ArrayList<LuckySpinItemOfLuckySpin>();
        for (int i = 0; i < turnNumber; i++) {
            responses.add(pickRandomItem(luckySpin, userInfo, ratioItems));
        }
        
        //update luckySpinUser
        luckySpinUser.setTurnsFree(luckySpinUser.getTurnsFree() - turnNumber);
        luckySpinUserRepo.save(luckySpinUser);
        
        // Update luckySpinTime
        luckySpin.setSpinTimes(luckySpinUser.getLuckySpin().getSpinTimes() + request.getTurnNumber());
        save(luckySpin);

        return responses;

    }
    
    @Override
    public ExchangeItemResponse luckySpinExchangeItem(LuckySpinExchangeItemRequest request)
            throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();
        
        if (ObjectUtils.isEmpty(request) 
                || ObjectUtils.isEmpty(request.getLuckySpinId())
                || ObjectUtils.isEmpty(request.getLuckySpinGiftId())
                || request.getTurnNumber() < 1) {
            throw new ScoinInvalidDataRequestException();
        }
        
        LuckySpin luckySpin = repo.getOne(request.getLuckySpinId());
        if (ObjectUtils.isEmpty(luckySpin)) {
            throw new ScoinNotFoundEntityException("Not found Lucky Spin by this id");
        }
        
//        if (luckySpin.getStartDate().after(new Date())
//                || luckySpin.getEndDate().before(new Date())) {
//            throw new ScoinTimingStartAndEndException();
//        }
        
        LuckySpinGift luckySpinGift = luckySpinGiftRepo.getOne(request.getLuckySpinGiftId());
        if (ObjectUtils.isEmpty(luckySpinGift)) {
            throw new ScoinNotFoundEntityException("Not found Lucky Spin Gift by this id");
        }
        
        //check item quantiry of user
        Set<LuckySpinItem> luckySpinItems = luckySpinGift.getLuckySpinItems();
        luckySpinItems.forEach(luckySpinItem -> {
            LuckySpinItemOfUser luckySpinItemOfUser = luckySpinItemOfUserRepo
                    .findByLuckySpinAndUserInfoAndLuckySpinItem(luckySpin, userInfo, luckySpinItem);
            int quantity = luckySpinItemOfUser.getQuantity();
            if (quantity < request.getTurnNumber()) 
                throw new ScoinNotEnoughtException("Không đủ Chữ để đổi");
        });
        
        for(int i = 0 ; i < request.getTurnNumber(); i++) {
          //Create spin history
          createLuckySpinHistoty(userInfo, 
                  luckySpin, 
                  luckySpinGift,
                  null,
                  Constant.LUCKYSPIN_TURN_TYPE_GIFT);
        }
        
        if (luckySpinGift.getTypeGift().equals(Constant.LUCKYSPIN_GIFT_SCOIN)) {
            long totalScoinGift = luckySpinGift.getValue() * request.getTurnNumber();
            if (totalScoinGift >= 1000000) {
                throw new ScoinFailedToExecuteException("Vượt quá mức scoin cho phép");
            }

            cardScoinService.topupScoin(totalScoinGift, userInfo.getUserVTC().getUsername());
        }
        
        // deduct quantity gift
        luckySpinGift.setQuantity(luckySpinGift.getQuantity() - request.getTurnNumber());
        luckySpinGiftRepo.save(luckySpinGift);
        
        // deduct item of user
        luckySpinItems.forEach(luckySpinItem -> {
            LuckySpinItemOfUser luckySpinItemOfUser = luckySpinItemOfUserRepo
                    .findByLuckySpinAndUserInfoAndLuckySpinItem(luckySpin, userInfo, luckySpinItem);
            luckySpinItemOfUser
                    .setQuantity(luckySpinItemOfUser.getQuantity() - request.getTurnNumber());
            luckySpinItemOfUserRepo.save(luckySpinItemOfUser);
            if (ObjectUtils.isEmpty(luckySpinItemOfUser)) {
                throw new ScoinFailedToExecuteException("Can't create item of user");
            }
        });
        
        ExchangeItemResponse response = new ExchangeItemResponse();
        response.setLuckySpinGiftId(luckySpinGift.getId());
        response.setName(luckySpinGift.getName());
        response.setTypeGift(luckySpinGift.getTypeGift());
        response.setValue(luckySpinGift.getValue() * request.getTurnNumber());
        response.setQuantity(request.getTurnNumber());
        
        return response;
    }
    
    @Override
    public UserXuInfoResponse getBalanceXu(Long scoinId) throws ScoinBusinessException {
        return exchangeMoneyScoinService.getBalanceXu(scoinId);
    }

    @Override
    public List<String> getLuckyNumbers(long cardValue, int quantity) throws Exception {
        if (cardValue < 1 || quantity < 1)
            throw new ScoinInvalidDataRequestException();
        List<String> responses = new ArrayList<String>();
        for (int i = 0; i < quantity ; i++) {
            FundsCardScoin fundsCardScoin = cardScoinService.buyCard(String.valueOf(cardValue), 1);
            String response = "Mã Thẻ : " + fundsCardScoin.getMainCodeCard()
                        + ", Seri : " + fundsCardScoin.getSeriCard()
                        + ", HSD : " + DateUtils.toStringFormDate(fundsCardScoin.getExpirationDateCard(), DateUtils.DATE_TIME_MYSQL_FORMAT);
            responses.add(response);
        }
        
        return responses;
    }

    @Override
    public String createLuckyNumber(int digitNumber) throws ScoinBusinessException {
        String maxNumberString = "";
        for (int i = 0; i < digitNumber; i++) {
            maxNumberString += "9";
        }
        
        int maxNum = Integer.parseInt(maxNumberString);
//        List<LuckyNumber> luckyNumbers = new ArrayList<LuckyNumber>();
        
        List<Integer> luckyCodes = new ArrayList<Integer>();
        for (int i = 0; i <= maxNum; i++) {
          luckyCodes.add(i);
        }
        
        Collections.shuffle(luckyCodes);
        
        String formatLuckyCode = "%0" + digitNumber + "d";
        for (Integer luckyCode : luckyCodes){
          LuckyNumber luckyNumber = new LuckyNumber();
          luckyNumber.setLuckyCode(String.format(formatLuckyCode, luckyCode));
          luckyNumber.setUsed(false);
          luckyNumberRepo.save(luckyNumber);
        }
        return "Success";
    }
    
    private LuckySpinItemOfLuckySpin pickRandomItem(LuckySpin luckySpin, 
                                                    UserInfo userInfo, 
                                                    List<RamdomItems> ratioItems) {
        LuckySpinItemOfLuckySpin resultItem = null;
        while (resultItem == null) {
            long positionRamdom = ramdomInList(ratioItems);
            if (positionRamdom == -1)
                continue;
            resultItem = spinItemOfLuckySpinRepo.findById(positionRamdom)
                    .orElseThrow(() -> new ScoinNotFoundEntityException("Not found spin item by this id ramdom"));
            
            LuckySpinItem item = resultItem.getItem();

            long totalTurnover = 0;
            LuckySpinSetting turnoverSetting = luckySpinSettingRepo.
                    findByKeyNameAndStatus(Constant.LUCKYSPIN_TURNOVER_KEYNAME_TOTAL, Constant.STATUS_ACTIVE);
            if (!ObjectUtils.isEmpty(turnoverSetting))
                totalTurnover = turnoverSetting.getIntValue();

            boolean passItem = conditionTurnover.checkPickItemFolowTurnover(luckySpin,
                    totalTurnover, item);
            if (!passItem) {
                LOGGER.info("PASS ITEM : ============== : {}" + item.getType() + " , Value = " + item.getValue());
                resultItem = null;
                continue;
            }
        }
        
        LuckySpinItemOfUser luckySpinItemOfUser = luckySpinItemOfUserRepo
                .findByLuckySpinAndUserInfoAndLuckySpinItem(luckySpin, userInfo, resultItem.getItem());
        if (ObjectUtils.isEmpty(luckySpinItemOfUser)) {
            luckySpinItemOfUser = new LuckySpinItemOfUser();
            luckySpinItemOfUser.setLuckySpin(luckySpin);
            luckySpinItemOfUser.setUserInfo(userInfo);
            luckySpinItemOfUser.setLuckySpinItem(resultItem.getItem());
            luckySpinItemOfUser.setQuantity(1);
        } else {
            luckySpinItemOfUser.setQuantity(luckySpinItemOfUser.getQuantity() + 1);
        }
        
        luckySpinItemOfUserRepo.save(luckySpinItemOfUser);
        if (ObjectUtils.isEmpty(luckySpinItemOfUser))
            throw new ScoinFailedToExecuteException("Don't success when save Item of User");
        
        createLuckySpinHistoty(userInfo, luckySpin, null, resultItem.getItem(),
                Constant.LUCKYSPIN_TURN_TYPE_ITEM);
        
        // update quantity Spin_item
        resultItem.setReceivedQuantity(resultItem.getReceivedQuantity() + 1);
        spinItemOfLuckySpinRepo.save(resultItem);
        
        return resultItem;
    }
    
    //===================== COMPONENT =============================
    
//    private synchronized LuckyNumber addLuckyNumber(LuckyNumber luckyNumber, UserInfo userInfo) {
//        luckyNumber.setScoinId(userInfo.getUserVTC().getScoinId());
//        luckyNumber.setUsed(true);
//        luckyNumber = luckyNumberRepo.save(luckyNumber);
//        return luckyNumber;
//    }
    
//    private void addItemTuDoScoin(Long scoinId, int itemId, String itemName, String giftDetail) {
//        String description = "Chúc mừng bạn đã sở hữu " + itemName 
//                + ". Chi tiết : " + giftDetail;
//        
//        Long time = new Timestamp(System.currentTimeMillis()).getTime();
//        String sign = StringUtils.toMD5(TUDO_API_KEY
//                                + TUDO_API_SECRET
//                                + scoinId.toString()
//                                + String.valueOf(itemId)
//                                + time.toString());
//        AddItemTuDoRequest addItemTuDoRequest = new AddItemTuDoRequest();
//        addItemTuDoRequest.setApiKey(TUDO_API_KEY);
//        addItemTuDoRequest.setScoinId(scoinId);
//        addItemTuDoRequest.setItemId(itemId);
//        addItemTuDoRequest.setDescription(description);
//        addItemTuDoRequest.setTime(time);
//        addItemTuDoRequest.setSign(sign);
//        
//        CallApiScoinBaseResponse<String> response = ApiExchangeServiceUtil
//                .post(TUDO_URL, addItemTuDoRequest, new TypeReference<CallApiScoinBaseResponse<String>>() {});
//        
//        if (response.getError_code() < 0) {
//            throw new ScoinUnknownErrorException(response.getError_code().toString(),
//                    response.getError_desc());
//        }
//        
//    }
    
    private long ramdomInList(List<RamdomItems> randomItems) {
        int tmp = ThreadLocalRandom.current().nextInt(10000);
        
        List<Long> spinTurnIds = new ArrayList<Long>();
        randomItems.forEach(randomItem -> {
            int totalItemFollowRatio = Math.round((float) randomItem.getRadioItem() * 100);
            long spinItemId = randomItem.getSpinItemId();
            for (int i = 0; i < totalItemFollowRatio; i++) {
                spinTurnIds.add(spinItemId);
            }
        });
        Collections.shuffle(spinTurnIds);
        
      return spinTurnIds.get(tmp);
  }
    
    private LuckySpinUser updateUserTurnSpin(UserInfo userInfo, LuckySpin luckySpin) {
        //process turn spin
        int turnTopupCardToGame = 0;
        long totalTopupCardToGame = 0;
        long balance = 0;
        LuckySpinUser userTurnSpin = new LuckySpinUser();
        Optional<LuckySpinUser> optUserTurnSpin = luckySpinUserRepo
                .findByUserInfoAndLuckySpin(userInfo, luckySpin);
        
        if (!optUserTurnSpin.isPresent()) {
            userTurnSpin.setLuckySpin(luckySpin);
            userTurnSpin.setUserInfo(userInfo);
        } else {
            userTurnSpin = optUserTurnSpin.get();
        }
        
        //Update UserTurnSpin
        List<TopupCardHistory> topupCards = topupCardHistoryRepo.
                findByLuckyWheelUsedIsFalseAndScoinIdAndCreateOnAfterAndCreateOnBefore(
                        userInfo.getUserVTC().getScoinId(), luckySpin.getStartDate(), luckySpin.getEndDate());
        if (!CollectionUtils.isEmpty(topupCards)) {
            for (TopupCardHistory topupCard : topupCards) {
                if (topupCard.getPaymentType().equals(Constant.SCOIN_CARD_TYPE_CARD_SCOIN_TO_GAME)) {
                  totalTopupCardToGame += topupCard.getTotalPayment();
                }
               
                topupCard.setLuckyWheelUsed(true);
                topupCardHistoryRepo.save(topupCard);
            }
            
            balance = totalTopupCardToGame + userTurnSpin.getBalance();
            if (balance >= 50000) {
                turnTopupCardToGame = (int) (balance / 50000);
                balance = balance - (turnTopupCardToGame * 50000);
            }
            
            userTurnSpin.setTurnsFree(userTurnSpin.getTurnsFree() + turnTopupCardToGame);
            userTurnSpin.setPersonalTopup(userTurnSpin.getPersonalTopup() + totalTopupCardToGame);
            userTurnSpin.setBalance(balance);
        }
        
        userTurnSpin = luckySpinUserRepo.save(userTurnSpin);
        if (ObjectUtils.isEmpty(userTurnSpin)) {
            throw new ScoinFailedToExecuteException("Don't create new user turn spin");
        }
        
        return userTurnSpin;
    }
    
    private List<RamdomItems> makeRatioItemToUser(LuckySpin luckySpin, long personalTopup, UserInfo userInfo){
        List<RamdomItems> ratioItems = new ArrayList<RamdomItems>();
        int totalIndexItem = 0;
        boolean haveActionItem = false;
        
        
        
        // choose ratio of item for group user
        if (personalTopup >= 25000000
                && spinHistoryRepo.countMainItemReceived(25000000, luckySpin, userInfo) < 1) {
            // User have topup high
            List<LuckySpinRadiusPersonalTopup> radiusPersonalTopups = radiusPersonalTopupRepo.
                    findByLuckySpinAndPersonalTopupLandmark(luckySpin, 25000000);
            
            if (!ObjectUtils.isEmpty(radiusPersonalTopups)) {
                
                //total ratio index
                for (LuckySpinRadiusPersonalTopup radiusPersonalTopup : radiusPersonalTopups) {
                    totalIndexItem += radiusPersonalTopup.getRatioIndex();
                    if (radiusPersonalTopup.getSpinItem().getItem().getType().equals(Constant.LUCKYSPIN_GIFT_ACTION))
                        haveActionItem = true;
                }
                
                // check is lucky spin has item
                if (totalIndexItem < 1 && haveActionItem == true) {
                    throw new ScoinNotFoundEntityException("Not found items off this lucky spin");
                }
                
                // add to items random
                for (LuckySpinRadiusPersonalTopup radiusPersonalTopup : radiusPersonalTopups) {
                    if (radiusPersonalTopup.getRatioIndex() <= 0) continue;
                    ratioItems.add(new RamdomItems(radiusPersonalTopup.getSpinItem().getId(),
                                                   radiusPersonalTopup.getRatio(),
                                                   radiusPersonalTopup.getSpinItem().getItem().getType()));
                }
                return ratioItems;
            }
        } 
        // User have ratio basic
        //total ratio index and check is lucky spin has item
        for (LuckySpinItemOfLuckySpin item : luckySpin.getSpinItems()) {
            totalIndexItem += item.getRatioIndex();
            if (item.getItem().getType().equals(Constant.LUCKYSPIN_GIFT_ACTION))
                haveActionItem = true;
        }
            
        // check is lucky spin has item
        if (totalIndexItem < 1 && haveActionItem == true) {
            throw new ScoinNotFoundEntityException("Not found items off this lucky spin");
        }
        
        // add to items random
        for (LuckySpinItemOfLuckySpin item : luckySpin.getSpinItems()) {
            if (item.getRatioIndex() > 0
                    || item.getItem().getType().equals(Constant.LUCKYSPIN_GIFT_ACTION)) {
                ratioItems.add(new RamdomItems(item.getId(),
                        (double) 100 / totalIndexItem * item.getRatioIndex(),
                        item.getItem().getType()));
            }
        }
        
        return ratioItems;
    }
    
    private void createLuckySpinHistoty(UserInfo userInfo, 
                                        LuckySpin luckySpin, 
                                        LuckySpinGift luckySpinGift,
                                        LuckySpinItem luckySpinItem,
                                        String turnType) {
        LuckySpinHistory spinHistory = new LuckySpinHistory();
        
        if (!ObjectUtils.isEmpty(luckySpinGift)) {
            spinHistory.setValue(luckySpinGift.getValue());
            spinHistory.setMessage(luckySpinGift.getName());
            spinHistory.setDescription(luckySpinGift.getTypeGift());
            spinHistory.setItem(null);
            spinHistory.setGift(luckySpinGift);
        } else {
            spinHistory.setValue(luckySpinItem.getValue());
            spinHistory.setMessage(luckySpinItem.getName());
            spinHistory.setDescription(luckySpinItem.getType());
            spinHistory.setItem(luckySpinItem);
            spinHistory.setGift(null);
        }
       
        spinHistory.setUserInfo(userInfo);
        spinHistory.setLuckySpin(luckySpin);
        
        spinHistory.setStatus(Constant.STATUS_RECIEVED);
        spinHistory.setTurnType(turnType);
        LuckySpinHistory history = spinHistoryRepo.save(spinHistory);
        if (ObjectUtils.isEmpty(history)) {
            throw new ScoinFailedToExecuteException("Can't create Spin History");
        }
    }
    
    @Override
    public List<LuckySpin> findLuckySpin(Long spinId, String status, Pageable pageable) {
        return repo.findLuckySpin(spinId, status, pageable);
    }

    @Override
    public LuckySpin findByType(String type) {
        Pageable pageable = PageRequest.of(0, 1);
        List<LuckySpin> luckySpins = repo.findByType(type, pageable);
        if (CollectionUtils.isEmpty(luckySpins)) {
            throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin");
        }
        
        return luckySpins.get(0);
    }
    
}
