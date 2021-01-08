/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.GameItem;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 12, 2020
 */
public interface GameItemRepository extends JpaRepository<GameItem, Long> {

}
