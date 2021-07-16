package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 团购挂单-发票申请信息表
 *
 * @author 雷智荣
 */
@Entity
@Table(name = "bill_apply_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class BillApplyInfo {

    /**
     * 关键字
     */
    private String id;

    /**
     * 申请单编号
     */
    private String applyBH;

    /**
     * 会计期间
     */
    private String periodMonth;

    /**
     * 收支费项名称
     */
    private String  proceedName;

    /**
     * 收支费项id
     */
    private String  proceedId;

    /**
     * 结算方式
     */
    private String paymentMethod;

    /**
     * 是否挂账
     */
    private String onAccount;

    /**
     * 申请部门
     */
    private DeptInfo applyDept;

    /**
     * 申请人编号
     */
    private String applyUserBH;

    /**
     * 申请人名称
     */
    private String applyUserName;

    /**
     * 结算部门编号
     */
    private String setllDeptBH;

    /**
     * 申请时间
     */
    private Timestamp applyDate;

    /**
     * 销售方信息
     */
    private BillCorpInfo saleCorp;

    /**
     * 销售方开户银行
     */
    private String saleKhyh;

    /**
     * 销售方银行账号
     */
    private String saleYhzh;

    /**
     * 购买方信息
     */
    private BillCorpInfo buyerCorp;

    /**
     * 购买方开户银行
     */
    private String buyKhyh;

    /**
     * 购买方银行账号
     */
    private String  buyYhzh;


    /**
     * 发票类型
     */
    private String billType;

    /**
     * 发票代码
     */
    private String billCode;

    /**
     * 发票号码
     */
    private String billNumber;

    /**
     * 发票金额
     */
    private Double billMoney;

    /**
     * 税率
     */
    private Double taxRate;

    /**
     * 税额
     */
    private Double taxMoney;

    /**
     * 不含税金额
     */
    private Double amountMoney;

    /**
     * 单据状态
     */
    private String ztbz;

    /**
     * 余额
     */
    private Double balanceMoney;

    /**
     * 开票人
     */
    private String drawerUser;

    /**
     * 收款人
     */
    private String payeeUser;

    /**
     * 复核人
     */
    private String reviewerUser;

    /**
     * 开票日期
     */
    private Timestamp invoiceDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 排序号
     */
    private int orders;

    /**
     * 帐套编号
     */
    private String bookSet;

    /**
     * 时间戳
     */
    private String rVTime;

    /**
     * 进项税档案名称
     */
    private String jxsdaName;

    /**
     * 摘要
     */
    private String describe;


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyBH() {
        return applyBH;
    }

    public void setApplyBH(String applyBH) {
        this.applyBH = applyBH;
    }

    @ManyToOne
    @JoinColumn(name = "dept_id")
    public DeptInfo getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(DeptInfo applyDept) {
        this.applyDept = applyDept;
    }

    public String getApplyUserBH() {
        return applyUserBH;
    }

    public void setApplyUserBH(String applyUserBH) {
        this.applyUserBH = applyUserBH;
    }

    public String getSetllDeptBH() {
        return setllDeptBH;
    }

    public void setSetllDeptBH(String setllDeptBH) {
        this.setllDeptBH = setllDeptBH;
    }

    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    @ManyToOne
    @JoinColumn(name = "sale_corp")
    public BillCorpInfo getSaleCorp() {
        return saleCorp;
    }

    public void setSaleCorp(BillCorpInfo saleCorp) {
        this.saleCorp = saleCorp;
    }

    @ManyToOne
    @JoinColumn(name = "buyer_corp")
    public BillCorpInfo getBuyerCorp() {
        return buyerCorp;
    }

    public void setBuyerCorp(BillCorpInfo buyerCorp) {
        this.buyerCorp = buyerCorp;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public BillApplyInfo() {
        super();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Double getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(Double billMoney) {
        this.billMoney = billMoney;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getDrawerUser() {
        return drawerUser;
    }

    public void setDrawerUser(String drawerUser) {
        this.drawerUser = drawerUser;
    }

    public String getPayeeUser() {
        return payeeUser;
    }

    public void setPayeeUser(String payeeUser) {
        this.payeeUser = payeeUser;
    }

    public String getReviewerUser() {
        return reviewerUser;
    }

    public void setReviewerUser(String reviewerUser) {
        this.reviewerUser = reviewerUser;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(Double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }

    public String getSaleKhyh() {
        return saleKhyh;
    }

    public void setSaleKhyh(String saleKhyh) {
        this.saleKhyh = saleKhyh;
    }

    public String getSaleYhzh() {
        return saleYhzh;
    }

    public void setSaleYhzh(String saleYhzh) {
        this.saleYhzh = saleYhzh;
    }

    public String getBuyKhyh() {
        return buyKhyh;
    }

    public void setBuyKhyh(String buyKhyh) {
        this.buyKhyh = buyKhyh;
    }

    public String getBuyYhzh() {
        return buyYhzh;
    }

    public void setBuyYhzh(String buyYhzh) {
        this.buyYhzh = buyYhzh;
    }

    public String getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(String periodMonth) {
        this.periodMonth = periodMonth;
    }

    public String getProceedName() {
        return proceedName;
    }

    public void setProceedName(String proceedName) {
        this.proceedName = proceedName;
    }

    public String getProceedId() {
        return proceedId;
    }

    public void setProceedId(String proceedId) {
        this.proceedId = proceedId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOnAccount() {
        return onAccount;
    }

    public void setOnAccount(String onAccount) {
        this.onAccount = onAccount;
    }

    public String getJxsdaName() {
        return jxsdaName;
    }

    public void setJxsdaName(String jxsdaName) {
        this.jxsdaName = jxsdaName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
