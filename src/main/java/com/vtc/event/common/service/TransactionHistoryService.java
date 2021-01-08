/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service;

import com.vtc.event.common.dao.entity.TransactionHistory;
import com.vtc.event.common.dto.request.TransactionHistoryCreateRequest;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jun 5, 2019
 */
public interface TransactionHistoryService
        extends AbstractInterfaceService<TransactionHistory, Long> {
    
    TransactionHistory createTransactionHistory(TransactionHistoryCreateRequest request);

}
