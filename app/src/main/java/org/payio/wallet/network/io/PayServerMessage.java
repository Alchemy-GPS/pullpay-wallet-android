package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayServerMessage implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : SUCCESS
     * data : {"customerAddr":"0x3e126df4f2fd6b95396861bd1a2df2210456b7a7","id":1077,"orderAmount":5.86878466,"orderStatus":"4","orderType":"ACH","payUrl":"http://13.251.47.174:9199/raidenOtcWallet/customer/channelPayment?token=0x0f114A1E9Db192502E7856309cc899952b3db1ED&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=5.86878466&sysFlowNo=gw2018122607225800001&userAddr=","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","sysFlowNo":"gw2018122607225800001","tokenAddress":"0x0f114A1E9Db192502E7856309cc899952b3db1ED","updateTime":1545808980000}
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private PayInfo data;

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

    public PayInfo getData() {
        return data;
    }

    public void setData(PayInfo data) {
        this.data = data;
    }

    public static class PayInfo {
        /**
         * customerAddr : 0x3e126df4f2fd6b95396861bd1a2df2210456b7a7
         * id : 1077
         * orderAmount : 5.86878466
         * orderStatus : 4
         * orderType : ACH
         * payUrl : http://13.251.47.174:9199/raidenOtcWallet/customer/channelPayment?token=0x0f114A1E9Db192502E7856309cc899952b3db1ED&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=5.86878466&sysFlowNo=gw2018122607225800001&userAddr=
         * proxyAddr : 0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20
         * receiverAddr : 0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a
         * sysFlowNo : gw2018122607225800001
         * tokenAddress : 0x0f114A1E9Db192502E7856309cc899952b3db1ED
         * updateTime : 1545808980000
         */

        @SerializedName("customerAddr")
        private String customerAddr;
        @SerializedName("id")
        private int id;
        @SerializedName("orderAmount")
        private double orderAmount;
        @SerializedName("orderStatus")
        private String orderStatus;
        @SerializedName("orderType")
        private String orderType;
        @SerializedName("payUrl")
        private String payUrl;
        @SerializedName("proxyAddr")
        private String proxyAddr;
        @SerializedName("receiverAddr")
        private String receiverAddr;
        @SerializedName("sysFlowNo")
        private String sysFlowNo;
        @SerializedName("tokenAddress")
        private String tokenAddress;
        @SerializedName("updateTime")
        private long updateTime;

        public String getCustomerAddr() {
            return customerAddr;
        }

        public void setCustomerAddr(String customerAddr) {
            this.customerAddr = customerAddr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPayUrl() {
            return payUrl;
        }

        public void setPayUrl(String payUrl) {
            this.payUrl = payUrl;
        }

        public String getProxyAddr() {
            return proxyAddr;
        }

        public void setProxyAddr(String proxyAddr) {
            this.proxyAddr = proxyAddr;
        }

        public String getReceiverAddr() {
            return receiverAddr;
        }

        public void setReceiverAddr(String receiverAddr) {
            this.receiverAddr = receiverAddr;
        }

        public String getSysFlowNo() {
            return sysFlowNo;
        }

        public void setSysFlowNo(String sysFlowNo) {
            this.sysFlowNo = sysFlowNo;
        }

        public String getTokenAddress() {
            return tokenAddress;
        }

        public void setTokenAddress(String tokenAddress) {
            this.tokenAddress = tokenAddress;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
