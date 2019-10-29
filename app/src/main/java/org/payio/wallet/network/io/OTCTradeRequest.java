package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OTCTradeRequest implements Serializable {

    /**
     * type : IN
     * currencyType : USDT
     * page : 1
     */

    @SerializedName("type")
    private String type;
    @SerializedName("currencyType")
    private String currencyType;
    @SerializedName("page")
    private int page;

    public OTCTradeRequest(String type, String currencyType, int page) {
        this.type = type;
        this.currencyType = currencyType;
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
