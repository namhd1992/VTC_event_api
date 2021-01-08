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
 * Nov 25, 2019
 */
@Setter
@Getter
@NoArgsConstructor
public class UserBuyTurnResponse {
    
    private long totalTopupOfUser; // total scoin by topup from wallet to game

    private long accumulationPoint; // turn by top by topup from wallet to game

    private long cardBalanceRounding; // card exist to buy 1 turn
    
    private long scoinBalanceRounding; // scoin exist to buy 1 turn

}
