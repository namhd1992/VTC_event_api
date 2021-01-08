/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.game.service;

import java.util.List;
import java.util.Optional;

import com.vtc.event.common.dao.entity.Game;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.service.AbstractInterfaceService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 31, 2019
 */
public interface GameService extends AbstractInterfaceService<Game, Long> {
    
    List<Game> getAllGameActive() throws ScoinBusinessException;
    
    Optional<Game> getGameByServiceId(Long serviceId) throws ScoinBusinessException;
    
    List<Game> getGameByGameTags(List<Long> gameTagIds) throws ScoinBusinessException;
    
}
