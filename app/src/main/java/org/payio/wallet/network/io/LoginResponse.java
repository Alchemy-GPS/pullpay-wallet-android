package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : 成功
     * data : {"ethPassword":"c51cd8e64b0aeb778364765013df9ebe","name":"大买家","ethAddress":"0x3e126df4f2fd6b95396861bd1a2df2210456b7a7","userId":"61"}
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private UserInfo info;

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

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public static class UserInfo {
        /**
         * ethPassword : c51cd8e64b0aeb778364765013df9ebe
         * name : 大买家
         * ethAddress : 0x3e126df4f2fd6b95396861bd1a2df2210456b7a7
         * userId : 61
         */

        @SerializedName("ethPassword")
        private String ethPassword;
        @SerializedName("name")
        private String name;
        @SerializedName("ethAddress")
        private String ethAddress;
        @SerializedName("userId")
        private String userId;

        public String getEthPassword() {
            return ethPassword;
        }

        public void setEthPassword(String ethPassword) {
            this.ethPassword = ethPassword;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEthAddress() {
            return ethAddress;
        }

        public void setEthAddress(String ethAddress) {
            this.ethAddress = ethAddress;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
