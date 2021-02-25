/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.luckySpin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtc.event.common.AbstractController;
import com.vtc.event.common.Constant;
import com.vtc.event.common.dto.request.LuckySpinExchangeItemRequest;
import com.vtc.event.common.dto.request.LuckySpinGetRequest;
import com.vtc.event.common.dto.request.LuckySpinOpenItemRequest;
import com.vtc.event.common.dto.response.LuckySpinDetailResponse;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.luckySpin.service.LuckySpinService;


/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 8, 2019
 */
@RestController
//@RequestMapping("/lucky-spin")
public class LuckySpinController extends AbstractController<LuckySpinService> {
    
    @GetMapping("/lucky-spin/get")
    public ResponseEntity<?> getLuckySpin(LuckySpinGetRequest request) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(request));
        return response(toResult(service.getLuckySpinActive(request), service.countLuckySpin(request)));
    }
    
    @GetMapping("/anonymous/lucky-spin/detail")
    public ResponseEntity<?> getLuckySpinDetail(@RequestParam(value = "lucky_spin_id") Long luckySpinId) {
        LOGGER.info("===============REQUEST============== \n {}",
                JsonMapperUtils.toJson(luckySpinId));
        LuckySpinDetailResponse response = service.getLuckySpinDetail(luckySpinId, Constant.ROLE_ANONYMOUS).orElseThrow(
                () -> new ScoinFailedToExecuteException("Get lucky spin detail don't successful"));
        return response(toResult(response));
    }
    
    @GetMapping("/lucky-spin/detail")
    public ResponseEntity<?> getLuckySpinDetailUser(@RequestParam(value = "lucky_spin_id") Long luckySpinId) {
        LOGGER.info("===============REQUEST============== \n {}",
                JsonMapperUtils.toJson(luckySpinId));
        LuckySpinDetailResponse response = service.getLuckySpinDetail(luckySpinId, null).orElseThrow(
                () -> new ScoinFailedToExecuteException("Get lucky spin detail don't successful"));
        return response(toResult(response));
    }
    
    @PostMapping("/lucky-spin/exchange-item")
    public ResponseEntity<?> luckySpinExchangeItem(@RequestBody LuckySpinExchangeItemRequest request) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(request));
        return response(toResult(service.luckySpinExchangeItem(request)));
    }
    
    @PostMapping("/lucky-spin/open-item")
    public ResponseEntity<?> luckySpinOpenItem(@RequestBody LuckySpinOpenItemRequest request) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(request));
        return response(toResult(service.luckySpinOpenItem(request)));
    }
    
//    ======================================================================================================
    
    @GetMapping("/scoin/export-card")
    public ResponseEntity<?> getLuckyNumber(@RequestParam(value = "card_value") long cardValue,
                                            @RequestParam(value = "quantity") int quantity) throws Exception {
        return response(toResult(service.getLuckyNumbers(cardValue, quantity)));
    }
    
    @GetMapping("/anonymous/lucky-spin/createLuckyNumber")
    public ResponseEntity<?> createLuckyNumber(@RequestParam(value = "digit_number") int digitNumber) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(digitNumber));
        return response(toResult(service.createLuckyNumber(digitNumber)));
    }
    
    @GetMapping("/getBalance")
    public ResponseEntity<?> getBalance(@RequestParam(value = "scoin_id") Long scoinId) {
        LOGGER.info("===============REQUEST============== \n {}", JsonMapperUtils.toJson(scoinId));
        return response(toResult(service.getBalanceXu(scoinId)));
    }
}
