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
 * Nov 28, 2019
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LuckySpinTurnBuyHistoryResponse {

    private String sourceTurn;

    private int    receivedTurn;

    private String date;

}
