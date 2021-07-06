package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountListDto {
    /**
     * 关键字
     */
    private String id;

    /**
     * 开户银行
     */
    private String khyh;

    /**
     * 银行帐号
     *
     */
    private String yhzh;

    private String handle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public BankAccountListDto() {
        super();
    }

    public BankAccountListDto(BankAccount bankAccount) {
        this.setId(bankAccount.getId());
        this.setKhyh(bankAccount.getKhyh());
        this.setYhzh(bankAccount.getYhzh());
        String str=bankAccount.getKhyh()+"【"+bankAccount.getYhzh()+"】";
        this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getBankAccountKhyhAndYhzh(&quot;" + bankAccount.getKhyh() + "&quot;,&quot;" + bankAccount.getYhzh() + "&quot;);'>"+str+"</a></div>");
    }

    public List<BankAccountListDto> listBankAccountListDto(List<BankAccount> list) {
        List<BankAccountListDto> cts = new ArrayList<>();
        if (list.size() > 0) {
            for (BankAccount mast : list) {
                cts.add(new BankAccountListDto(mast));
            }
        }
        return cts;
    }


}
