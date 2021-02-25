/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.entity.UserTurnSpin;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 7, 2019
 */
@Repository
public interface UserTurnSpinRepository extends JpaRepository<UserTurnSpin, Long> {
    
    Optional<UserTurnSpin> findByUserInfoAndLuckySpin(UserInfo userInfo, LuckySpin luckySpin);
  
}
