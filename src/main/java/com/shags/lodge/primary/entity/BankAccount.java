package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill_bank_account")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class BankAccount {
    /**
     * 关键字
     */
    private String id;

    /**
     * 客商ID
     */
    private String corpId;

    /**
     * 开户银行
     */
     private String khyh;

    /**
     * 银行帐号
     * 
     */
    private String yhzh;

    /**
     * 默认标识
     */
    private String moren;

    /**
     * 状态标识
     */
     private String ztbz;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getKhyh() {
        return khyh;
    }

    public void setKhyh(String khyh) {
        this.khyh = khyh;
    }

    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public BankAccount() {
        super();
    }

    public String getMoren() {
        return moren;
    }

    public void setMoren(String moren) {
        this.moren = moren;
    }

    public BankAccount(String corpId, String khyh, String yhzh, String ztbz) {
        this.corpId = corpId;
        this.khyh = khyh;
        this.yhzh = yhzh;
        this.ztbz = ztbz;
    }

    public BankAccount(String id, String khyh, String yhzh) {
        this.id = id;
        this.khyh = khyh;
        this.yhzh = yhzh;
    }

}
