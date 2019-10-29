package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WalletInfoResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : SUCCESS
     * data : [{"returnCode":"0000","returnMsg":"成功","data":null,"id":58,"name":"USDT","tokenAddress":"0x0f114A1E9Db192502E7856309cc899952b3db1ED","customerAddress":"0x3690d089407ae0a4e056f789829d5bebe9441fa4","proxyAddress":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","channelAmount":231.59219978,"tokenAmount":18},{"returnCode":"0000","returnMsg":"成功","data":null,"id":93,"name":"USDC","tokenAddress":"0x0f114A1E9Db192502E7856309cc899952b3db1EDaa","customerAddress":"0x3690d089407ae0a4e056f789829d5bebe9441fa4","proxyAddress":"0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20","channelAmount":0,"tokenAmount":18}]
     * raidenChannelResp : null
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private List<CryptoInfo> data;

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

    public List<CryptoInfo> getData() {
        return data;
    }

    public void setData(List<CryptoInfo> data) {
        this.data = data;
    }

    public static class CryptoInfo {
        /**
         * returnCode : 0000
         * returnMsg : 成功
         * data : null
         * id : 58
         * name : USDT
         * tokenAddress : 0x0f114A1E9Db192502E7856309cc899952b3db1ED
         * customerAddress : 0x3690d089407ae0a4e056f789829d5bebe9441fa4
         * proxyAddress : 0x0B14a96CbEd20797341EF18bE27Ec80b7B7e5F20
         * channelAmount : 231.59219978
         * tokenAmount : 18
         */

        @SerializedName("id")
        private int id;
        @SerializedName("tokenId")
        private String tokenId;
        @SerializedName("name")
        private String name;
        @SerializedName("tokenAddress")
        private String tokenAddress;
        @SerializedName("customerAddress")
        private String customerAddress;
        @SerializedName("proxyAddress")
        private String proxyAddress;
        @SerializedName("channelAmount")
        private double channelAmount;
        @SerializedName("freezeAmount")
        private double freezeAmount;
        @SerializedName("needAmount")
        private double needAmount;
        @SerializedName("tokenAmount")
        private double tokenAmount;
        @SerializedName("orderAmount")
        private double orderAmount;
        @SerializedName("iconUrl")
        private String iconUrl;

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTokenAddress() {
            return tokenAddress;
        }

        public void setTokenAddress(String tokenAddress) {
            this.tokenAddress = tokenAddress;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getProxyAddress() {
            return proxyAddress;
        }

        public void setProxyAddress(String proxyAddress) {
            this.proxyAddress = proxyAddress;
        }

        public double getChannelAmount() {
            return channelAmount;
        }

        public void setChannelAmount(double channelAmount) {
            this.channelAmount = channelAmount;
        }

        public double getFreezeAmount() {
            return freezeAmount;
        }

        public void setFreezeAmount(double freezeAmount) {
            this.freezeAmount = freezeAmount;
        }

        public double getNeedAmount() {
            return needAmount;
        }

        public void setNeedAmount(double needAmount) {
            this.needAmount = needAmount;
        }

        public double getTokenAmount() {
            return tokenAmount;
        }

        public void setTokenAmount(double tokenAmount) {
            this.tokenAmount = tokenAmount;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
