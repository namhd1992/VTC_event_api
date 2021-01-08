/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.userInfo.service;

import java.util.Optional;

import com.vtc.event.common.dao.entity.UserVTC;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.service.AbstractInterfaceService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
public interface UserVTCService extends AbstractInterfaceService<UserVTC, Long> {

    public Optional<UserVTC> findByUserName(String userName) throws ScoinBusinessException;
    
    public Optional<UserVTC> findByScoinId(Long scoinId);

}
