/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vtc.event.common.dao.entity.LuckySpin;
import com.vtc.event.common.dao.entity.LuckySpinItemOfUser;
import com.vtc.event.common.dao.entity.LuckySpinSetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 8, 2019
 */
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinDetailResponse {
    
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//    private List<LuckySpinItemOfLuckySpin> luckySpinItemOfLuckySpin;

    private LuckySpin                      luckySpin;

    private List<LuckySpinSetting>         settings;

    private List<LuckySpinGiftResponse>    luckySpinGifts;

    private List<LuckySpinItemOfUser>      itemOfUser;

    private UserTurnSpinDetailResponse     userTurnSpin;

}
