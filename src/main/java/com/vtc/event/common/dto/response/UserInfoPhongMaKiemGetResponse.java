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
 * Apr 18, 2020
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInfoPhongMaKiemGetResponse {
    
    private long   serverid;

    private String servername;

    private long   roleid;

    private String rolename;
}
