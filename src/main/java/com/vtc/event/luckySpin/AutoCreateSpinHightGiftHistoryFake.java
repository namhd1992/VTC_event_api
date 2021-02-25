///***************************************************************************
// * Product made by Quang Dat *
// **************************************************************************/
//package com.vtc.event.luckySpin;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import com.vtc.event.common.Constant;
//import com.vtc.event.common.dao.entity.LuckySpin;
//import com.vtc.event.common.dao.entity.LuckySpinHistoryFake;
//import com.vtc.event.common.dao.repository.LuckySpinHistoryFakeRepository;
//import com.vtc.event.common.dao.repository.LuckySpinRepository;
//import com.vtc.event.common.exception.ScoinNotFoundEntityException;
//import com.vtc.event.common.utils.DateUtils;
//
///**
// * Author : Dat Le Quang
// * Email: Quangdat0993@gmail.com
// * Oct 24, 2019
// */
//@Component
//public class AutoCreateSpinHightGiftHistoryFake {
//    
//    protected Logger             LOGGER = LoggerFactory.getLogger(this.getClass());
//    
//    @Autowired
//    LuckySpinRepository luckySpinRepository;
//    
//    @Autowired
//    LuckySpinHistoryFakeRepository spinHistoryFakeRepository;
//    
//    
//    
//    @Scheduled(fixedDelay = 17280000)
//    public void doWork() {
//        try {
//            LOGGER.info("======START Scheduled Hight ======== : {}" , DateUtils.toStringFormDate(new Date(), DateUtils.DATE_TIME_MYSQL_FORMAT));
//            Pageable pageable = PageRequest.of(0, 1);
//            List<LuckySpin> luckySpins = luckySpinRepository.findByType(Constant.LUCKYSPIN_TYPE_LAT_THE, pageable);
//            if (CollectionUtils.isEmpty(luckySpins)) {
//                throw new ScoinNotFoundEntityException("Not fount Item of this lucky spin id");
//            }
//            
//            LuckySpin luckySpin = luckySpins.get(0);
//            if (new Date().getTime() >= luckySpin.getStartDate().getTime()
//                    && new Date().getTime() <= luckySpin.getEndDate().getTime()) {
//                int tmpUserName = ThreadLocalRandom.current().nextInt(50) - 1;
//                int tmpGift = ThreadLocalRandom.current().nextInt(16) - 1;
//                List<String> userName = new ArrayList<String>();
//                userName.add("ducnm12");
//                userName.add("tienThanhok");
//                userName.add("datbeo93");
//                userName.add("namCao92");
//                userName.add("quynhGD");
//                userName.add("dungnm12");
//                userName.add("thaoThanhok");
//                userName.add("thanhbeo93");
//                userName.add("nhungCao92");
//                userName.add("quyenGD");
//                userName.add("manhnm12");
//                userName.add("vietanhok");
//                userName.add("linhbeo93");
//                userName.add("cuongCao92");
//                userName.add("vanGD");
//                userName.add("ducAnhnm12");
//                userName.add("huongThanhok");
//                userName.add("minhbeo93");
//                userName.add("candyCao92");
//                userName.add("phuongGD");
//                userName.add("hiennm12");
//                userName.add("tuanHanhok");
//                userName.add("toanbeo93");
//                userName.add("nghiaCao92");
//                userName.add("giangGD");
//                userName.add("duynem12");
//                userName.add("maiThanhok");
//                userName.add("quangBeo93");
//                userName.add("vanCao92");
//                userName.add("linhAnhGD");
//                userName.add("tuyenVunm12");
//                userName.add("AnThanhok");
//                userName.add("thanhbeo93");
//                userName.add("boDoi92");
//                userName.add("tieuSanGD");
//                userName.add("Toannm12");
//                userName.add("ThienTrang98");
//                userName.add("MinhTrang95");
//                userName.add("CaoThao95");
//                userName.add("DuyNguyen152");
//                userName.add("ThanhMafia");
//                userName.add("NgoiNhaMauTim");
//                userName.add("DavidQuan1989");
//                userName.add("quanNguyen");
//                userName.add("thanhDaubo");
//                userName.add("linhCandy");
//                userName.add("thaoXaBong");
//                userName.add("chongChongTre");
//                userName.add("GioMua");
//                userName.add("tititu198");
//
//                List<String> gifts = new ArrayList<String>();
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("500K Scoin");
//                gifts.add("1 Triệu Scoin");
//                gifts.add("1 Triệu Scoin");
//                gifts.add("1 Triệu Scoin");
//                LuckySpinHistoryFake historyFake = new LuckySpinHistoryFake();
//                historyFake.setLuckySpinId(luckySpin.getId());
//                historyFake.setUserName(userName.get(tmpUserName));
//                historyFake.setItemName(gifts.get(tmpGift));
//                historyFake.setItemType(Constant.LUCKYSPIN_GIFT_SCOIN);
//                switch (gifts.get(tmpGift)) {
//                case "20K Scoin":
//                    historyFake.setItemValue(20000);
//                    break;
//                case "50K Scoin":
//                    historyFake.setItemValue(50000);
//                    break;
//                case "100K Scoin":
//                    historyFake.setItemValue(100000);
//                    break;
//
//                default:
//                    break;
//                }
//                spinHistoryFakeRepository.save(historyFake);
//            }
//            
//            LOGGER.info("===============END Scheduled ===========");
//        } catch (Exception e) {
//            LOGGER.info("WORKER NOT WORKED");
//            LOGGER.info(e.getMessage());
//        }
//    }
//}
