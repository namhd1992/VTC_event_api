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
    
    private long turnCanBuy;// turn can buy in day

    private long scoinTopupWallet; // total scoin by topup from wallet to game
    
    private long scoinTopupCardToGame; // total scoin by topup from wallet to game

    private long scoinBalanceRounding; // scoin exist to buy 1 turn

}
