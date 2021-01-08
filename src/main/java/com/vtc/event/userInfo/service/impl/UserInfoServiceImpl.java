/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.userInfo.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vtc.event.EnvironmentKey;
import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.FundsCardScoin;
import com.vtc.event.common.dao.entity.GroupRole;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dao.entity.UserVTC;
import com.vtc.event.common.dao.repository.GroupRoleRepository;
import com.vtc.event.common.dao.repository.UserInfoRepository;
import com.vtc.event.common.dto.request.SignInScoinRequest;
import com.vtc.event.common.dto.response.GetAccessTokenScoinResponse;
import com.vtc.event.common.dto.response.GetProfileByAccessTokenScoinDataResponse;
import com.vtc.event.common.dto.response.ProfileGetResponse;
import com.vtc.event.common.dto.response.ProfileTokenResponse;
import com.vtc.event.common.dto.response.ScoinBasicResponse;
import com.vtc.event.common.dto.response.SigninScoinResponse;
import com.vtc.event.common.exception.ScoinBusinessException;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.exception.ScoinInvalidDataRequestException;
import com.vtc.event.common.exception.ScoinNotFoundEntityException;
import com.vtc.event.common.exception.ScoinUnAuthorizationException;
import com.vtc.event.common.service.AbstractService;
import com.vtc.event.common.service.CommonCardScoinService;
import com.vtc.event.common.service.PaymentService;
import com.vtc.event.common.utils.ApiExchangeServiceUtil;
import com.vtc.event.common.utils.DateUtils;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.common.utils.StringUtils;
import com.vtc.event.userInfo.service.UserInfoService;
import com.vtc.event.userInfo.service.UserVTCService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends AbstractService<UserInfo, Long, UserInfoRepository>
        implements UserInfoService {
    
    @Autowired
    UserVTCService         userVTCService;

    @Autowired
    GroupRoleRepository    groupRoleRepo;

    @Autowired
    CommonCardScoinService CardScoinService;

    @Autowired
    PaymentService         paymentService;
    
    @Autowired
    private TokenStore              tokenStore;

    @Autowired
    private JwtAccessTokenConverter tokenEnhancer;

    @Autowired
    private ClientDetailsService    clientDetailsService;

    private String                  SCOIN_SIGNIN_GET_ACCESS_TOKEN_URL;
    private String                  SCOIN_SIGNIN_GET_PROFILE_URL;
    private String                  SCOIN_SIGNIN_CLIENTID;
    private String                  SCOIN_SIGNIN_CLIENTSECRET;
    private String                  SCOIN_SIGNIN_AGENCYID;
//    private String                  SCOIN_BALANCE_SCOIN_URL;
   
    public UserInfoServiceImpl(Environment env) {
        SCOIN_SIGNIN_GET_ACCESS_TOKEN_URL = env
                .getProperty(EnvironmentKey.LIVE_SCOIN_SIGNIN_GET_ACCESS_TOKEN_URL.getKey());
        SCOIN_SIGNIN_GET_PROFILE_URL = env
                .getProperty(EnvironmentKey.LIVE_SCOIN_SIGNIN_GET_PROFILE_URL.getKey());
        SCOIN_SIGNIN_CLIENTID = env
                .getProperty(EnvironmentKey.LIVE_SCOIN_SIGNIN_CLIENTID.getKey());
        SCOIN_SIGNIN_CLIENTSECRET = env
                .getProperty(EnvironmentKey.LIVE_SCOIN_SIGNIN_CLIENTSECRET.getKey());
        SCOIN_SIGNIN_AGENCYID = env
                .getProperty(EnvironmentKey.LIVE_SCOIN_SIGNIN_AGENCYID.getKey());
//        SCOIN_BALANCE_SCOIN_URL = env
//                .getProperty(EnvironmentKey.LIVE_SCOIN_BALANCE_SCOIN_URL.getKey());
    }
    
    private List<GrantedAuthority> grantedAuths;
  
    @Override
    public SigninScoinResponse signin(SignInScoinRequest request) {
        if (ObjectUtils.isEmpty(request)
                || StringUtils.isEmpty(request.getCode())
                || StringUtils.isEmpty(request.getRedirectUri())) {
            throw new ScoinInvalidDataRequestException();
        }
        
        LinkedMultiValueMap<String, String> getAcceccTokenRequest = new LinkedMultiValueMap<String, String>();
        getAcceccTokenRequest.add("client_id", SCOIN_SIGNIN_CLIENTID);
        getAcceccTokenRequest.add("code", request.getCode());
        getAcceccTokenRequest.add("client_secret", SCOIN_SIGNIN_CLIENTSECRET);
        getAcceccTokenRequest.add("agencyid", SCOIN_SIGNIN_AGENCYID);
        getAcceccTokenRequest.add("redirect_uri", request.getRedirectUri());
        
        GetAccessTokenScoinResponse getAcceccTokenresponse = ApiExchangeServiceUtil.
                postURLENCODE(SCOIN_SIGNIN_GET_ACCESS_TOKEN_URL, getAcceccTokenRequest, new TypeReference<GetAccessTokenScoinResponse>() {});
        
        if (ObjectUtils.isEmpty(getAcceccTokenresponse)) {
            throw new ScoinUnAuthorizationException("Don't get access_token of scoin");
        }
        
        String getProfileURL = SCOIN_SIGNIN_GET_PROFILE_URL + getAcceccTokenresponse.getAccessToken();
        ScoinBasicResponse<GetProfileByAccessTokenScoinDataResponse> getProfileResponse = ApiExchangeServiceUtil.
                get(getProfileURL, new TypeReference<ScoinBasicResponse<GetProfileByAccessTokenScoinDataResponse>>() {});
        if(ObjectUtils.isEmpty(getProfileResponse)
                || getProfileResponse.getCode() < 1) {
            throw new ScoinUnAuthorizationException("Don't get profile by this access_token");
        }
        GetProfileByAccessTokenScoinDataResponse profile = getProfileResponse.getData();
        
        //Create userInfo
        UserVTC userVTC = new UserVTC();
        UserInfo userInfo = new UserInfo();
        Optional<UserVTC> optUserVTC = userVTCService.findByScoinId(profile.getAccountId());
        if (optUserVTC.isEmpty()) {
            if(!StringUtils.isEmpty(profile.getFacebookName())) {
                userInfo.setFullName(profile.getFacebookName());
            } else {
                userInfo.setFullName(profile.getAccountName());
            }
  //          Random random = ThreadLocalRandom.current();
  //          byte[] randomOtp = new byte[256]; //Means 2048 bit
  //          random.nextBytes(randomOtp);
  //          LOGGER.info("randomOtp =================== {}", new String(randomOtp));
            userInfo.setOtpKey("");
  
            //Get role default
            GroupRole groupRole = groupRoleRepo.findByName(Constant.ROLE_USER);
            userInfo.setGroupRole(groupRole);
  
            //Create user scoin
            userVTC.setScoinId(profile.getAccountId());
            userVTC.setStatus(Constant.STATUS_ACTIVE);
            userVTC.setUserInfo(userInfo);
            userVTC.setFirstLogin(true);
        } else {
            
            userVTC = optUserVTC.get();
            userInfo = userVTC.getUserInfo();
        }
        
        //update username
        if(!StringUtils.isEmpty(profile.getAccountName())
            && profile.getAccountName() != userVTC.getUsername())
          userVTC.setUsername(profile.getAccountName());
        
        //update phone number
        if(!StringUtils.isEmpty(profile.getMobile())
                && profile.getMobile() != userInfo.getPhoneNumber())
            userInfo.setPhoneNumber(profile.getMobile());
        
        //update email
        if(!StringUtils.isEmpty(profile.getEmail())
                && profile.getEmail() != userInfo.getEmail())
            userInfo.setEmail(profile.getEmail());
        
        userInfo.setScoinToken(getAcceccTokenresponse.getAccessToken());
        userInfo = repo.save(userInfo);
        userVTC = userVTCService.save(userVTC)
            .orElseThrow(() -> new ScoinFailedToExecuteException("Don't create UserVTC"));
        
        if (userVTC.getUserInfo() == null 
                || userVTC.getUserInfo().getGroupRole() == null) {
            throw new ScoinUnAuthorizationException("You not right");
        }
  
        ProfileTokenResponse profileToken = new ProfileTokenResponse();
        profileToken.setUserInfoId(userInfo.getId());
        profileToken.setScoinId(userVTC.getScoinId());
        profileToken.setUserName(userVTC.getUsername());
        profileToken.setEmail(userInfo.getEmail());
        profileToken.setPhoneNumber(userInfo.getPhoneNumber());
        profileToken.setRole(userInfo.getGroupRole().getName());
        
        grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority(Constant.ROLE_USER));
  
        Authentication responseAuth = new UsernamePasswordAuthenticationToken(JsonMapperUtils.toJson(profileToken), null,
                grantedAuths);
        SecurityContextHolder.getContext().setAuthentication(responseAuth);
        OAuth2AccessToken accessToken = createAccesstoken();
        if (accessToken == null)
            throw new ScoinUnAuthorizationException();
        
        ProfileTokenResponse profileTokenR = JsonMapperUtils.convertJsonToObject(responseAuth.getPrincipal().toString(), ProfileTokenResponse.class);
        SigninScoinResponse response = new SigninScoinResponse();
        response.setAccess_token(accessToken.getValue());
        response.setScoinAccessToken(getAcceccTokenresponse.getAccessToken());
        response.setToken_type("bearer");
        response.setId(profileTokenR.getUserInfoId());
        response.setEmail(profileTokenR.getEmail());
        response.setUsername(profileTokenR.getUserName());
        response.setFirstLogin(userVTC.isFirstLogin());
        return response;
    }
    
    @Override
    public UserInfo getUserInfoByUserName(String userName) throws ScoinBusinessException {
        if (StringUtils.isEmpty(userName)) {
            throw new ScoinInvalidDataRequestException();
        }

        Optional<UserVTC> userVTC = userVTCService.findByUserName(userName);
        if (!userVTC.isPresent()) {
            return repo.findByFullName(userName);
        }

        UserInfo userInfo = userVTC.get().getUserInfo();
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new ScoinNotFoundEntityException("Not fount user info by this username");
        }

        userInfo.setUrlAvatar(userVTC.get().getUsername());
        return userInfo;
    }

    @Override
    public ProfileGetResponse getMyProfile() throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();

        paymentService.updateBalanceSoin(userInfo);
        ProfileGetResponse profileResponse = new ProfileGetResponse();
        profileResponse.setUserId(userInfo.getId());
        if (!StringUtils.isEmpty(userInfo.getFullName())) {
            profileResponse.setFullName(userInfo.getFullName());
        } else {
            profileResponse.setFullName(userInfo.getUserVTC().getUsername());
        }
        
        profileResponse.setUrlAvatar(userInfo.getUrlAvatar());
        profileResponse.setScoinBalance(userInfo.getUserVTC().getScoin());
        profileResponse.setAccountNumber(userInfo.getUserVTC().getScoinId());
        profileResponse.setVipLevel(userInfo.getVipLevel());
        if (!StringUtils.isEmpty(userInfo.getPhoneNumber()))
            profileResponse.setPhoneNumber(StringUtils.hiddenCharString(userInfo.getPhoneNumber(), 3));

        if (!StringUtils.isEmpty(userInfo.getEmail()))
            profileResponse.setEmail(StringUtils.encodeEmail(userInfo.getEmail()));

        return profileResponse;
    }
    
    @Override
    public List<String> exportCardScoin(long valueCard, int quantity) {
        if (valueCard == 0 || quantity == 0) {
            throw new ScoinInvalidDataRequestException();
        }
        List<String> responses = new ArrayList<String>();
        for (int i = 0; i < quantity; i++) {
            FundsCardScoin fundsCardScoin = CardScoinService.buyCard(String.valueOf(valueCard), 1);
            if (ObjectUtils.isEmpty(fundsCardScoin)) {
                throw new ScoinNotFoundEntityException("Not export card SCOIN");
            }
            String response = "Mã thẻ : " + fundsCardScoin.getMainCodeCard()
                    + " - Seri : " + fundsCardScoin.getSeriCard() 
                    + " - HSD : " + DateUtils.toStringFormDate(fundsCardScoin.getExpirationDateCard(), DateUtils.DATE_TIME_MYSQL_FORMAT)
                    + " - Mệnh giá : " + fundsCardScoin.getValueCard();
            responses.add(response);
        }
        
        return responses;
    }
    
    @Override
    public UserInfo getByScoinId(Long scoinId) throws ScoinBusinessException {
        return repo.getByScoinId(scoinId);
    }

    @Override
    public UserVTC exchangeScoin(String typeExchange, int amount) throws ScoinBusinessException {
        UserInfo userInfo = verifyAccessTokenUser();
        paymentService.exchangeScoin(userInfo, typeExchange, amount, "Test " + typeExchange + " Scoin, amount : " + amount);
        paymentService.updateBalanceSoin(userInfo);
        return userInfo.getUserVTC();
    }
    
    private OAuth2AccessToken createAccesstoken() {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();
  
            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("grant_type", "password");
            /*requestParams.put("username","phucnguyen");
            requestParams.put("password","123456");*/
            requestParams.put("loginType", Constant.LOGIN_TYPE_VTC);
  
            String clientId = Constant.CLIENT_ID;
  
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("USER"));
  
            boolean approved = true;
  
            Set<String> scope = new HashSet<>();
            scope.add("scope");
  
            Set<String> resourceIds = new HashSet<>();
            scope.add(Constant.RESOURCE_ID);
  
            Set<String> responseTypes = new HashSet<>();
            responseTypes.add("code");
            Map<String, Serializable> extensionProperties = new HashMap<>();
  
            OAuth2Request authorizationRequest = new OAuth2Request(requestParams, clientId, authorities,
                    approved, scope, resourceIds, null, responseTypes, extensionProperties);
  
  
            OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest, authenticationToken);
            authenticationRequest.setAuthenticated(true);
  
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore);
            defaultTokenServices.setClientDetailsService(clientDetailsService);
            defaultTokenServices.setTokenEnhancer(tokenEnhancer);
            defaultTokenServices.setSupportRefreshToken(true);
  
            OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(authenticationRequest);
            return accessToken;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }
    
}
