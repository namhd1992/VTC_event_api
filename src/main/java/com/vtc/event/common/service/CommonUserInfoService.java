/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service;

import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.exception.ScoinBusinessException;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Aug 28, 2019
 */
public interface CommonUserInfoService extends AbstractInterfaceService<UserInfo, Long> {
    
    public UserInfo getByScoinId(Long ScoinId) throws ScoinBusinessException;
    
    public Long scoinSSO(String accessToken) throws ScoinBusinessException;

}
