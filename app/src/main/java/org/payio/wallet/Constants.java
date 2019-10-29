package org.payio.wallet;

public class Constants {

    public static String IP;
    public static String APP_HOST;
    public static boolean isEnabled;

    static {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            //生产地址
            IP = "payio.org";
            APP_HOST = "https://" + IP + "/";
            isEnabled = false;
        } else {
            //测试地址
            IP = "13.251.47.174:9199";
//            IP = "172.100.16.183:9199";
//            IP = "172.100.16.78:9199";


            APP_HOST = "http://" + IP + "/";
            isEnabled = true;
        }
    }


    public static String WS_HOST = "ws://" + IP + "/raidenOtcWallet/websocket";
    public static final String PAY_SCHEMA = "payio://payio.org/params";
    public static final String LOGIN_HOST = "raidenOtcWallet/user/loginRequest";
    public static final String OTC_TRADE_BUY_HOST = "raidenOtcWallet/trade/buy/usdt?type=OUT";
    public static final String OTC_TRADE_SELL_HOST = "raidenOtcWallet/trade/buy/usdt?type=IN";
    public static final String OTC_ORDER_HOST_2 = "raidenOtcWallet/trade/buy/order?type=2&orderId=";
    public static final String OTC_ORDER_HOST_4 = "raidenOtcWallet/trade/buy/order?type=4&orderId=";
    public static final String OTC_PUTUP_ORDER = "raidenOtcWallet/entrust/putUp";
    public static final String OTC_USER_ORDER = "raidenOtcWallet/entrust/user";


    public static String WEB3JURL = "https://ropsten.infura.io/78ASIWuls38yBSxn1u5W";
//    public static String WEB3JURL = "http://47.104.90.114:8545";

    public static String CONTRACT_ADDRESS = "0x0f114A1E9Db192502E7856309cc899952b3db1ED";
    public static String CONTRACT_ADDRESS_RTT = "0x0f114A1E9Db192502E7856309cc899952b3db1ED";
    public static String CONTRACT_ADDRESS_INSP = "0xd18da7B6E727f288aA056842723476284Beae36E";
    public static String CONTRACT_ADDRESS_ACHPAY = "0x822925476aF6C7baE9667C09161Ea84294be2500";
    public static String CONTRACT_ADDRESS_ACHRID = "0x0c79B0ebEbB65c0d15d9c1011626DA1B006736d8";
    public static String CONTRACT_ADDRESS_INF = "0x2608096B2aEbD85bc3c61444cA43dC136Cd5CEd8";

    public static String TUNNEL_PROXY_ADDRESS = "0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20";
}
