package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 团购挂单-账务信息实体类
 * @author 雷智荣
 */
@Entity
@Table(name = "bill_accoun_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class BillAccounInfo {

    /**
     * 交易流水号
     */
    private String id;

    /**
     * 交易编号
     */
    private String transactionNumber;

    /**
     * 入账时间
     */
    private Timestamp entryTime;

    /**
     * 团购订单号
     */
    private String applyBH;

    /**
     * 交易部门
     */
    private DeptInfo deptInfo;

    /**
     * 账务类型
     */
    private String accounType;

    /**
     * 发票类型
     */
    private String billType;

    /**
     * 收入含税金额
     */
    private Double amountTaxMoney;

    /**
     * 税率
     */
    private Double taxRate;

    /**
     * 合计税额
     */
    private Double countTaxMoney;

    /**
     * 收入不含税金额
     */
    private Double amountMoney;

    /**
     * 支出含税金额
     */
    private Double expendTaxMoney;

    /**
     * 支出不含税金额
     */
    private Double expendMoney;

    /**
     * 含税余额
     */
    private Double balanceTaxMoney;

    /**
     * 不含税余额
     */
    private Double balanceMoney;

    /**
     * 销售方信息
     */
    private BillCorpInfo saleCorp;

    /**
     * 购买方信息
     */
    private BillCorpInfo buyerCorp;

    /**
     * 排序号
     */
    private int orders;

    /**
     * 账套
     */
    private String bookSet;

    /**
     * 状态标识(T=已做凭证处理,F未做凭证处理)
     */
    private String ztbz;

    /**
     * 时间戳
     */
    private String rVTime;

    /**
     * 备注
     */
    private String remarks="";

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getApplyBH() {
        return applyBH;
    }

    public void setApplyBH(String applyBH) {
        this.applyBH = applyBH;
    }

    @ManyToOne
    @JoinColumn(name = "deptInfo")
    public DeptInfo getDeptInfo() {
        return deptInfo;
    }

    public void setDeptInfo(DeptInfo deptInfo) {
        this.deptInfo = deptInfo;
    }

    public String getAccounType() {
        return accounType;
    }

    public void setAccounType(String accounType) {
        this.accounType = accounType;
    }

    public Double getAmountTaxMoney() {
        return amountTaxMoney;
    }

    public void setAmountTaxMoney(Double amountTaxMoney) {
        this.amountTaxMoney = amountTaxMoney;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getCountTaxMoney() {
        return countTaxMoney;
    }

    public void setCountTaxMoney(Double countTaxMoney) {
        this.countTaxMoney = countTaxMoney;
    }

    public Double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(Double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public Double getExpendTaxMoney() {
        return expendTaxMoney;
    }

    public void setExpendTaxMoney(Double expendTaxMoney) {
        this.expendTaxMoney = expendTaxMoney;
    }

    public Double getExpendMoney() {
        return expendMoney;
    }

    public void setExpendMoney(Double expendMoney) {
        this.expendMoney = expendMoney;
    }

    public Double getBalanceTaxMoney() {
        return balanceTaxMoney;
    }

    public void setBalanceTaxMoney(Double balanceTaxMoney) {
        this.balanceTaxMoney = balanceTaxMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    @ManyToOne
    @JoinColumn(name = "saleCorp")
    public BillCorpInfo getSaleCorp() {
        return saleCorp;
    }

    public void setSaleCorp(BillCorpInfo saleCorp) {
        this.saleCorp = saleCorp;
    }
    @ManyToOne
    @JoinColumn(name = "buyerCorp")
    public BillCorpInfo getBuyerCorp() {
        return buyerCorp;
    }

    public void setBuyerCorp(BillCorpInfo buyerCorp) {
        this.buyerCorp = buyerCorp;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
