/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.request;

import com.vtc.event.common.dto.request.AbstractResquest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 8, 2019
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuckySpinGetRequest extends AbstractResquest {

    private Long spinId;

}
