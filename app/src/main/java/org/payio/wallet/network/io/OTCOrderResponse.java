package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OTCOrderResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : 成功
     * data : [{"id":1175,"sysFlowNo":"1544675801864","orderQuantity":"80.00000000","orderAmount":"800.00","orderStatus":"8","createTime":"2018-12-13 04:36:41","currencyType":"USDT","userType":"2"},{"id":1170,"sysFlowNo":"1544671435626","orderQuantity":"1.00000000","orderAmount":"10.00","orderStatus":"3","createTime":"2018-12-13 03:23:55","currencyType":"USDT","userType":"2"},{"id":1168,"sysFlowNo":"1544669720362","orderQuantity":"1.00000000","orderAmount":"10.00","orderStatus":"3","createTime":"2018-12-13 02:55:21","currencyType":"USDT","userType":"2"},{"id":1167,"sysFlowNo":"1544669718966","orderQuantity":"1.00000000","orderAmount":"10.00","orderStatus":"3","createTime":"2018-12-13 02:55:19","currencyType":"USDT","userType":"2"},{"id":1166,"sysFlowNo":"1544668476782","orderQuantity":"1.00000000","orderAmount":"1.00","orderStatus":"3","createTime":"2018-12-13 02:34:36","currencyType":"USDT","userType":"1"},{"id":1165,"sysFlowNo":"1544668438401","orderQuantity":"1.00000000","orderAmount":"1.00","orderStatus":"3","createTime":"2018-12-13 02:33:58","currencyType":"USDT","userType":"1"},{"id":1164,"sysFlowNo":"1544667539076","orderQuantity":"1.00000000","orderAmount":"1.00","orderStatus":"3","createTime":"2018-12-13 02:18:57","currencyType":"USDT","userType":"1"},{"id":1163,"sysFlowNo":"1544667507357","orderQuantity":"1.00000000","orderAmount":"1.00","orderStatus":"3","createTime":"2018-12-13 02:18:27","currencyType":"USDT","userType":"1"},{"id":1160,"sysFlowNo":"1544609250088","orderQuantity":"1.00000000","orderAmount":"10.00","orderStatus":"6","createTime":"2018-12-12 18:07:30","currencyType":"USDT","userType":"2"},{"id":1159,"sysFlowNo":"1544605668008","orderQuantity":"1.00000000","orderAmount":"10.00","orderStatus":"3","createTime":"2018-12-12 17:07:48","currencyType":"USDT","userType":"2"}]
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private List<OrderDetail> data;

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

    public List<OrderDetail> getData() {
        return data;
    }

    public void setData(List<OrderDetail> data) {
        this.data = data;
    }

    public static class OrderDetail {
        /**
         * id : 1175
         * sysFlowNo : 1544675801864
         * orderQuantity : 80.00000000
         * orderAmount : 800.00
         * orderStatus : 8
         * createTime : 2018-12-13 04:36:41
         * currencyType : USDT
         * userType : 2
         */

        @SerializedName("id")
        private int id;
        @SerializedName("sysFlowNo")
        private String sysFlowNo;
        @SerializedName("orderQuantity")
        private String orderQuantity;
        @SerializedName("orderAmount")
        private String orderAmount;
        @SerializedName("orderStatus")
        private String orderStatus;
        @SerializedName("orderStatusMsg")
        private String orderStatusMsg;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("currencyType")
        private String currencyType;
        @SerializedName("userType")
        private String userType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSysFlowNo() {
            return sysFlowNo;
        }

        public void setSysFlowNo(String sysFlowNo) {
            this.sysFlowNo = sysFlowNo;
        }

        public String getOrderQuantity() {
            return orderQuantity;
        }

        public void setOrderQuantity(String orderQuantity) {
            this.orderQuantity = orderQuantity;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusMsg() {
            return orderStatusMsg;
        }

        public void setOrderStatusMsg(String orderStatusMsg) {
            this.orderStatusMsg = orderStatusMsg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
