/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.game.service;

import com.vtc.event.common.dao.entity.GameRanking;
import com.vtc.event.common.dto.request.GameRankingAwardRequest;
import com.vtc.event.common.dto.response.GameRankingGetResponse;
import com.vtc.event.common.dto.response.GameRankingGiftResponse;
import com.vtc.event.common.service.AbstractInterfaceService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 12, 2020
 */
public interface GameRankingService extends AbstractInterfaceService<GameRanking, Long> {
    
    GameRankingGetResponse getGameRanking(Long serviceId, String week);
    
    GameRankingGiftResponse getRankingGift(Long serviceId);
    
    String rankingAward(GameRankingAwardRequest request);

}
