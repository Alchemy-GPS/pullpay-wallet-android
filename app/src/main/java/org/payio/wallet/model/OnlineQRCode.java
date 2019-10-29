package org.payio.wallet.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnlineQRCode implements Serializable {

    /**
     * agentCode : MXC123
     * outFlowNo : 324354546
     */

    @SerializedName("agentCode")
    private String agentCode;
    @SerializedName("outFlowNo")
    private String outFlowNo;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getOutFlowNo() {
        return outFlowNo;
    }

    public void setOutFlowNo(String outFlowNo) {
        this.outFlowNo = outFlowNo;
    }
}
