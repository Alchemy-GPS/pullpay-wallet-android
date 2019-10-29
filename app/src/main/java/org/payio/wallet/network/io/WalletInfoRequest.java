package org.payio.wallet.network.io;

import java.io.Serializable;

public class WalletInfoRequest implements Serializable {

    public String outFlowNo;
    public String agentCode;
    public String userAddr;

    public WalletInfoRequest(String userAddr) {
        this.userAddr = userAddr;
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

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }
}
