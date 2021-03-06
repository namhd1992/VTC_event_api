/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.Giftcode;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 28, 2019
 */
@Repository
public interface GiftcodeRepository extends JpaRepository<Giftcode, Long> {
    
//    List<Giftcode> findByItemSpinAndUserLostIsNullAndDeviceIDIsNull(LuckySpinItem itemSpin);

}
