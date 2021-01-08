/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 12, 2020
 */
@Getter
@Setter
@NoArgsConstructor
public class GameRankingGetResponse {

    private String                           gameRankingName;

    private String                           gameRule;
    
    private int                              myPosition;

    private String                           myGameName;

    private String                           myRankName;
    
    private String                           myRankIconUrl;

    private String                           myGameServer;

    private List<GameRankingUserGetResponse> users;

//    private List<GameRankingRankGetResponse> ranks;

}
