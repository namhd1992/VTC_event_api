/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
public class Constant {
    
    //Config on system
    public static final String PROJECT_RESOURCES           = "src/main/webapp/resources/";
    public static final String FILE_PRIVATE_KEY_SCOIN_CARD = "card_scoin_key/merchant_privateKey.der";
    public static final String PROJECT_BASE_URL            = "https://api.simba-app.com/resources/";
    public static final String PROJECT_UPLOAD_FILE         = "src/main/webapp/upload/";
    public static final String IMAGE_NULL_SOURCE           = "http://171.244.14.44:9091/resources/image/image-null.jpg";
    
    //Service game id
    public static final long            SERVICE_GAME_ID_PHONG_MA_CHIEN = 330333;
    
    //Authorization signin
    public static final String CLIENT_ID                        = "vtcmobile";
    public static final String CLIENT_SECRET                    = "vtc@2017";
    public static final String RESOURCE_ID                      = "myresource";
    public static final String LOGIN_TYPE_VTC                   = "vtc";
    public static final String ROLE_USER                        = "user";
    public static final String ROLE_ANONYMOUS                   = "anonymous";
    
    
    public static final String LUCKYSPIN_SETTING_TYPE_LIMIT_BUYTURN = "LIMIT_BUY_TURN";
    
    // Type gift luckyspin
    public static final String LUCKYSPIN_GIFT_XU                 = "XU";
    public static final String LUCKYSPIN_GIFT_SCOIN              = "SCOIN";
    public static final String LUCKYSPIN_GIFT_SCOIN_PACKAGE      = "SCOIN_PACKAGE";
    public static final String LUCKYSPIN_GIFT_JACKPOT            = "JACKPOT";
    public static final String LUCKYSPIN_GIFT_ACTION             = "ACTION";
    public static final String LUCKYSPIN_GIFT_GIFTCODE           = "GIFTCODE";
    public static final String LUCKYSPIN_GIFT_REALITY            = "REALITY";
    public static final String LUCKYSPIN_GIFT_LUCKY_NUMBER       = "LUCKY_NUMBER";
    public static final String LUCKYSPIN_GIFT_SCOIN_CARD         = "SCOIN_CARD";
    public static final String LUCKYSPIN_TYPE_VONG_QUAY          = "VONG_QUAY";
    public static final String LUCKYSPIN_TYPE_LAT_THE            = "LAT_THE";
    public static final String LUCKYSPIN_BUY_TURN_TYPE_TOPUP_GAME= "TOPUP_GAME";
    public static final String LUCKYSPIN_BUY_TURN_TYPE_CROSS_CARD= "BUY_TURN";
    public static final String LUCKYSPIN_TOPUP_TYPE_CARD         = "CARD";
    public static final String LUCKYSPIN_TOPUP_TYPE_CARD_DES     = "Thẻ Scoin";
    public static final String LUCKYSPIN_TOPUP_TYPE_SCOIN        = "SCOIN";
    public static final String LUCKYSPIN_TOPUP_TYPE_SCOIN_DES    = "Ví Scoin";
    public static final String LUCKYSPIN_TOPUP_TYPE_ALL          = "ALL";
    public static final String LUCKYSPIN_TURNOVER_KEYNAME_TOTAL  = "TOTAL_TURNOVER";
    public static final String LUCKYSPIN_TURNOVER_LIMIT_TYPE_DAY = "DAY";
    
    //Type Action Mission
    public static final int    ACTION_LUCKY_SPIN               = 1;
    public static final int    ACTION_CHECKIN_DAYLY            = 2;
    public static final int    ACTION_LOGIN_GAME               = 3;
    public static final int    ACTION_FIRST_LOGIN_GAME         = 4;
    public static final int    ACTION_TOPUP_CARD               = 5;
    public static final int    MISSION_SATISFYING_DEFAULT      = 1;
    public static final int    MISSION_PROGRESS_DEFAULT        = 1;
    public static final String AWARD_MISSION_XU                = "XU";
    public static final String AWARD_MISSION_TURN_SPIN         = "TURN_SPIN";
    public static final long    CARD_VALUE_10000               = 10000;
    public static final long    CARD_VALUE_20000               = 20000;

    // Type limit gift
    public static final String DAY                             = "DAY";
    public static final String EVENT                           = "EVENT";
    
    //Giftcode
    public static final String GIFTCODE_EVENT_TYPE_GAME_RANKING = "GAME_RANKING";
    
    //URL Call To Other Server
    public static final String URL_LOGIN_SERVICE_HISTORY         = "https://graph.vtcmobile.vn/accountapi/server/get_accountservice.aspx?";
    
