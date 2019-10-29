package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : 成功
     * data : {"headImage":"http://13.251.47.174:9299/payioImage/adfea1ef66b742cabebed6c68c379432.png","userName":"天地人"}
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo {
        /**
         * headImage : http://13.251.47.174:9299/payioImage/adfea1ef66b742cabebed6c68c379432.png
         * userName : 天地人
         */

        @SerializedName("headImage")
        private String headImage;
        @SerializedName("userName")
        private String userName;

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
