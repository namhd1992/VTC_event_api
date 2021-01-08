/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 20, 2020
 */
@Setter
@Getter
@NoArgsConstructor
public class GameRankingGiftResponse {
  
    private Date endDateReceivedGift;
    
    private List<GameRankingRankGetResponse> ranks;

}
