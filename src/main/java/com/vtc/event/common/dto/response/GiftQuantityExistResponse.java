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
 * Nov 27, 2019
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftQuantityExistResponse {

    private String itemName;

    private int    itemQuantityExist;

    private String type;

}
