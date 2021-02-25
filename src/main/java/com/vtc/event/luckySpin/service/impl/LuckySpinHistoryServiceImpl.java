/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinBuyTurnHistory;
import com.vtc.event.common.dao.entity.LuckySpinHistory;
import com.vtc.event.common.dao.entity.LuckySpinHistoryFake;
import com.vtc.event.common.dao.entity.LuckySpinItemOfLuckySpin;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.repository.LuckySpinBuyTurnHistoryRepository;
import com.vtc.event.common.dao.repository.LuckySpinHistoryFakeRepository;
import com.vtc.event.common.dao.repository.LuckySpinHistoryRepository;
import com.vtc.event.common.dto.request.AbstractResquest;
import com.vtc.event.common.dto.response.GetLuckySpinHistoryResponse;
import com.vtc.event.common.dto.response.GiftQuantityExistResponse;
import com.vtc.event.common.dto.response.LuckySpinTurnBuyHistoryResponse;
import com.vtc.event.common.dto.response.LuckySpinTurnHistoryResponse;
import com.vtc.event.common.dto.response.SpinHistoryGetLuckyResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.exception.ScoinNotFoundEntityException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.utils.DateUtils;
import com.vtc.event.common.utils.StringUtils;
import com.vtc.event.luckySpin.service.LuckySpinHistoryService;
import com.vtc.event.luckySpin.service.LuckySpinService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 2, 2019
 */
