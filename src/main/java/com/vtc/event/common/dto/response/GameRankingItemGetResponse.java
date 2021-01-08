/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

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
public class GameRankingItemGetResponse {

    private String itemName;

    private String description;

    private String itemIconUrl;

    private String itemType;

    private int    itemQuantity;

    private int    itemValue;

    private String winningTittle;

}
