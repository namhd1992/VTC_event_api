/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    public List<SpinHistoryGetLuckyResponse> getSpinHistory(Long luckySpinId, String typeItem, AbstractResquest request) throws ScoinBusinessException {
        if (ObjectUtils.isEmpty(luckySpinId)) throw new ScoinInvalidDataRequestException();
        
        Pageable pageable = PageRequest.of(0, 1);
        List<LuckySpin> luckySpins = luckySpinService.findLuckySpin(luckySpinId, Constant.STATUS_ACTIVE,pageable);
        if (CollectionUtils.isEmpty(luckySpins)) {
            throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin");
        }
        
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        LuckySpin luckySpin = luckySpins.get(0);
        
        List<SpinHistoryGetLuckyResponse> responses = new ArrayList<SpinHistoryGetLuckyResponse>();
        List<LuckySpinHistory> spinHistories = new ArrayList<LuckySpinHistory>();
        List<String> spinType = new ArrayList<String>();
        if (luckySpin.getBuyTurnType().equals(Constant.LUCKYSPIN_TOPUP_TYPE_ALL)) {
            spinType.add(Constant.LUCKYSPIN_TOPUP_TYPE_SCOIN);
            spinType.add(Constant.LUCKYSPIN_TOPUP_TYPE_CARD);
        } else {
            spinType.add(luckySpin.getBuyTurnType());
        }
        
        spinHistories = repo.
                getByLuckySpinAndUserInfoAndTypeGift(luckySpin, null, null, spinType,null);
        
        if (!CollectionUtils.isEmpty(spinHistories)) {
            responses = toResponse(spinHistories, null);
        }
        
        List<LuckySpinHistoryFake> spinHistoryFakes = spinHistoryFakeRepo.findByLuckySpinId(luckySpin.getId());
        if (!CollectionUtils.isEmpty(spinHistoryFakes)) {
            for (LuckySpinHistoryFake spinHistoryFake : spinHistoryFakes) {
                SpinHistoryGetLuckyResponse response = new SpinHistoryGetLuckyResponse();
                response.setDate(DateUtils.toStringFormDate(spinHistoryFake.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
                response.setItemName(spinHistoryFake.getItemName());
                response.setUserName(StringUtils.hiddenCharString(spinHistoryFake.getUserName(), 3));
                if(!StringUtils.isEmpty(spinHistoryFake.getPhone()))
                    response.setPhone(StringUtils.hiddenCharString(spinHistoryFake.getPhone(), 3));
                if (spinHistoryFake.isHighlights()) response.setDescription(Constant.STATUS_HIGHLIGHTS);
                responses.add(response);
            }
        }
        TOTAL_SPIN_HISTORY_ALL = responses.size();
        
        responses.sort((o1, o2) -> Long
                .valueOf(DateUtils.toDateFromStr(o2.getDate(), "HH:mm:ss dd-MM-yyyy").getTime())
                .compareTo(Long.valueOf(
                        DateUtils.toDateFromStr(o1.getDate(), "HH:mm:ss dd-MM-yyyy").getTime())));
        
        // limit and offset
        if (request.getLimit() <= 0
                || request.getOffset() > Math.round(responses.size()/request.getLimit())) {
            return new ArrayList<SpinHistoryGetLuckyResponse>();
        }
        
        // Item highlights 
        if (!StringUtils.isEmpty(typeItem) 
                && typeItem.equals(Constant.STATUS_HIGHLIGHTS)) {
            List<SpinHistoryGetLuckyResponse> highlights = new ArrayList<SpinHistoryGetLuckyResponse>();
            responses.forEach(honor -> {
                if (!StringUtils.isEmpty(honor.getDescription())
                        && honor.getDescription().equals(Constant.STATUS_HIGHLIGHTS)) {
                    highlights.add(honor);
                }
            });
            
            responses = highlights;
            TOTAL_SPIN_HISTORY_ALL = responses.size();
        }
        
        if (!CollectionUtils.isEmpty(responses))
            responses = (responses.stream().skip(request.getOffset() * request.getLimit())
                    .limit(request.getLimit()).collect(Collectors.toList()));
        
        return responses;
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
        spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0), userInfo, null, spinTypes, pageable);
        if (CollectionUtils.isEmpty(spinHistories))
            return new ArrayList<SpinHistoryGetLuckyResponse>();
        
        return toResponse(spinHistories, typeGift);
    }
    
    @Override
    public List<SpinHistoryGetLuckyResponse> getSpinTudo(Long luckySpinId, AbstractResquest request) throws ScoinBusinessException {
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
        List<String> spinType = new ArrayList<String>();
        spinType.add(Constant.LUCKYSPIN_GIFT_SCOIN_CARD);
        spinType.add(Constant.LUCKYSPIN_GIFT_SCOIN);
        List<LuckySpinHistory> spinHistories = repo.getByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0), 
                                                      userInfo, 
                                                      null, 
                                                      spinType, 
                                                      pageable);
        List<LuckySpinHistory> spinHistoriesResponse = new ArrayList<LuckySpinHistory>();
        spinHistories.forEach(spinHistorie -> {
            if (spinHistorie.getItem().getType().equals(Constant.LUCKYSPIN_GIFT_SCOIN_CARD)) {
                spinHistoriesResponse.add(spinHistorie);
            }
        });
        
        if (CollectionUtils.isEmpty(spinHistories))
            return new ArrayList<SpinHistoryGetLuckyResponse>();
        
        return toResponse(spinHistories, Constant.LUCKYSPIN_GIFT_SCOIN_CARD);
    }
    
    @Override
    public List<LuckySpinTurnHistoryResponse> getTurnSpinHistory(Long luckySpinId,
                                                                 AbstractResquest request) {
        UserInfo userInfo = verifyAccessTokenUser();
        
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        List<LuckySpinHistory> spinHistories = repo.
                findByUserInfoAndLuckySpinOrderByCreateOnDesc(userInfo, luckySpins.get(0), pageable);
        
        List<LuckySpinTurnHistoryResponse> responses = new ArrayList<LuckySpinTurnHistoryResponse>();
        long stt = (request.getOffset() *request.getLimit()) + 1;
        for (LuckySpinHistory spinHistory : spinHistories) {
            String date = DateUtils.toStringFormDate(spinHistory.getCreateOn(), "HH:mm:ss dd-MM-yyyy");
            responses.add(new LuckySpinTurnHistoryResponse(stt, spinHistory.getItem().getName(), date));
            stt +=1;
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
//            String sourceBuyturn = "";
//            if (turnBuyHistory.getBuyType().equals(Constant.LUCKYSPIN_TOPUP_TYPE_CARD)) {
//                sourceBuyturn = Constant.LUCKYSPIN_TOPUP_TYPE_CARD_DES;
//            } else if (turnBuyHistory.getBuyType().equals(Constant.LUCKYSPIN_TOPUP_TYPE_SCOIN)) {
//                sourceBuyturn = Constant.LUCKYSPIN_TOPUP_TYPE_SCOIN_DES;
//            }
            
            responses.add(new LuckySpinTurnBuyHistoryResponse("Nhận Chìa Khóa", 
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
            return repo.countByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0),null, null, typeGifts, value, day);
        
        return repo.countByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0),null, null, typeGifts, null, day);
    }
    
    @Override
    public int countSpinHistoryHasUser(Long luckySpinId, List<String> typeGifts) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) return TOTAL_SPIN_HISTORY_ALL;
