/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.GameRankingHistory;
import com.vtc.event.common.dao.entity.GameRankingItem;
import com.vtc.event.common.dao.entity.UserInfo;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 20, 2020
 */
@Repository
public interface GameRankingHistoryRepository extends JpaRepository<GameRankingHistory, Long> {
    
    GameRankingHistory findByUserInfoAndItem(UserInfo userInfo, GameRankingItem item);
    
    GameRankingHistory findByUserInfoAndItemAndCreateOnBetween(UserInfo 
                                                             userInfo, 
                                                             GameRankingItem item, 
                                                             Date startDate, 
                                                             Date endDate);

}
