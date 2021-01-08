/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.game.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.Game;
import com.vtc.event.common.dao.entity.GameRanking;
import com.vtc.event.common.dao.entity.GameTag;
import com.vtc.event.common.dao.repository.GameRankingRepository;
import com.vtc.event.common.dao.repository.GameRepository;
import com.vtc.event.common.dao.repository.GameTagRepository;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.exception.ScoinNotFoundEntityException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.game.service.GameService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 31, 2019
 */
@Service("gameService")
public class GameServiceImpl extends AbstractService<Game, Long, GameRepository>
        implements GameService {
    
    @Autowired
    GameTagRepository gameTagRepo;
    
    @Autowired
    GameRankingRepository gameRankingRepo;

    @Override
    public Optional<Game> getGameByServiceId(Long serviceId) throws ScoinBusinessException {
        if (ObjectUtils.isEmpty(serviceId)) {
            throw new ScoinInvalidDataRequestException();
        }
        
        Optional<Game> optGame = repo.findByScoinGameIdAndStatus(serviceId, Constant.STATUS_ACTIVE);
        if (optGame.isEmpty()) {
            throw new ScoinNotFoundEntityException("Not found Game by this id or inactive");
        }
        
        Game game = optGame.get();
        GameRanking gameRanking = gameRankingRepo.findByServiceId(game.getScoinGameId());
        if (ObjectUtils.isEmpty(gameRanking)) {
            game.setGameRanking(false);
        } else {
            game.setGameRanking(true);
        }
        
        return Optional.of(game);
    }

    @Override
    public List<Game> getGameByGameTags(List<Long> gameTagIds) throws ScoinBusinessException {
        if (CollectionUtils.isEmpty(gameTagIds)) throw new ScoinInvalidDataRequestException();
        
        List<GameTag> gameTags = gameTagRepo.findAllByIdIn(gameTagIds);
        if (CollectionUtils.isEmpty(gameTags)) throw new ScoinNotFoundEntityException("Not Found game tag by this ids");
        
        List<Game> responses = new ArrayList<>();
        if (gameTags.size() < 2) {
            responses = repo.findByTagsList(gameTags.get(0));
        } else {
            List<Game> games = repo.findAllByTagsListIn(gameTags);
            if (CollectionUtils.isEmpty(games)) return new ArrayList<Game>();
            for (Game game : games) {
                if (game.getTagsList().containsAll(gameTags)) {
                    responses.add(game);
                }
            }
        }
        
        if (CollectionUtils.isEmpty(responses)) return new ArrayList<Game>();
        
      return responses;
    }

    @Override
    public List<Game> getAllGameActive() throws ScoinBusinessException {
        List<Game> games = repo.findByStatus(Constant.STATUS_ACTIVE);
        if (CollectionUtils.isEmpty(games)) return new ArrayList<Game>();
        return games;
    }

}
