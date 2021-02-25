/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinItem;
import com.vtc.event.common.dao.entity.TopupCardHistory;
import com.vtc.event.common.dao.entity.TurnoverLandmark;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.repository.TopupCardHistoryRepository;
import com.vtc.event.common.dao.repository.TurnoverLandmarkRepository;
import com.vtc.event.luckySpin.service.LuckySpinHistoryService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 18, 2019
 */
@Component
public class ConditionTurnover {
    
//    @Autowired
//    LuckySpinSettingRepository luckySpinSettingRepo;
    
    @Autowired
    TurnoverLandmarkRepository turnoverRepo;
    
    @Autowired
    LuckySpinHistoryService luckySpinHistoryService;
    
    @Autowired
    TopupCardHistoryRepository topupCardHistoryRepo;
    
    static Logger             LOGGER = LoggerFactory.getLogger(ConditionTurnover.class);
    
    public boolean checkPickItemFolowTurnover(LuckySpin luckySpin, long totalTurnover,LuckySpinItem item) {
    
        boolean flagItemQuantityBalance = false;
        List<TurnoverLandmark> turnoverLandmarks = turnoverRepo.
                findByGameAndGameIdAndItemId(luckySpin.getType(), luckySpin.getId(), item.getId());
        if (CollectionUtils.isEmpty(turnoverLandmarks)) return true;
        
        for(TurnoverLandmark turnoverLandmark : turnoverLandmarks) {
            if (totalTurnover <= turnoverLandmark.getTurnoverLandmark()) {
                flagItemQuantityBalance = true;
                
                List<String> typeGifts = new ArrayList<String>();
                typeGifts.add(item.getType());
                int TOTAL_QUANTITY_ITEM_RECEIVED = 0;
                
                if(turnoverLandmark.getLimitType().equals(Constant.LUCKYSPIN_TURNOVER_LIMIT_TYPE_DAY)) {
                    if (item.getType().equals(Constant.LUCKYSPIN_GIFT_WORD)) {
                        TOTAL_QUANTITY_ITEM_RECEIVED = luckySpinHistoryService.
                                countSpinHistory(luckySpin.getId(), typeGifts, null, new Date());
                    } else {
                        TOTAL_QUANTITY_ITEM_RECEIVED = luckySpinHistoryService.
                                countSpinHistory(luckySpin.getId(), typeGifts, item.getValue(), new Date());
                    }
                    
                }
                
                LOGGER.info("TOTAL_QUANTITY_ITEM_RECEIVED ===================== : {}", TOTAL_QUANTITY_ITEM_RECEIVED);
                
                if(turnoverLandmark.getLimitType().equals(Constant.LUCKYSPIN_TURNOVER_LIMIT_TYPE_EVENT)) {
                    if (item.getType().equals(Constant.LUCKYSPIN_GIFT_WORD)) {
                        TOTAL_QUANTITY_ITEM_RECEIVED = luckySpinHistoryService.
                                countSpinHistory(luckySpin.getId(), typeGifts, null, null);
                    } else {
                        TOTAL_QUANTITY_ITEM_RECEIVED = luckySpinHistoryService.
                                countSpinHistory(luckySpin.getId(), typeGifts, item.getValue(), null);
                    }
                    
                }
                
              //Limit item by TURNOVER
                if (TOTAL_QUANTITY_ITEM_RECEIVED >= turnoverLandmark.getLimitQuantity()) {
                    flagItemQuantityBalance = false;
                    break;
                }
                
                break;
            }
        };
        
        return flagItemQuantityBalance;
        
    }

    public boolean checkPickUserFolowTopupCard(UserInfo userInfo, LuckySpinItem item) {
        long scoinId = userInfo.getUserVTC().getScoinId();
        if (scoinId < 1) {
            LOGGER.info("Accoout Empty or error");
            return false;
        }
        Long sumTopupCard = topupCardHistoryRepo.sumCardValueByScoinId(scoinId);
        if (ObjectUtils.isEmpty(sumTopupCard)) sumTopupCard = (long) 0;
        if (sumTopupCard < item.getValue() * 10) {
            return false;
        }
        
        return true;
        
        
    }
    
    public long pickRatioItemFolowPersonalTurnover(long userTopupCost) {
        List<Long> personalTurnovers = new ArrayList<Long>();
        personalTurnovers.add((long) 25000000);
        
        long landmark = 0;
        for (int i = 0; i < personalTurnovers.size() ; i++) {
            if (personalTurnovers.get(i) < userTopupCost) continue;
            if (personalTurnovers.get(i) == userTopupCost) {
                landmark = personalTurnovers.get(i);
                break;
            }
            
            landmark = personalTurnovers.get(i - 1);
            break;
        };
        
        return landmark;
    }
    
    public int turnSpinAddTopupCardScoin(TopupCardHistory topupCard) {
        long cardValue = topupCard.getTotalPayment();
        int turnAddNew = 0;
        if (cardValue < 50000) return 0;
        if (cardValue < 1000000) {
            turnAddNew = (int) cardValue / 50000;
        } else if (cardValue < 5000000) {
            turnAddNew = ((int) cardValue / 50000) + (int) cardValue / 500000;
        } else if (cardValue == 5000000) {
            turnAddNew = ((int) cardValue / 10000) + (int) (2 * cardValue / 500000);
        }
        return turnAddNew;
    }

}
