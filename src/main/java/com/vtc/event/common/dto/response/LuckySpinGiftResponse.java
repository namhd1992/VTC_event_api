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
 * Jan 8, 2020
 */
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinGiftResponse {
    
    private Long    luckySpinGiftId;

    private String  name;

    private Integer maxQuantity;

    private int     setPoint;

    private String  typeGift;

    private long    value;

    private int     quantity;

    private String  description;

}
