/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service;

import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dto.request.XuExchangeRequest;
import com.vtc.event.common.dto.response.UserXuInfoResponse;
import com.vtc.event.common.exception.ScoinBusinessException;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 14, 2019
 */
public interface PaymentService {
    
    long updateBalanceSoin(UserInfo userInfo) throws ScoinBusinessException;
    
    void exchangeScoin(UserInfo userInfo, String typeExchange, long amount, String description);

    UserXuInfoResponse getBalanceXu(Long scoinId) throws ScoinBusinessException;
    
    UserXuInfoResponse exchangeXu(XuExchangeRequest request, String type)
            throws ScoinBusinessException;
    
}
