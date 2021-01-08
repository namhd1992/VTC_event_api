/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Sep 19, 2019
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSSOScoinResponse {

    private Long   scoinId;

    private String accountName;

    private String userIPAndress;

    private Date   loginTime;

}
