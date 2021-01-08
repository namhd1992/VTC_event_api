/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import com.vtc.event.common.Constant;
import com.vtc.event.common.dao.entity.UserInfo;
import com.vtc.event.common.dto.response.ProfileTokenResponse;
import com.vtc.event.common.exception.ScoinUnAuthorizationException;
import com.vtc.event.common.utils.JsonMapperUtils;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Apr 18, 2019
 */
public abstract class AbstractService<T, ID extends Serializable, R extends JpaRepository<T, ID>> {
    
    protected Logger             LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected R                  repo;
    
    @Autowired
    protected HttpServletRequest httpRequest;

    @Autowired
    protected Environment        env;
    
    @Autowired
    CommonUserInfoService userInfoService;

    public Optional<T> get(ID id) {
        Optional<T> opt = repo.findById(id);
        return Optional.ofNullable(opt.get());
    }
    
    @SuppressWarnings("unchecked")
    public List<T> getByIds(List<Long> ids) {
        return repo.findAllById((Iterable<ID>) ids);
    }

    public List<T> getAll() {
        return repo.findAll();
    }

    public Optional<T> save(T t) {
        LOGGER.info("\n\n ----> save record: {} \n\n", JsonMapperUtils.toJson(t));
        t = repo.save(t);

        return Optional.ofNullable(t);
    }

    public List<T> save(List<T> objects) {
        return repo.saveAll(objects);
    }

    public Optional<T> delete(ID id) {
        Optional<T> opt = repo.findById(id);

        if (opt.isPresent()) {
            repo.deleteById(id);
        }
        return Optional.ofNullable(opt.get());
    }
    
//    public UserInfo verifyUser() {
//        UserInfo userInfo = userInfoService.getUserInfoByUserName("BigZest");
//        if (ObjectUtils.isEmpty(userInfo)) {
//            throw new ScoinUnAuthorizationException();
//        }
//        return userInfo;
//    }
//    
    public UserInfo verifyUserSanbox() {
      UserInfo userInfo = userInfoService.getByScoinId((long) 1200369965);
      if (ObjectUtils.isEmpty(userInfo)) {
          throw new ScoinUnAuthorizationException();
      }
      return userInfo;
  }
//    
//    public UserInfo verifyAdmin() {
//        UserInfo userInfo = userInfoService.getUserInfoByUserName("admingamerid");
//        if (ObjectUtils.isEmpty(userInfo)) {
//            throw new ScoinUnAuthorizationException();
//        }
//        return userInfo;
//    }
    
//    public UserInfo verifyAccessTokenUser() {
//        if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())
//                || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//            throw new ScoinUnAuthorizationException();
//        }
//        
//        Long scoinId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        UserInfo userInfo = userInfoService.getByScoinId(scoinId);
//        return userInfo;
//    }
    
    public UserInfo verifyAccessTokenUser() {
        if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())
                || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new ScoinUnAuthorizationException();
        }
      
      ProfileTokenResponse profileTokenResponse = JsonMapperUtils.convertJsonToObject(
              SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),
              ProfileTokenResponse.class);
      
      UserInfo userInfo = userInfoService.get(profileTokenResponse.getUserInfoId()).orElseThrow(
              () -> new ScoinUnAuthorizationException("Don't Get UserInfo By this Id"));
      return userInfo;
  }
    
    public UserInfo verifyAccessTokenUser(String role) {
        if (role.equals(Constant.ROLE_ANONYMOUS))
            return null;
        return verifyAccessTokenUser();
    }

}
