/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinHistory;
import com.vtc.event.common.dao.entity.LuckySpinItem;
import com.vtc.event.common.dao.entity.UserInfo;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 7, 2019
 */
@Repository
public interface LuckySpinHistoryRepository extends JpaRepository<LuckySpinHistory, Long>{
    
    List<LuckySpinHistory> findByUserInfoAndLuckySpinOrderByCreateOnDesc(UserInfo userInfo,
                                                                                       LuckySpin luckySpin, 
//                                                                                       String typeItem, 
                                                                                       Pageable pageable);
    
    int countByItemAndCreateOnBetween(LuckySpinItem luckySpinItem,
                                      Date startDate,
                                      Date endDate);
    
    int countByUserInfoAndItem(UserInfo userInfo, LuckySpinItem luckySpinItem);
    
    
    @Query(value ="select s from LuckySpinHistory s where luckySpin = ?1 "
            + " and (s.userInfo = ?2 or ?2 is null) "
            + " and (s.createOn > ?3 or ?3 is null) "
            + " and s.description in ?4"
//            + " and s.turnType = ?5 "
            + " order by s.createOn desc")
    List<LuckySpinHistory> getByLuckySpinAndUserInfoAndTypeGift(LuckySpin luckySpin,
                                                          UserInfo userInfo,
                                                          Date limitDate,
                                                          List<String> type,
//                                                          String turnType,
                                                          Pageable pageable);
    
    int countByUserInfoAndLuckySpin(UserInfo userInfo, LuckySpin luckySpin);
    
    int countByUserInfoAndLuckySpinAndDescriptionOrderByCreateOnDesc(UserInfo userInfo,
                                                                     LuckySpin luckySpin, 
                                                                     String typeItem);
    
    @Query(value ="select count(s) from LuckySpinHistory s where luckySpin = ?1 "
            + " and (s.userInfo = ?2 or ?2 is null)"
            + " and (s.createOn > ?3 or ?3 is null)"
            + " and s.description in ?4"
            + " and (s.value = ?5 or ?5 is null)"
            + " and (convert(s.createOn, DATE) = ?6 or ?6 is null)")
    int countByLuckySpinAndUserInfoAndTypeGift(LuckySpin luckySpin,
                                              UserInfo userInfo,
                                              Date limitDate,
                                              List<String> type,
                                              Long value,
                                              Date day);
    
    @Query(value = "select count(s.id) from LuckySpinHistory s where luckySpin = ?1 "
            + " and s.item.type = ?2"
            + " and (s.item.value = ?3 or ?3 = null )")
    int countByItemType(LuckySpin luckySpin, String itemType, Long value);
    
    
//    @Query(value = "SELECT COUNT(s.id) FROM LuckySpinHistory s" 
//            + " INNER JOIN LuckySpinItemOfLuckySpin io ON s.item = io.item" 
//            + " INNER JOIN LuckySpinRadiusPersonalTopup p ON io.id = p.spinItem" 
//            + " WHERE p.personalTopupLandmark = ?1"
//                  + " AND p.isMainItem = 1 AND s.luckySpin = ?2"
//                  + " AND s.userInfo = ?3")
//    int countMainItemReceived (long personalTopupLandmark, LuckySpin luckySpin , UserInfo userInfo);

}
