/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.TransactionHistory;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 13, 2019
 */
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

}
