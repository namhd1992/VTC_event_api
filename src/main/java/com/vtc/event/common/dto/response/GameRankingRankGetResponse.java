/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 17, 2020
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRankingRankGetResponse {

    private String                     rankName;

    private String                     decription;

    private String                     rankIconUrl;

    private int                        rankPosition;

    private int                        rankQuantity;

    private boolean                    myRank;

    private List<GameRankItemResponse> items;

}
