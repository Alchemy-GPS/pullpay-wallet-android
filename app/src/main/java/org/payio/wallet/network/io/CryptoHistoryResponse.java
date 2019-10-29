package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CryptoHistoryResponse implements Serializable {

    /**
     * returnCode : 0000
     * returnMsg : 成功
     * data : {"freezeAmount":"0.00","list":[{"time":"2019-01-09 13:52:15","amount":"-0.20USDT","name":"转币成功"},{"time":"2019-01-09 13:50:20","amount":"-3.50USDT","name":"转币成功"}],"needAmount":"343.82"}
     */

    @SerializedName("returnCode")
    private String returnCode;
    @SerializedName("returnMsg")
    private String returnMsg;
    @SerializedName("data")
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * freezeAmount : 0.00
         * list : [{"time":"2019-01-09 13:52:15","amount":"-0.20USDT","name":"转币成功"},{"time":"2019-01-09 13:50:20","amount":"-3.50USDT","name":"转币成功"}]
         * needAmount : 343.82
         */

        @SerializedName("freezeAmount")
        private String freezeAmount;
        @SerializedName("needAmount")
        private String needAmount;
        @SerializedName("list")
        private List<CryptoHistory> historyList;

        public String getFreezeAmount() {
            return freezeAmount;
        }

        public void setFreezeAmount(String freezeAmount) {
            this.freezeAmount = freezeAmount;
        }

        public String getNeedAmount() {
            return needAmount;
        }

        public void setNeedAmount(String needAmount) {
            this.needAmount = needAmount;
        }

        public List<CryptoHistory> getList() {
            return historyList;
        }

        public void setList(List<CryptoHistory> historyList) {
            this.historyList = historyList;
        }

        public static class CryptoHistory {
            /**
             * time : 2019-01-09 13:52:15
             * amount : -0.20USDT
             * name : 转币成功
             */

            @SerializedName("time")
            private String time;
            @SerializedName("amount")
            private String amount;
            @SerializedName("name")
            private String name;
            @SerializedName("orderId")
            private long orderId;
            @SerializedName("type")
            private int type;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
                this.orderId = orderId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
