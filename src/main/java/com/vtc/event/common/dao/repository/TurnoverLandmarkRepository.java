/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.TurnoverLandmark;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Sep 9, 2019
 */
@Repository
public interface TurnoverLandmarkRepository extends JpaRepository<TurnoverLandmark, Long> {
    
    List<TurnoverLandmark> findByGameAndGameIdAndItemId(String game, Long gameId, Long itemId);

}
