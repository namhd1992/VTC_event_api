/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.userInfo.service;

import java.util.List;

import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.entity.UserVTC;
import com.vtc.event.common.dto.request.SignInScoinRequest;
import com.vtc.event.common.dto.response.ProfileGetResponse;
import com.vtc.event.common.dto.response.SigninScoinResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.service.AbstractInterfaceService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
public interface UserInfoService extends AbstractInterfaceService<UserInfo, Long> {
    
    public SigninScoinResponse signin(SignInScoinRequest request);

    public UserInfo getUserInfoByUserName(String userName) throws ScoinBusinessException;
    
    public ProfileGetResponse getMyProfile() throws ScoinBusinessException;
    
    public UserInfo getByScoinId(Long ScoinId) throws ScoinBusinessException;
    
    public UserVTC exchangeScoin(String typeExchange, int amount) throws ScoinBusinessException;
    
    public List<String> exportCardScoin(long valueCard, int quantity);

}
