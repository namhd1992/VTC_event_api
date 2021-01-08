/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.LuckySpinHistoryFake;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 26, 2019
 */
@Repository
public interface LuckySpinHistoryFakeRepository extends JpaRepository<LuckySpinHistoryFake, Long> {
    
    List<LuckySpinHistoryFake> findByLuckySpinId(Long luckySpinId);
    
    int countByLuckySpinIdAndItemTypeAndItemValue(Long luckySpinId, String itemType, long itemValue);
    
    int countByLuckySpinIdAndItemValueAndCreateOnBetween(Long luckySpinId, 
                                                         long itemValue, 
                                                         Date startDate,
                                                         Date endDate);

}
