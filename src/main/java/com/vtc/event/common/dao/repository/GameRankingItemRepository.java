/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.GameRankingItem;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 22, 2020
 */
@Repository
public interface GameRankingItemRepository extends JpaRepository<GameRankingItem, Long> {

}
