package org.payio.wallet.network.io;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    @SerializedName("phone")
    private String phone;
    @SerializedName("password")
    private String password;
    @SerializedName("ethAddress")
    private String ethAddress;
    @SerializedName("ethPassword")
    private String ethPassword;

    public RegisterRequest(String phone, String password, String ethAddress, String ethPassword) {
        this.phone = phone;
        this.password = password;
        this.ethAddress = ethAddress;
        this.ethPassword = ethPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getEthPassword() {
        return ethPassword;
    }

    public void setEthPassword(String ethPassword) {
        this.ethPassword = ethPassword;
    }
}
