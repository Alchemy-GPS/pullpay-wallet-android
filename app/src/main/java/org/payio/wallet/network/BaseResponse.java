package org.payio.wallet.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 获取json数据基类
 */

public class BaseResponse<T> implements Serializable {
    @SerializedName("returnCode")
    public String returnCode;
    @SerializedName("returnMsg")
    public String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
