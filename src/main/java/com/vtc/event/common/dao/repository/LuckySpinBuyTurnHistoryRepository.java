/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinBuyTurnHistory;
import com.vtc.event.common.dao.entity.UserInfo;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Nov 20, 2019
 */
public interface LuckySpinBuyTurnHistoryRepository
        extends JpaRepository<LuckySpinBuyTurnHistory, Long> {
    
    LuckySpinBuyTurnHistory findByUserInfoAndLuckySpinAndAndBuyValueAndCreateOnAfterAndCreateOnBefore(UserInfo userInfo, 
                                                                                       LuckySpin luckySpin, 
                                                                                       long buyValue,
                                                                                       Date startDay,
                                                                                       Date endDay);
    
    List<LuckySpinBuyTurnHistory> findByUserInfoOrderByCreateOnDesc(UserInfo userInfo, Pageable pageable);
    
    int countByUserInfo(UserInfo userInfo);

}