@Service("luckySpinHistoryService")
public class LuckySpinHistoryServiceImpl
        extends AbstractService<LuckySpinHistory, Long, LuckySpinHistoryRepository>
        implements LuckySpinHistoryService {

    @Autowired
    LuckySpinService          luckySpinService;

    @Autowired
    LuckySpinHistoryFakeRepository spinHistoryFakeRepo;
    
    @Autowired
    LuckySpinHistoryRepository spinHistoryRepo;
    
    @Autowired
    LuckySpinBuyTurnHistoryRepository luckySpinBuyTurnHistoryRepo;
    
    private int TOTAL_SPIN_HISTORY_ALL;

    @Override
    public GetLuckySpinHistoryResponse getSpinGiftHistory(Long luckySpinId, AbstractResquest request) throws ScoinBusinessException {
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        LuckySpin luckySpin = luckySpinService.findByType(Constant.LUCKYSPIN_TYPE_GHEP_CHU);

        GetLuckySpinHistoryResponse response = new GetLuckySpinHistoryResponse();
        List<SpinHistoryGetLuckyResponse> responseScoins = new ArrayList<SpinHistoryGetLuckyResponse>();
        List<SpinHistoryGetLuckyResponse> responseGolds = new ArrayList<SpinHistoryGetLuckyResponse>();
        List<LuckySpinHistory> spinHistories = new ArrayList<LuckySpinHistory>();
        
        List<String> spinTypeGolds = new ArrayList<String>();
        spinTypeGolds.add(Constant.LUCKYSPIN_GIFT_GOLD);
        spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(luckySpin, 
                null, //user
                null, //limited date
                spinTypeGolds, // type of gift
                Constant.LUCKYSPIN_TURN_TYPE_GIFT,
                null);
        
        if (!CollectionUtils.isEmpty(spinHistories)) {
            responseGolds = toResponse(spinHistories);
        }
        
        List<LuckySpinHistoryFake> historyFakes = spinHistoryFakeRepo.
        		findByLuckySpinId(luckySpinId);
        if(!CollectionUtils.isEmpty(historyFakes)) {
        	for (LuckySpinHistoryFake historyFake : historyFakes) {
        		if(historyFake.getItemType().equals(Constant.LUCKYSPIN_GIFT_GOLD)) {
        			SpinHistoryGetLuckyResponse responseGoldFace = new SpinHistoryGetLuckyResponse();
        			responseGoldFace.setDate(DateUtils.toStringFormDate(historyFake.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
        			responseGoldFace.setItemName(historyFake.getItemName());
        			responseGoldFace.setUserName(StringUtils.hiddenCharString(historyFake.getUserName(), 3));
        			
        			responseGolds.add(responseGoldFace);
        		}
        	};
        }
        
        response.setGolds(responseGolds);
        
        List<String> spinTypeScoins = new ArrayList<String>();
        spinTypeScoins.add(Constant.LUCKYSPIN_GIFT_SCOIN);
        spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(luckySpin, 
                null, //user
                null, //limited date
                spinTypeScoins, // type of gift
                Constant.LUCKYSPIN_TURN_TYPE_GIFT,
                null);

        if(!CollectionUtils.isEmpty(historyFakes)) {
        	for (LuckySpinHistoryFake historyFake : historyFakes) {
        		if(historyFake.getItemType().equals(Constant.LUCKYSPIN_GIFT_SCOIN)) {
        			SpinHistoryGetLuckyResponse responseScoinFace = new SpinHistoryGetLuckyResponse();
        			responseScoinFace.setDate(DateUtils.toStringFormDate(historyFake.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
        			responseScoinFace.setItemName(historyFake.getItemName());
        			responseScoinFace.setUserName(StringUtils.hiddenCharString(historyFake.getUserName(), 3));
        			
        			responseScoins.add(responseScoinFace);
        		}
        	};
        }
        
        if (!CollectionUtils.isEmpty(spinHistories)) {
            responseScoins = toResponse(spinHistories);
        }
        
        List<LuckySpinHistoryFake> spinHistoryFakes = spinHistoryFakeRepo.findByLuckySpinId(luckySpin.getId());
        if (!CollectionUtils.isEmpty(spinHistoryFakes)) {
            for (LuckySpinHistoryFake spinHistoryFake : spinHistoryFakes) {
                SpinHistoryGetLuckyResponse responseScoin = new SpinHistoryGetLuckyResponse();
                responseScoin.setDate(DateUtils.toStringFormDate(spinHistoryFake.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
                responseScoin.setItemName(spinHistoryFake.getItemName());
                responseScoin.setUserName(StringUtils.hiddenCharString(spinHistoryFake.getUserName(), 3));
                
                responseScoins.add(responseScoin);
            }
        }
        TOTAL_SPIN_HISTORY_ALL = responseScoins.size();
        
        //sort response follow date
        Collections.sort(responseScoins, new Comparator<SpinHistoryGetLuckyResponse>() {
            @Override
            public int compare(SpinHistoryGetLuckyResponse o1, SpinHistoryGetLuckyResponse o2) {
                Long o1Date = DateUtils.toDateFromStr(o1.getDate(), "HH:mm:ss dd-MM-yyyy").getTime();
                Long o2Date = DateUtils.toDateFromStr(o2.getDate(), "HH:mm:ss dd-MM-yyyy").getTime();
                return o2Date.compareTo(o1Date);
            }
        });
        
        // limit and offset
        if (request.getLimit() <= 0
                || request.getOffset() > Math.round(responseScoins.size()/request.getLimit())) {
            return new GetLuckySpinHistoryResponse();
        }
        
        responseScoins = responseScoins.stream().skip(request.getOffset() * request.getLimit())
                .limit(request.getLimit()).collect(Collectors.toList());
        response.setScoin(responseScoins);
        return response;
    }
    
    @Override
    public List<SpinHistoryGetLuckyResponse> getSpinHistoryByType(Long luckySpinId, String typeGift, AbstractResquest request) throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();
        
//        if (ObjectUtils.isEmpty(luckySpinId)) throw new ScoinInvalidDataRequestException();
//        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
//                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        Pageable pageable = null;
        if (request.getLimit() != 0) {
            pageable = PageRequest.of(request.getOffset(), request.getLimit());
        }

        List<LuckySpinHistory> spinHistories = new ArrayList<LuckySpinHistory>();
        List<String> spinTypes = new ArrayList<String>();
        spinTypes.add(typeGift);
        spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0), 
                userInfo, 
                null, spinTypes, Constant.LUCKYSPIN_TURN_TYPE_GIFT, pageable);
        if (CollectionUtils.isEmpty(spinHistories))
            return new ArrayList<SpinHistoryGetLuckyResponse>();
        
        return toResponse(spinHistories);
    }
    
    @Override
    public List<SpinHistoryGetLuckyResponse> getSpinTudo(Long luckySpinId, List<String> typeGifts,
                                                         AbstractResquest request) throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();
        
//        if (ObjectUtils.isEmpty(luckySpinId)) throw new ScoinInvalidDataRequestException();
//        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
//                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        List<LuckySpinHistory> spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(
                                                      luckySpins.get(0), 
                                                      userInfo, 
                                                      null,
                                                      typeGifts,
                                                      Constant.LUCKYSPIN_TURN_TYPE_GIFT,
                                                      pageable);
        List<LuckySpinHistory> spinHistoriesResponse = new ArrayList<LuckySpinHistory>();
        spinHistories.forEach(spinHistorie -> {
            spinHistoriesResponse.add(spinHistorie);
        });
        
        if (CollectionUtils.isEmpty(spinHistories))
            return new ArrayList<SpinHistoryGetLuckyResponse>();
        
        return toResponse(spinHistories);
    }
    
    @Override
    public List<LuckySpinTurnHistoryResponse> getOpenWordSpinHistory(Long luckySpinId,
                                                                 AbstractResquest request) {
        UserInfo userInfo = verifyAccessTokenUser();
        
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        List<LuckySpinHistory> spinHistories = repo.
                findByUserInfoAndLuckySpinAndDescriptionOrderByCreateOnDesc(userInfo, 
                                                                luckySpins.get(0),
                                                                Constant.LUCKYSPIN_GIFT_WORD,
                                                                pageable);
        
        List<LuckySpinTurnHistoryResponse> responses = new ArrayList<LuckySpinTurnHistoryResponse>();
        for (LuckySpinHistory spinHistory : spinHistories) {
            String date = DateUtils.toStringFormDate(spinHistory.getCreateOn(), "HH:mm:ss dd-MM-yyyy");
            responses.add(new LuckySpinTurnHistoryResponse(spinHistory.getItem().getName(), date));
        }
        return responses;
    }
    
    @Override
    public List<LuckySpinTurnBuyHistoryResponse> getSpinTurnBuyHistory(Long luckySpinId,
                                                                       AbstractResquest request) {
        UserInfo userInfo = verifyAccessTokenUser();
        
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpinTurnBuyHistoryResponse> responses = new ArrayList<LuckySpinTurnBuyHistoryResponse>();
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        List<LuckySpinBuyTurnHistory> turnBuyHistories = luckySpinBuyTurnHistoryRepo.findByUserInfoOrderByCreateOnDesc(userInfo, pageable);
        if (ObjectUtils.isEmpty(turnBuyHistories)) return new ArrayList<LuckySpinTurnBuyHistoryResponse>();
        
        turnBuyHistories.forEach(turnBuyHistory -> {
            String sourceBuyturn = "";
            if (turnBuyHistory.getBuyType().equals(Constant.LUCKYSPIN_BUY_TURN_TYPE_TOPUP_GAME)) {
                sourceBuyturn = "Nạp Game";
            } else if (turnBuyHistory.getBuyType().equals(Constant.LUCKYSPIN_BUY_TURN_TYPE_CROSS_CARD)) {
                sourceBuyturn = "Mua";
            }
            
            responses.add(new LuckySpinTurnBuyHistoryResponse(sourceBuyturn, 
                          turnBuyHistory.getSpinTurn(), 
                          DateUtils.toStringFormDate(turnBuyHistory.getCreateOn(), "HH:mm:ss dd-MM-yyyy")));
        });
        return responses;
    }
    
    @Override
    public int countSpinHistory(Long luckySpinId, List<String> typeGifts, Long value, Date day) {
        if (ObjectUtils.isEmpty(luckySpinId)) return TOTAL_SPIN_HISTORY_ALL;
//        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
//                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        if(!ObjectUtils.isEmpty(day)) 
            day = DateUtils.toDateChangeFormatFromDate(day, DateUtils.DATE_MYSQL_FORMAT);
        if (!ObjectUtils.isEmpty(value)) 
            return repo.countBySpinEventAndUserInfoAndTypeGift(luckySpins.get(0),null, null, typeGifts, value, day);
        
        return repo.countBySpinEventAndUserInfoAndTypeGift(luckySpins.get(0),null, null, typeGifts, null, day);
    }
    
    @Override
    public int countSpinHistoryHasUser(Long luckySpinId, List<String> typeGifts) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) return TOTAL_SPIN_HISTORY_ALL;
//        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
//                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        return repo.countBySpinEventAndUserInfoAndTypeGift(luckySpins.get(0), userInfo, null, typeGifts, null, null);
    }
  
    @Override
    public int countTurnSpinHistory(Long luckySpinId) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        return repo.countByUserInfoAndLuckySpinAndDescriptionOrderByCreateOnDesc(userInfo, luckySpins.get(0), Constant.LUCKYSPIN_GIFT_WORD);
    }
    
    @Override
    public int countSpinTurnBuyHistory(Long luckySpinId) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        return luckySpinBuyTurnHistoryRepo.countByUserInfo(userInfo);
    }
    
    private List<SpinHistoryGetLuckyResponse> toResponse(List<LuckySpinHistory> spinHistories){
        List<SpinHistoryGetLuckyResponse> historyResponses = new ArrayList<SpinHistoryGetLuckyResponse>();
        for (LuckySpinHistory spinHistory : spinHistories) {
//            spinHistory.setUserInfo(null);
            SpinHistoryGetLuckyResponse historyResponse = new SpinHistoryGetLuckyResponse();
            historyResponse.setDate(DateUtils.toStringFormDate(spinHistory.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
            if (StringUtils.isEmpty(spinHistory.getUserName())) {
                spinHistory.setUserName(spinHistory.getUserInfo().getUserVTC().getUsername());
                spinHistoryRepo.save(spinHistory);
            }
            
            if (spinHistory.getUserName().equals("- Chưa có -")) {
                historyResponse.setUserName(spinHistory.getUserName());
            } else {
                historyResponse.setUserName(StringUtils.hiddenCharString(spinHistory.getUserName(), 3));
            }
            
            if (!ObjectUtils.isEmpty(spinHistory.getItem())) {
                historyResponse.setItemName(spinHistory.getItem().getName());
            } else {
                historyResponse.setItemName(spinHistory.getGift().getName());
            }
            historyResponses.add(historyResponse);
        }
        return historyResponses;
    }
    
    @Override
    public List<GiftQuantityExistResponse> giftQuantityExist(Long luckySpinId)
            throws ScoinBusinessException {
        if (ObjectUtils.isEmpty(luckySpinId))
            throw new ScoinInvalidDataRequestException();
        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        List<LuckySpinItemOfLuckySpin> spinItems = luckySpin.getSpinItems();
        if (CollectionUtils.isEmpty(spinItems)) {
            return new ArrayList<GiftQuantityExistResponse>();
        }
        
        List<GiftQuantityExistResponse> responses = new ArrayList<GiftQuantityExistResponse>();
        
      //sort response follow date
        Collections.sort(spinItems, new Comparator<LuckySpinItemOfLuckySpin>() {
            @Override
            public int compare(LuckySpinItemOfLuckySpin o1, LuckySpinItemOfLuckySpin o2) {
                Long o1Date = o1.getItem().getValue();
                Long o2Date = o2.getItem().getValue();
                return o2Date.compareTo(o1Date);
            }
        });
        
        spinItems.forEach(spinItem -> {
            String itemType = spinItem.getItem().getType();
            if (!itemType.equals(Constant.LUCKYSPIN_GIFT_ACTION)) {
                long itemValue = spinItem.getItem().getValue();
                int giftReceivedRael = repo.countByItemType(luckySpin, itemType, itemValue);
                int giftReceivedFake = spinHistoryFakeRepo.countByLuckySpinIdAndItemTypeAndItemValue(luckySpin.getId(), itemType, itemValue);
                int giftExist = spinItem.getTotalQuantity() - (giftReceivedRael + giftReceivedFake);
                if (giftExist < 0) giftExist = 0;
                
                responses.add(new GiftQuantityExistResponse(spinItem.getItem().getName(), giftExist, itemType));
            }
        });
      
        return responses;
    }

    @SuppressWarnings("resource")
    @Override
    public String createSpinHistoryFake(MultipartFile file) throws IOException, InvalidFormatException {
        String URLProvisional = Constant.PROJECT_RESOURCES + "file_create_history_fake.xlsx";
        File fileProvisional = new File(URLProvisional);
        if (!fileProvisional.exists()) fileProvisional.createNewFile();
        FileOutputStream fos = new FileOutputStream(fileProvisional);
        fos.write(file.getBytes());
        fos.close();
        
        FileInputStream ios = new FileInputStream(fileProvisional);
        Workbook workbook = new XSSFWorkbook(ios);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            LuckySpinHistoryFake historyFake = new LuckySpinHistoryFake();
            historyFake.setUserName(currentRow.getCell(0).getStringCellValue().trim());
            historyFake.setItemName(currentRow.getCell(1).getStringCellValue().trim());
            historyFake = spinHistoryFakeRepo.save(historyFake);
            if (ObjectUtils.isEmpty(historyFake)) throw new ScoinFailedToExecuteException("Don't create history fake");
        }
        
        fileProvisional.delete();
        return "Successful";
    }

}
