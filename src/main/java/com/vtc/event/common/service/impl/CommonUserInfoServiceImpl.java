/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vtc.event.EnvironmentKey;
import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.GroupRole;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.entity.UserVTC;
import com.vtc.event.common.dao.repository.GroupRoleRepository;
import com.vtc.event.common.dao.repository.SessionRepository;
import com.vtc.event.common.dao.repository.UserInfoRepository;
import com.vtc.event.common.dao.repository.UserVTCRepository;
import com.vtc.event.common.dto.response.GetProfileByAccessTokenScoinDataResponse;
import com.vtc.event.common.dto.response.ScoinBasicResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.service.CommonUserInfoService;
import com.vtc.event.common.service.PaymentService;
import com.vtc.event.common.utils.ApiExchangeServiceUtil;
import com.vtc.event.common.utils.EncryptAndDecryptUtils;
import com.vtc.event.common.utils.StringUtils;


/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Aug 28, 2019
 */
@Service("commonUserInfoService")
public class CommonUserInfoServiceImpl extends AbstractService<UserInfo, Long, UserInfoRepository>
        implements CommonUserInfoService {
    
    @Autowired
    SessionRepository sessionRepo;
    
    @Autowired
    UserVTCRepository userVTCRepo;
    
    @Autowired
    GroupRoleRepository groupRoleRepo;
    
    @Autowired
    PaymentService paymentService;
    
    private transient String    SCOIN_SIGNIN_GET_PROFILE_URL;
    private transient String    SCOIN_SIGNIN_KEY_DECRYPT_TRIPLE_DES;

    public CommonUserInfoServiceImpl(Environment env) {
        SCOIN_SIGNIN_GET_PROFILE_URL = env
                .getProperty(EnvironmentKey.SANDBOX_SCOIN_SIGNIN_GET_PROFILE_URL.getKey());
        SCOIN_SIGNIN_KEY_DECRYPT_TRIPLE_DES = env
                .getProperty(EnvironmentKey.SANDBOX_SCOIN_SIGNIN_KEY_DECRYPT_TRIPLE_DES.getKey());
    }
    
    @Override
    public UserInfo getByScoinId(Long scoinId) throws ScoinBusinessException {
        return repo.getByScoinId(scoinId);
    }

    @Override
    public Long scoinSSO(String accessToken) throws ScoinBusinessException {
        if (accessToken.isBlank()) return null;

        // Decrypt tripleDES data from ud of scoin
        String tokenData = null;
        String decryptTokenData = null;
        try {
            tokenData = URLDecoder.decode(accessToken, StandardCharsets.UTF_8);
            decryptTokenData = EncryptAndDecryptUtils
                    .tripleDESDecrypt(SCOIN_SIGNIN_KEY_DECRYPT_TRIPLE_DES, tokenData);
        } catch (Exception e) {
            return null;
        }
        
        if (decryptTokenData.isBlank()) return null;
        
        //Example: 1200369966|payment.test02|118.107.71.29|1569899190|k1200369966.1569985589.NwA1AFgAawBUADMAKwBoAGoAcgA2AHEAbwB3AEMAawBjADkAWQBvADMAUQA9AD0A
        List<String> data = Arrays.asList(decryptTokenData.split("[|]"));

        //Get user info from scoin token
        String scoinToken = data.get(4);
        String getProfileURL = SCOIN_SIGNIN_GET_PROFILE_URL + scoinToken;
        ScoinBasicResponse<GetProfileByAccessTokenScoinDataResponse> getProfileResponse = ApiExchangeServiceUtil
                .get(getProfileURL,new TypeReference<ScoinBasicResponse<GetProfileByAccessTokenScoinDataResponse>>() {});
        if (ObjectUtils.isEmpty(getProfileResponse) || getProfileResponse.getCode() < 1) {
            return null;
        }

        GetProfileByAccessTokenScoinDataResponse profile = getProfileResponse.getData();
        Optional<UserVTC> optUserVTC = userVTCRepo.findByScoinId(Long.parseLong(data.get(0)));
        UserVTC userVTC = new UserVTC();
        if (!optUserVTC.isPresent()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(profile.getEmail());

            if (!StringUtils.isEmpty(profile.getFacebookName())) {
                userInfo.setFullName(profile.getFacebookName());
            } else {
                userInfo.setFullName(profile.getAccountName());
            }

            if (userInfo.getPhoneNumber() == null || userInfo.getPhoneNumber().length() < 1)
                userInfo.setPhoneNumber(profile.getMobile());
            userInfo.setScoinToken(scoinToken);
            userInfo.setOtpKey("");

            // Get role default
            GroupRole groupRole = groupRoleRepo.findByName(Constant.ROLE_USER);
            userInfo.setGroupRole(groupRole);
            // Save user info
            userInfo = repo.save(userInfo);

            // Create user scoin
            userVTC.setScoinId(profile.getAccountId());
            userVTC.setUsername(profile.getAccountName());
            userVTC.setScoin(0);
            userVTC.setStatus(Constant.STATUS_ACTIVE);
            userVTC.setUserInfo(userInfo);
            userVTC.setFirstLogin(true);
            userVTC = userVTCRepo.save(userVTC);

        } else {
            userVTC = optUserVTC.get();
            userVTC.getUserInfo().setScoinToken(scoinToken);
            repo.save(userVTC.getUserInfo());
            
            paymentService.updateBalanceSoin(userVTC.getUserInfo());
        }
        
        return Long.parseLong(data.get(0));
    }

}
