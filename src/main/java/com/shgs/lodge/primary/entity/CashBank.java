package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "cash_bank")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class CashBank {

    /**
     * 关键字
     */
    private String id;

    /**
     * 科目编号
     */
    private String kmBH;

    /**
     * 科目名称
     */
    private String kmMC;

    /**
     * 科目简称
     */
    private String kmJC;

    /**
     * 科目拼音码
     */
    private String kmPYM;

    /**
     * 状态标识
     */
    private String ztbz;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 上级分类
     */
    private CashBank parent;

    /**
     * 排序号
     */
    private int orders = 0;

    /**
     * ids
     */
    private String ids;

    /**
     * 是否包含下级目录
     */
    private String contents;

    /**
     * 借贷方向
     */
    private String balance;


    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKmBH() {
        return kmBH;
    }

    public void setKmBH(String kmBH) {
        this.kmBH = kmBH;
    }

    public String getKmMC() {
        return kmMC;
    }

    public void setKmMC(String kmMC) {
        this.kmMC = kmMC;
    }

    public String getKmJC() {
        return kmJC;
    }

    public void setKmJC(String kmJC) {
        this.kmJC = kmJC;
    }

    public String getKmPYM() {
        return kmPYM;
    }

    public void setKmPYM(String kmPYM) {
        this.kmPYM = kmPYM;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @ManyToOne
    @JoinColumn(name = "pid")
    public CashBank getParent() {
        return parent;
    }

    public void setParent(CashBank parent) {
        this.parent = parent;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    @Column(length = 2000)
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public CashBank() {
        super();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
