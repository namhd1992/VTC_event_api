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
 * Apr 20, 2020
 */
@Getter
@Setter
@NoArgsConstructor
public class GameRankItemResponse {
    
    private Long    itemId;

    private String  itemType;

    private String  itemIconUrl;

    private String  itemName;

    private String  content;

    private int     value;

    private int     quantity;

    private boolean received;

    private String  winningTittle;

}
