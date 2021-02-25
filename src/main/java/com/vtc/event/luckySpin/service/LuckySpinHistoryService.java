/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import com.vtc.event.common.dao.entity.LuckySpinHistory;
import com.vtc.event.common.dto.request.AbstractResquest;
import com.vtc.event.common.dto.response.GetLuckySpinHistoryResponse;
import com.vtc.event.common.dto.response.GiftQuantityExistResponse;
import com.vtc.event.common.dto.response.LuckySpinTurnBuyHistoryResponse;
import com.vtc.event.common.dto.response.LuckySpinTurnHistoryResponse;
import com.vtc.event.common.dto.response.SpinHistoryGetLuckyResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.service.AbstractInterfaceService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 2, 2019
 */
public interface LuckySpinHistoryService extends AbstractInterfaceService<LuckySpinHistory, Long> {
    
    GetLuckySpinHistoryResponse getSpinGiftHistory(Long luckySpinId, AbstractResquest request)
            throws ScoinBusinessException;
    
    List<SpinHistoryGetLuckyResponse> getSpinHistoryByType(Long luckySpinId, String typeGift, AbstractResquest request);
    
    List<SpinHistoryGetLuckyResponse> getSpinTudo(Long luckySpinId, List<String> typeGifts, AbstractResquest request);
    
    List<LuckySpinTurnHistoryResponse> getOpenWordSpinHistory(Long luckySpinId, AbstractResquest request);
    
    List<LuckySpinTurnBuyHistoryResponse> getSpinTurnBuyHistory(Long luckySpinId, AbstractResquest request);
    
    int countSpinHistory(Long luckySpinId, List<String> typeGifts, Long value, Date date);
    
    int countSpinHistoryHasUser(Long luckySpinId, List<String> typeGifts);
    
    int countTurnSpinHistory(Long luckySpinId);
    
    int countSpinTurnBuyHistory(Long luckySpinId);
    
    String createSpinHistoryFake(MultipartFile file) throws IOException, InvalidFormatException ;
    
    List<GiftQuantityExistResponse> giftQuantityExist(Long luckySpinId) throws ScoinBusinessException;

}
