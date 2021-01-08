/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 17, 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRankingUserGetResponse {

    private String userName;

    private String server;

    private long   scoinTopup;

    private int    position;

    private String rankName;

    private String rankIconUrl;

}