//        LuckySpin luckySpin = luckySpinService.get(luckySpinId).orElseThrow(
//                () -> new ScoinNotFoundEntityException("Not fount Lucky Spin by this id"));
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        return repo.countByLuckySpinAndUserInfoAndTypeGift(luckySpins.get(0), userInfo, null, typeGifts, null, null);
    }
  
    @Override
    public int countTurnSpinHistory(Long luckySpinId) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        List<LuckySpin> luckySpins = luckySpinService.getAll();
        if(CollectionUtils.isEmpty(luckySpins)) throw new ScoinNotFoundEntityException("Not found LuckySpin");
        
        return repo.countByUserInfoAndLuckySpin(userInfo, luckySpins.get(0));
    }
    
    @Override
    public int countSpinTurnBuyHistory(Long luckySpinId) {
        UserInfo userInfo = verifyAccessTokenUser();
        if (ObjectUtils.isEmpty(luckySpinId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        return luckySpinBuyTurnHistoryRepo.countByUserInfo(userInfo);
    }
    
    private List<SpinHistoryGetLuckyResponse> toResponse(List<LuckySpinHistory> spinHistories, String typeGift){
        List<SpinHistoryGetLuckyResponse> historyResponses = new ArrayList<SpinHistoryGetLuckyResponse>();
        for (LuckySpinHistory spinHistory : spinHistories) {
//            spinHistory.setUserInfo(null);
            SpinHistoryGetLuckyResponse historyResponse = new SpinHistoryGetLuckyResponse();
            historyResponse.setDate(DateUtils.toStringFormDate(spinHistory.getCreateOn(), "HH:mm:ss dd-MM-yyyy"));
            if (StringUtils.isEmpty(spinHistory.getUserName())) {
                spinHistory.setUserName(spinHistory.getUserInfo().getUserVTC().getUsername());
                spinHistoryRepo.save(spinHistory);
            }
            historyResponse.setUserName(StringUtils.hiddenCharString(spinHistory.getUserName(), 3));
            
//            if (StringUtils.isEmpty(spinHistory.getPhoneNumber())) {
//                if (!StringUtils.isEmpty(spinHistory.getUserInfo().getPhoneNumber())) {
//                    spinHistory.setPhoneNumber(StringUtils.hiddenCharString(spinHistory.getUserInfo().getPhoneNumber(), 3));
//                    LOGGER.info("spinHistory.getUserInfo().GET ID ===================== , {}", spinHistory.getUserInfo().getId() );
//                    spinHistoryRepo.save(spinHistory);
//                }
//            }
            if (!StringUtils.isEmpty(spinHistory.getPhoneNumber()))
                historyResponse.setPhone(spinHistory.getPhoneNumber());

            historyResponse.setItemName(spinHistory.getItem().getName());
            if (spinHistory.getItem().isHighLights()) historyResponse.setDescription(Constant.STATUS_HIGHLIGHTS);
            
            historyResponses.add(historyResponse);
            if (StringUtils.isEmpty(typeGift)) {
                continue;
            }
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
        
        List<LuckySpinItemOfLuckySpin> itemOfSpins = luckySpin.getSpinItems();
        if (CollectionUtils.isEmpty(itemOfSpins)) {
            return new ArrayList<GiftQuantityExistResponse>();
        }
        
        List<GiftQuantityExistResponse> responses = new ArrayList<GiftQuantityExistResponse>();
        
      //sort response follow value
        itemOfSpins = itemOfSpins.stream().sorted(
                (o1, o2) -> ((Long) o2.getItem().getValue()).compareTo(o1.getItem().getValue()))
                .collect(Collectors.toList());
        
        itemOfSpins.forEach(spinItem -> {
            String itemType = spinItem.getItem().getType();
            if (!itemType.equals(Constant.LUCKYSPIN_GIFT_ACTION)) {
                long itemValue = spinItem.getItem().getValue();
//                int giftReceivedRael = repo.countByItemType(luckySpin, itemType, itemValue);
                int giftReceivedFake = spinHistoryFakeRepo.countByLuckySpinIdAndItemTypeAndItemValue(luckySpin.getId(), itemType, itemValue);
                int giftExist = spinItem.getTotalQuantity() - giftReceivedFake;
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
