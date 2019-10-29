package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

public class WebsocketServerMessage {

    /**
     * data : {"cryptoCurrencyQuantity":"1.00000000","messageTitle":"通知：您有一笔金额为10.00 CNY订单交易完成","orderAmount":"10.00","orderNo":"1544695400976","orderStatus":"8","orderStatusMsg":"交易完成","url":"/trade/buy/order?orderId=1203","userId":"3"}
     * messageType : trade
     */

    @SerializedName("data")
    private Message data;
    @SerializedName("messageType")
    private String messageType;

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public static class Message {
        /**
         * cryptoCurrencyQuantity : 1.00000000
         * messageTitle : 通知：您有一笔金额为10.00 CNY订单交易完成
         * orderAmount : 10.00
         * orderNo : 1544695400976
         * orderStatus : 8
         * orderStatusMsg : 交易完成
         * url : /trade/buy/order?orderId=1203
         * userId : 3
         */

        @SerializedName("cryptoCurrencyQuantity")
        private String cryptoCurrencyQuantity;
        @SerializedName("messageTitle")
        private String messageTitle;
        @SerializedName("orderAmount")
        private String orderAmount;
        @SerializedName("orderNo")
        private String orderNo;
        @SerializedName("orderStatus")
        private String orderStatus;
        @SerializedName("orderStatusMsg")
        private String orderStatusMsg;
        @SerializedName("url")
        private String url;
        @SerializedName("userId")
        private String userId;

        public String getCryptoCurrencyQuantity() {
            return cryptoCurrencyQuantity;
        }

        public void setCryptoCurrencyQuantity(String cryptoCurrencyQuantity) {
            this.cryptoCurrencyQuantity = cryptoCurrencyQuantity;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