    // Call API to Scoin : CARD SCOIN
    public static final String SCOIN_CARD_PARTNER_CODE           = "VTCMobileVAS"; // Account Test
//    public static final String SCOIN_CARD_PARTNER_CODE           = "ttvasminigame"; // Account Real
    public static final String SCOIN_CARD_COMMAND_TYPE_BUYCARD   = "BuyCard";
    public static final String SCOIN_TOPUP_COMMAND_TYPE_BUYCARD  = "TopupScoin";
    public static final String SCOIN_CARD_COMMAND_TYPE_GETCARD   = "GetCard";
    public static final String SCOIN_CARD_VERSION                = "1.0";
    public static final String SCOIN_CARD_SERVICE_CODE_SCOIN     = "VTCM001";
    public static final String SCOIN_TOPUP_SERVICE_CODE_SCOIN    = "VTCM002";
    public static final String SCOIN_CARD_SERVICE_CODE_VIETTEL   = "VTCM0027";
    public static final String SCOIN_CARD_SERVICE_CODE_VINAPHONE = "VTCM0028";
    public static final String SCOIN_CARD_SERVICE_CODE_MOBIFONE  = "VTCM0029";
    public static final String SCOIN_CARD_TEXT_START_RESPONSE    = "<RequestTransactionResult>";
    public static final String SCOIN_CARD_TYPE_CARD_SCOIN        = "SCOIN";
    public static final String SCOIN_CARD_TYPE_CARD_SCOIN_CROSS  = "CARD";
    public static final String SCOIN_CARD_TOPUP_HISTORY          = "topup_card_scoin_history"; //66dedf9b5451dfe1b51815c3e9b41164
    public static final String SCOIN_LIVE_CARD_TOPUP_HISTORY     = "VTC_MOBILE_topup_card_scoin_history"; //49decc386bcb5c3fa715b04c012e4aa1
    
    // Call API to Scoin : Tủ Đồ Scoin
    public static final int SCOIN_TUDO_ITEM_TYPE_GIFTCODE        = 405;
    public static final int SCOIN_TUDO_ITEM_TYPE_REALITY         = 406;
    public static final int SCOIN_TUDO_ITEM_TYPE_LUCKY_NUMBER    = 407;
    public static final int SCOIN_TUDO_ITEM_TYPE_SCOIN_CARD      = 408;
    
    //Exchange Coin
    public static final long   SERVICE_ID_XU                   = 330287;
    public static final String ROLLBACK_XU                     = "ROLLBACK";
    public static final String XU_TOPUP                        = "TOPUP";
    public static final String XU_DEDUCT                       = "DEDUCT";
    public static final String SCOIN_TYPE_TOPUP                = "TOPUP";
    public static final String SCOIN_URL_TOPUP                 = "topup";
    public static final String SCOIN_TYPE_DEDUCT               = "DEDUCT";
    public static final String SCOIN_URL_DEDUCT                = "buyitem";
    public static final String HISTORY_STATUS_SUCCESS          = "success";
    public static final String HISTORY_STATUS_FAILURE          = "failure";
    public static final int    BALANCE_XU_DEFAULT              = 0;
    
    //Status
    public static final String STATUS_ACTIVE                   = "active";
    public static final String STATUS_DELETED                  = "deleted";
    public static final String STATUS_SUCCESS                  = "success";
    public static final String STATUS_FAILURE                  = "failure";
    public static final String STATUS_RECIEVED                 = "recieved";
    public static final String STATUS_HIGHLIGHTS               = "highlights";

    public static final String SERVICE_TYPE_DEDUCT             = "DEDUCT";
    public static final String SERVICE_TYPE_TOPUP              = "TOPUP";
    
    public static final String SOURCE_TYPE_LUCKYSPIN           = "LUCKYSPIN";
    public static final String SOURCE_TYPE_WINNING_SP          = "WINNING-SP";
    public static final String SOURCE_TYPE_WINNING_JACKPOT     = "WINNING-JACKPOT";
    public static final String SOURCE_FINSH_MISSION            = "FINISH-MISSION";
    
   

    public static final int    LENGHT_FILE_OF_FOLDER_EMPTY     = 0;
    public static final String LEAGUE_CURRENCY                 = "XU";
    
    // Game Ranking
    public static final String GAME_RANKING_THIS_WEEK                = "THIS_WEEK";
    public static final String GAME_RANKING_WEEK_BEFORE_LAST         = "WEEK_BEFORE_LAST";
    public static final String GAME_RANKING_ITEM_TYPE_GIFTCODE       = "GIFTCODE";
    public static final String GAME_RANKING_ITEM_TYPE_INGAME         = "INGAME";
    public static final String GAME_RANKING_ITEM_TYPE_REAL           = "REAL";
    public static final String GAME_RANKING_ITEM_TYPE_TURN_LUCKYSPIN = "TURN_LUCKYSPIN";
    public static final String GAME_RANKING_ITEM_TYPE_XU             = "XU";
    public static final String GAME_RANKING_ITEM_TYPE_SCOIN          = "SCOIN";
    public static final String GAME_RANKING_ITEM_TYPE_CARD           = "CARD";

}
