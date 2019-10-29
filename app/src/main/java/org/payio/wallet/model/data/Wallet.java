package org.payio.wallet.model.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Wallet {

    @Id
    private String walletAddress;
    private String walletPath;
    private String walletName;
    private String walletPassword;
    private String createTime;
    private String privateKey;
    private String keystore;
    @Generated(hash = 1032898205)
    public Wallet(String walletAddress, String walletPath, String walletName,
            String walletPassword, String createTime, String privateKey,
            String keystore) {
        this.walletAddress = walletAddress;
        this.walletPath = walletPath;
        this.walletName = walletName;
        this.walletPassword = walletPassword;
        this.createTime = createTime;
        this.privateKey = privateKey;
        this.keystore = keystore;
    }
    @Generated(hash = 1197745249)
    public Wallet() {
    }
    public String getWalletAddress() {
        return this.walletAddress;
    }
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    public String getWalletPath() {
        return this.walletPath;
    }
    public void setWalletPath(String walletPath) {
        this.walletPath = walletPath;
    }
    public String getWalletName() {
        return this.walletName;
    }
    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
    public String getWalletPassword() {
        return this.walletPassword;
    }
    public void setWalletPassword(String walletPassword) {
        this.walletPassword = walletPassword;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getPrivateKey() {
        return this.privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getKeystore() {
        return this.keystore;
    }
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

}
