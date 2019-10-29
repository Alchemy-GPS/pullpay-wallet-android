package org.payio.wallet.params;

public interface User {
    String WALLET_PATH = "WALLET_PATH";
    String WALLET_NAME = "WALLET_NAME";
    String WALLET_ADDRESS = "WALLET_ADDRESS";
    String WALLET_PASSWORD = "WALLET_PASSWORD";


    String OTC_ORDER_ID = "OTC_ORDER_ID";
    String OTC_ORDER_TIME = "OTC_ORDER_TIME";
    String OTC_PAYMENT_CHANNEL = "OTC_PAYMENT_CHANNEL";

    String USER_ID = "USER_ID";
    String USER_IS_LOGIN = "USER_IS_LOGIN";
    String USER_COOKIE = "USER_COOKIE";
    String USER_NAME = "USER_NAME";

    String LAST_ORDER_STATUS = "LAST_ORDER_STATUS";

    String TRADE_TYPE = "TRADE_TYPE";

    String[] OTC_TRADE_TITLE = new String[]{"BITCNY","GUSD","USDC"};

    String DOWNLOAD_APK_NAME = "PayIO.apk";
    String DOWNLOAD_ID = "DOWNLOAD_ID";
    String APK_MD5_VALUE = "APK_MD5_VALUE";
    String FIRST_TIME = "FIRST_TIME";
    String FILE_AUTHORITY = "org.payio.wallet";
}
