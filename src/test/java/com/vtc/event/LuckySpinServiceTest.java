/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vtc.event.common.dao.repository.LuckySpinRepository;
import com.vtc.event.luckySpin.service.impl.LuckySpinServiceImpl;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jan 9, 2020
 */
public class LuckySpinServiceTest {
    
    @MockBean
    LuckySpinRepository luckySpinRepo;
    
    @Autowired
    LuckySpinServiceImpl luckySpinService;
    
    

}
