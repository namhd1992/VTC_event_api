/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.userInfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtc.event.common.AbstractController;
import com.vtc.event.common.dto.request.SignInScoinRequest;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.userInfo.service.UserInfoService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
@RestController
public class UserInfoController extends AbstractController<UserInfoService>{
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInScoinRequest request) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(request));
        return response(toResult(service.signin(request)));
    }
    
    @GetMapping("/name")
    public ResponseEntity<?> getUserInfoByName(@RequestParam("user_name") String userName) {
        LOGGER.info("===============REQUEST============== \n {}",
                JsonMapperUtils.toJson(userName));
        return response(toResult(service.getUserInfoByUserName(userName)));
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile() {
        return response(toResult(service.getMyProfile()));
    }
    
    @GetMapping("/exchange-scoin")
    public ResponseEntity<?> exchangeScoin(@RequestParam("type_exchange") String typeExchange,
                                           @RequestParam("amount") int amount) {
      LOGGER.info("===============REQUEST============== \n {}",
          JsonMapperUtils.toJson(amount));
        return response(toResult(service.exchangeScoin(typeExchange, amount)));
    }
    
//    @GetMapping("/scoin/export-scoin-card")
//    public ResponseEntity<?> exportScoinCard(@RequestParam("value_card") long valueCard,
//                                             @RequestParam("quantity") int quantity) {
//      LOGGER.info("===============REQUEST============== \n {}",
//          JsonMapperUtils.toJson(valueCard));
//        return response(toResult(service.exportCardScoin(valueCard, quantity)));
//    }

}
