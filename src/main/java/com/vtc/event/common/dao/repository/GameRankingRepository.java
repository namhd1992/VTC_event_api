/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.GameRanking;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 15, 2020
 */
@Repository
public interface GameRankingRepository extends JpaRepository<GameRanking, Long> {
    
    List<GameRanking> findAllByCreateBy(long createBy);
    
    GameRanking findByIdAndCreateBy(Long id, long createBy);
    
    GameRanking findByServiceId(Long serviceId);

}
