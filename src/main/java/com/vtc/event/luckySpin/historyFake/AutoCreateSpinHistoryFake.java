///***************************************************************************
// * Product made by Quang Dat *
// **************************************************************************/
//package com.vtc.event.luckySpin;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import com.vtc.event.common.dao.entity.LuckySpin;
//import com.vtc.event.common.dao.entity.SpinHistoryFake;
//import com.vtc.event.common.dao.repository.LuckySpinRepository;
//import com.vtc.event.common.dao.repository.SpinHistoryFakeRepository;
//import com.vtc.event.common.exception.ScoinNotFoundEntityException;
//
///**
// * Author : Dat Le Quang
// * Email: Quangdat0993@gmail.com
// * Oct 24, 2019
// */
//@Component
//public class AutoCreateSpinHistoryFake {
//    
//    protected Logger             LOGGER = LoggerFactory.getLogger(this.getClass());
//    
//    @Autowired
//    LuckySpinRepository luckySpinRepository;
//    
//    @Autowired
//    SpinHistoryFakeRepository spinHistoryFakeRepository;
//    
//    
//    
//    @Scheduled(cron = "*/30 * * * * *")
//    public void doWork() {
//        try {
//            List<LuckySpin> luckySpins = luckySpinRepository.findAll();
//            if (CollectionUtils.isEmpty(luckySpins)) {
//                throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin id");
//            }
//            
//            LuckySpin luckySpin = luckySpins.get(0);
//            int tmp = ThreadLocalRandom.current().nextInt(4);
//            List<String> userName = new ArrayList<String>();
//            userName.add("ducnm12");
//            userName.add("tienThanhok");
//            userName.add("datbeo93");
//            userName.add("namCao92");
//            userName.add("quynhGD");
//            List<String> gifts = new ArrayList<String>();
//            gifts.add("Thẻ 1 triệu Scoin");
//            gifts.add("Thẻ 5 triệu Scoin");
//            gifts.add("Thẻ 500 triệu Scoin");
//            gifts.add("Thẻ 1 tỷ Scoin");
//            gifts.add("Thẻ -500 Scoin");
//            SpinHistoryFake historyFake = new SpinHistoryFake();
//            historyFake.setItemName(gifts.get(tmp));
//            historyFake.setUserName(userName.get(tmp));
//            historyFake.setLuckySpinId(luckySpin.getId());
//            spinHistoryFakeRepository.save(historyFake);
//            LOGGER.info("===============OK===========");
//            
//        } catch (Exception e) {
//            LOGGER.info("WORKER NOT WORKED");
//            LOGGER.info(e.getMessage());
//        }
//    }
//    
//
//}
