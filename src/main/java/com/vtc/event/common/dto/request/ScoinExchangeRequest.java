/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Oct 3, 2019
 */
@Setter
@Getter
@NoArgsConstructor
public class ScoinExchangeRequest {
    
    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("access_token")
    private String accessToken;

    private String description;

    private long   amount;

    private long   time;

    private String sign;

}
