package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnlineRaidenPayRequest implements Serializable{

    @SerializedName("outFlowNo")
    private String outFlowNo;
    @SerializedName("agentCode")
    private String agentCode;
    @SerializedName("tokenAmount")
    private String tokenAmount;
    @SerializedName("customerAddr")
    private String customerAddr;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("tokenAddress")
    private String tokenAddress;
    @SerializedName("tokenName")
    private String tokenName;

    public OnlineRaidenPayRequest(String customerAddr, String customerId) {
        this.customerAddr = customerAddr;
        this.customerId = customerId;
    }

    public String getOutFlowNo() {
        return outFlowNo;
    }

    public void setOutFlowNo(String outFlowNo) {
        this.outFlowNo = outFlowNo;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(String tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
