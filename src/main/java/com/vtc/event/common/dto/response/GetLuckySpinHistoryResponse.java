/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jan 17, 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetLuckySpinHistoryResponse {

    private List<SpinHistoryGetLuckyResponse> golds;

    private List<SpinHistoryGetLuckyResponse> scoin;
}
