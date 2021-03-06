package org.payio.wallet.params;

public interface TransParam {
    String SUCCESS_CODE = "0000";
    String SESSION_EXPIRED = "1100";

    String QRCODE_RESULT = "QRCODE_RESULT";

    String CRYPTOCURRENCY_ID = "CRYPTOCURRENCY_ID";
    String WALLET_ADDRESS = "WALLET_ADDRESS";

    String SCAN_QRCODE_FORRESULT = "SCAN_QRCODE_FORRESULT";
    int QRCODE_REQUEST_CODE = 100;
    int QRCODE_RESULT_CODE = 101;

    int GET_QRCODE_IMAGE_REQUESTCODE = 1001;
    int GET_HEAD_IMAGE_REQUESTCODE = 1002;
    int CLIP_HEAD_IMAGE_REQUESTCODE = 1003;

    String LAUNCH_RXTRA_BUNDLE = "LAUNCH_RXTRA_BUNDLE";

    String CLIP_HEAD_IMAGE_PATH = "CLIP_HEAD_IMAGE_PATH";

    /*------------------OTC Web-----------------*/

    String OTC_PAGE_URL = "OTC_PAGE_URL";

    String APPEAL_ORDER_ID = "APPEAL_ORDER_ID";
    String APPEAL_PAYMENT_TYPE = "APPEAL_PAYMENT_TYPE";
    int APPEAL_REQUEST_CODE = 201;
    int APPEAL_RESULT_CODE = 202;

}
