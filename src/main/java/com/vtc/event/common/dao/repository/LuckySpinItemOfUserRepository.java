/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinItem;
import com.vtc.event.common.dao.entity.LuckySpinItemOfUser;
import com.vtc.event.common.dao.entity.UserInfo;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jan 3, 2020
 */
public interface LuckySpinItemOfUserRepository extends JpaRepository<LuckySpinItemOfUser, Long> {
    
    List<LuckySpinItemOfUser> findByLuckySpinAndUserInfo(LuckySpin luckySpin, UserInfo userInfo);
    
    LuckySpinItemOfUser findByLuckySpinAndUserInfoAndLuckySpinItem(LuckySpin luckySpin, 
                                                                   UserInfo userInfo, 
                                                                   LuckySpinItem luckySpinItem);
    
    int countByLuckySpinAndUserInfoAndLuckySpinItem(LuckySpin luckySpin, 
                                                    UserInfo userInfo, 
                                                    LuckySpinItem luckySpinItem);

}
