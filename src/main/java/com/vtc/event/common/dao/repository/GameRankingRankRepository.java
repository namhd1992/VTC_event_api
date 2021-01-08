/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.GameRanking;
import com.vtc.event.common.dao.entity.GameRankingRank;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 16, 2020
 */
@Repository
public interface GameRankingRankRepository extends JpaRepository<GameRankingRank, Long> {
    
    List<GameRankingRank> findByGameRanking(GameRanking gameRanking, Pageable pageable);
    
    int countByGameRanking(GameRanking gameRanking);

}
