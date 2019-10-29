package org.payio.wallet.model.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Cryptocurrency {
    @Id
    private long id;
    private String contract;//币种合约地址也是唯一的
    private String logo;
    private String unit;
    private String name;
    private String balance;
    private String proxy;
    private String address;
    private String needAmount;
    private String freezeAmount;
    @Generated(hash = 353077197)
    public Cryptocurrency(long id, String contract, String logo, String unit,
            String name, String balance, String proxy, String address,
            String needAmount, String freezeAmount) {
        this.id = id;
        this.contract = contract;
        this.logo = logo;
        this.unit = unit;
        this.name = name;
        this.balance = balance;
        this.proxy = proxy;
        this.address = address;
        this.needAmount = needAmount;
        this.freezeAmount = freezeAmount;
    }
    @Generated(hash = 64960040)
    public Cryptocurrency() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getContract() {
        return this.contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBalance() {
        return this.balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public String getProxy() {
        return this.proxy;
    }
    public void setProxy(String proxy) {
        this.proxy = proxy;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNeedAmount() {
        return this.needAmount;
    }
    public void setNeedAmount(String needAmount) {
        this.needAmount = needAmount;
    }
    public String getFreezeAmount() {
        return this.freezeAmount;
    }
    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }
}
