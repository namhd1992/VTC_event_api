/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinRadiusPersonalTopup;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Nov 22, 2019
 */
public interface LuckySpinRadiusPersonalTopupRepository
        extends JpaRepository<LuckySpinRadiusPersonalTopup, Long> {
    
    List<LuckySpinRadiusPersonalTopup> findByLuckySpinAndPersonalTopupLandmark(LuckySpin luckySpin, long personalTopupLandmark);

}
