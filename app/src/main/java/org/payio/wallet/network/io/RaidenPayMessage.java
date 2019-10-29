package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RaidenPayMessage implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : SUCCESS
     * data : {"createTime":1536068965000,"customerAddr":"0x4072c97d0429c5e02824bf94681d45ea5c4428ea","id":354,"orderAmount":0.84961767,"orderStatus":"4","payUrl":"http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.84961767&sysFlowNo=gw2018090413492500001&userAddr=","proxyAddr":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","receiverAddr":"0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a","sysFlowNo":"gw2018090413492500001","tokenAddress":"0x822925476aF6C7baE9667C09161Ea84294be2500","updateTime":1536068966000}
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message {
        /**
         * createTime : 1536068965000
         * customerAddr : 0x4072c97d0429c5e02824bf94681d45ea5c4428ea
         * id : 354
         * orderAmount : 0.84961767
         * orderStatus : 4
         * payUrl : http://13.250.21.97:9099/raidenNetwork/customer/channelPayment?token=0x822925476aF6C7baE9667C09161Ea84294be2500&proxy=0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20&amount=0.84961767&sysFlowNo=gw2018090413492500001&userAddr=
         * proxyAddr : 0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20
         * receiverAddr : 0xB770682b5Cd0eD15e1e8056Fb058bdCF611C703a
         * sysFlowNo : gw2018090413492500001
         * tokenAddress : 0x822925476aF6C7baE9667C09161Ea84294be2500
         * updateTime : 1536068966000
         */

        @SerializedName("createTime")
        private long createTime;
        @SerializedName("customerAddr")
        private String customerAddr;
        @SerializedName("id")
        private int id;
        @SerializedName("orderAmount")
        private double orderAmount;
        @SerializedName("orderStatus")
        private String orderStatus;
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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

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
