package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import org.payio.wallet.utils.CommonUtil;

public class OTCOrderRequest {

    @SerializedName("orderStatus")
    private String orderStatus;//订单状态
    @SerializedName("page")
    private int page;//第几页
    @SerializedName("content")
    private String content;//搜索内容
    @SerializedName("userId")
    private String userId = CommonUtil.getUserId();//用户Id

    public OTCOrderRequest(String orderStatus, int page, String content) {
        this.orderStatus = orderStatus;
        this.page = page;
        this.content = content;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
