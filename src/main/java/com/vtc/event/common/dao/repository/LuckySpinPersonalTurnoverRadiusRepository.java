/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.LuckySpinPersonalTurnoverRadius;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Nov 22, 2019
 */
public interface LuckySpinPersonalTurnoverRadiusRepository
        extends JpaRepository<LuckySpinPersonalTurnoverRadius, Long> {
    
//    List<LuckySpinPersonalTurnoverRadius> findByLuckySpinAndPersonalTopupLandmark(LuckySpin luckySpin, long personalTopupLandmark);

}
