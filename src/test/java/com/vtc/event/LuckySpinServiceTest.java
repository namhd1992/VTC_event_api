/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.repository.LuckySpinRepository;
import com.vtc.event.common.utils.JsonMapperUtils;
import com.vtc.event.luckySpin.service.LuckySpinService;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jan 9, 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LuckySpinServiceTest {
  
    @MockBean
    private LuckySpinRepository LuckySpinRepository;
    
    @Autowired
    private LuckySpinService luckySpinService;
    
    
    
    @Test
    public void luckySpinDetailTest() {
        String result = "{\r\n" + 
                "            \"id\": 119,\r\n" + 
                "            \"type\": \"SAN_KHO_BAU\",\r\n" + 
                "            \"name\": \"Săn Kho Bắu\",\r\n" + 
                "            \"keyName\": \"San Kho Bau\",\r\n" + 
                "            \"image\": \"http://171.244.14.44:9091/resources/image/1573782826348-lat_the_2.png\",\r\n" + 
                "            \"createOn\": 1583211109000,\r\n" + 
                "            \"startDate\": 1581927600000,\r\n" + 
                "            \"endDate\": 1584464400000,\r\n" + 
                "            \"lastUpdate\": 1584302179000,\r\n" + 
                "            \"description\": \"<p>HTML</p>\\n\",\r\n" + 
                "            \"linkLiveStream\": \"\",\r\n" + 
                "            \"freeSpinPerDay\": 0,\r\n" + 
                "            \"freeSpinOnStart\": 0,\r\n" + 
                "            \"buyTurnType\": \"ALL\",\r\n" + 
                "            \"maxSpinPerUser\": 1111111,\r\n" + 
                "            \"pricePerTurn\": 100000,\r\n" + 
                "            \"status\": \"active\",\r\n" + 
                "            \"spinTimes\": 9989,\r\n" + 
                "            \"checkDeviceId\": false\r\n" + 
                "        }";
        Pageable pageable = PageRequest.of(0, 1);
        List<LuckySpin> luckySpins = new ArrayList<LuckySpin>();
        when(LuckySpinRepository.findLuckySpin((long) 1, "success", pageable)).thenReturn(luckySpins);
        
        assertTrue(JsonMapperUtils.toJson(luckySpins).contains(JsonMapperUtils.toJson(luckySpinService.findLuckySpin((long) 1, "success", pageable))));
       
        
        assertNotNull(luckySpinService.findLuckySpin((long) 1, "success", pageable));
    }
    

}
