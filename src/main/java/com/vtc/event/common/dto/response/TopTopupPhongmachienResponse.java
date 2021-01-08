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
 * Apr 17, 2020
 */
@Setter
@Getter
@NoArgsConstructor
public class TopTopupPhongmachienResponse {
    
    private int    position;

    private int    accountid;

    private long   roleid;

    private String rolename;

    private long   serverid;

    private int    level;

    private float  totalpayment;
}
