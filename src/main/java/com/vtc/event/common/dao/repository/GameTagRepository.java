/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.GameTag;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jun 9, 2019
 */
public interface GameTagRepository extends JpaRepository<GameTag, Long> {
    
    List<GameTag> findAllByIdIn(List<Long> ids);

}
