/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service;

import java.util.Date;
import java.util.List;

import com.vtc.event.common.dao.entity.FundsCardScoin;
import com.vtc.event.common.dto.response.GetCardScoinResponse;
import com.vtc.event.common.dto.response.TopupCardHistoryResponse;
import com.vtc.event.common.exception.ScoinBusinessException;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 9, 2019
 */
public interface CommonCardScoinService extends AbstractInterfaceService<FundsCardScoin, Long> {
    
    public FundsCardScoin buyCard(String valueCard, Integer quantity) throws ScoinBusinessException;
    
    public GetCardScoinResponse getCard(String valueCard, String VTCTranID) throws ScoinBusinessException;
    
    public void topupScoin(long valueScoin, String userName) throws ScoinBusinessException;
    
    List<TopupCardHistoryResponse> getTopupCardHistory(String scoinToken, Date topupDate, Long serviceId);
    
}
