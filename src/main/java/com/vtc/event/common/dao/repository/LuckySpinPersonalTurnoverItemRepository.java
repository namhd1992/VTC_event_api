/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.LuckySpinItemOfLuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinPersonalTurnoverItem;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Mar 4, 2020
 */
public interface LuckySpinPersonalTurnoverItemRepository
        extends JpaRepository<LuckySpinPersonalTurnoverItem, Long> {
    
    LuckySpinPersonalTurnoverItem findBySpinItem(LuckySpinItemOfLuckySpin luckySpinItemOfLuckySpin);

}
