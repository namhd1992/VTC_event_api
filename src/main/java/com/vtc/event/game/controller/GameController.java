/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtc.event.common.AbstractController;
import com.vtc.event.common.dao.entity.Game;
import com.vtc.event.common.exception.ScoinFailedToExecuteException;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.game.service.GameService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jun 7, 2019
 */
@RestController
@RequestMapping("/anonymous/game")
public class GameController extends AbstractController<GameService> {
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllGameActive() {
        return response(toResult(service.getAllGameActive()));
    }
    
    @GetMapping("/detail")
    public ResponseEntity<?> getGameByServiceId(@RequestParam("service_id") Long serviceId) {
        LOGGER.info("============ REQUEST =========== \n {}", JsonMapperUtils.toJson(serviceId));
        Game response = service.getGameByServiceId(serviceId).orElseThrow(
                () -> new ScoinFailedToExecuteException("Don't Get game by service id"));
        return response(toResult(response));
    }
    
    @GetMapping("/tag")
    public ResponseEntity<?> getGameByGameTags(@RequestParam("game_tag") List<Long> gameTagIds) {
        LOGGER.info("============ REQUEST =========== \n {}", JsonMapperUtils.toJson(gameTagIds));
        return response(toResult(service.getGameByGameTags(gameTagIds)));
    }

}
