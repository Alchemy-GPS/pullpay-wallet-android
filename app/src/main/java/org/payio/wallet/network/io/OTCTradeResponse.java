package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OTCTradeResponse implements Serializable{

    /**
     * returnCode : 0000
     * returnMsg : 成功
     * data : [{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"1.00","orderQuantityMax":"99.00","orderPrice":"1.00","orderAmountLimit":"1.00","orderAmountMax":"99.00","wechat":"0","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=3&type=2","orderId":3},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"50.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"500.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=5&type=2","orderId":5},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"50.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"500.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=6&type=2","orderId":6},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"50.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"500.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=10&type=2","orderId":10},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"50.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"500.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=11&type=2","orderId":11},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"30.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"300.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=12&type=2","orderId":12},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"50.00","orderQuantityMax":"60.00","orderPrice":"10.00","orderAmountLimit":"500.00","orderAmountMax":"600.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=13&type=2","orderId":13},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=14&type=2","orderId":14},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=15&type=2","orderId":15},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=16&type=2","orderId":16},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=17&type=2","orderId":17},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=18&type=2","orderId":18},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=19&type=2","orderId":19},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"100.00","orderQuantityMax":"200.00","orderPrice":"100.00","orderAmountLimit":"10000.00","orderAmountMax":"20000.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=20&type=2","orderId":20},{"name":"payIo521828","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"20.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"200.00","wechat":"1","alipay":"0","card":"0","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=23&type=2","orderId":23},{"name":"mxc-sellser","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"2.00","orderQuantityMax":"8.00","orderPrice":"0.60","orderAmountLimit":"1.20","orderAmountMax":"4.80","wechat":"1","alipay":"1","card":"0","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=27&type=2","orderId":27},{"name":"payIo427171","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"1.00","orderQuantityMax":"2.00","orderPrice":"1.50","orderAmountLimit":"1.50","orderAmountMax":"3.00","wechat":"0","alipay":"1","card":"0","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=29&type=2","orderId":29},{"name":"payIo497420","headImage":"http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg","tradeRate":"0","highRate":"0","doneAmount":"0","orderQuantityLimit":"10.00","orderQuantityMax":"40.00","orderPrice":"10.00","orderAmountLimit":"100.00","orderAmountMax":"400.00","wechat":"1","alipay":"1","card":"1","remark":null,"currencyType":null,"userType":"C","type":"IN","url":"/buy/buy?id=30&type=2","orderId":30}]
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private List<TradeInfo> data;

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

    public List<TradeInfo> getData() {
        return data;
    }

    public void setData(List<TradeInfo> data) {
        this.data = data;
    }

    public static class TradeInfo {
        /**
         * name : payIo497420
         * headImage : http://172.100.16.103:9199/raidenOtcWalletstatic/images/head.jpg
         * tradeRate : 0
         * highRate : 0
         * doneAmount : 0
         * orderQuantityLimit : 1.00
         * orderQuantityMax : 99.00
         * orderPrice : 1.00
         * orderAmountLimit : 1.00
         * orderAmountMax : 99.00
         * wechat : 0
         * alipay : 1
         * card : 1
         * remark : null
         * currencyType : USDT
         * userType : C
         * type : IN
         * url : /buy/buy?id=3&type=2
         * orderId : 3
         */

        @SerializedName("name")
        private String name;
        @SerializedName("headImage")
        private String headImage;
        @SerializedName("tradeRate")
        private String tradeRate;
        @SerializedName("highRate")
        private String highRate;
        @SerializedName("doneAmount")
        private String doneAmount;
        @SerializedName("orderQuantityLimit")
        private String orderQuantityLimit;
        @SerializedName("orderQuantityMax")
        private String orderQuantityMax;
        @SerializedName("orderPrice")
        private String orderPrice;
        @SerializedName("orderAmountLimit")
        private String orderAmountLimit;
        @SerializedName("orderAmountMax")
        private String orderAmountMax;
        @SerializedName("wechat")
        private String wechat;
        @SerializedName("alipay")
        private String alipay;
        @SerializedName("card")
        private String card;
        @SerializedName("remark")
        private Object remark;
        @SerializedName("currencyType")
        private String currencyType;
        @SerializedName("userType")
        private String userType;
        @SerializedName("type")
        private String type;
        @SerializedName("url")
        private String url;
        @SerializedName("orderId")
        private int orderId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getTradeRate() {
            return tradeRate;
        }

        public void setTradeRate(String tradeRate) {
            this.tradeRate = tradeRate;
        }

        public String getHighRate() {
            return highRate;
        }

        public void setHighRate(String highRate) {
            this.highRate = highRate;
        }

        public String getDoneAmount() {
            return doneAmount;
        }

        public void setDoneAmount(String doneAmount) {
            this.doneAmount = doneAmount;
        }

        public String getOrderQuantityLimit() {
            return orderQuantityLimit;
        }

        public void setOrderQuantityLimit(String orderQuantityLimit) {
            this.orderQuantityLimit = orderQuantityLimit;
        }

        public String getOrderQuantityMax() {
            return orderQuantityMax;
        }

        public void setOrderQuantityMax(String orderQuantityMax) {
            this.orderQuantityMax = orderQuantityMax;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getOrderAmountLimit() {
            return orderAmountLimit;
        }

        public void setOrderAmountLimit(String orderAmountLimit) {
            this.orderAmountLimit = orderAmountLimit;
        }

        public String getOrderAmountMax() {
            return orderAmountMax;
        }

        public void setOrderAmountMax(String orderAmountMax) {
            this.orderAmountMax = orderAmountMax;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }
}
