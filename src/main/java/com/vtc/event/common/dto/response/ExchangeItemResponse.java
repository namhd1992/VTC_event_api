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
 * Jan 9, 2020
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeItemResponse {
    
    private Long   luckySpinGiftId;

    private String name;

    private String typeGift;

    private long   value;

    private int    quantity;

}
