/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 16, 2019
 */
@Setter
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TopupCardUserHistoryGet {
    
    private Long   scoinId;

    private String cardType;

    private long   cardValue;

    private long   scoinTransId;

    private Long   serviceId;

    private String sign;

}
