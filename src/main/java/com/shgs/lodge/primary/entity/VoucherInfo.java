package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 凭证记录
 *
 * @author 雷智荣
 */

@Entity
@Table(name = "voucher_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class VoucherInfo {

    /**
     * 关键字
     */
    private String id;

    /**
     * 单据ID
     */
    private String applyId;

    /**
     * 单据编号
     */
    private String applyBH;

    /**
     * 所属账套
     */
    private String bookSet;

    /**
     * 创建日期
     */
    private Timestamp creationTime;

    /**
     * 会计期间
     */
    private String periodMonth;

    /**
     * 摘要
     */
    private String describe;

    /**
     * 科目
     */
    private CashBank cashBank;

    /**
     * 借贷方向
     */
    private String direction;

    /**
     * 费项ID
     */
    private String proceedId;

    /**
     * 费项名称
     */
    private String proceedName;

    /**
     * 表单状态
     */
    private String auditStatus;

    /**
     * 挂账状态
     */
    private String onAccount;

    /**
     * 结算方式
     */
    private String paymentMethod;

    /**
     * 申请部门ID
     */
    private String applyDeptId;

    /**
     * 申请部门名称
     */
    private String applyDeptName;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 客商ID
     */
    private String corpId;

    /**
     * 客商名称
     */
    private String corpName;

    /**
     * 销售方ID
     */
    private String saleCorpId;

    /**
     * 销售方名称
     */
    private String saleCorpName;

    /**
     * 销售方开户银行
     */
    private String saleKhyh;

    /**
     * 销售方银行账号
     */
    private String saleYhzh;

    /**
     * 购买方ID
     */
    private String buyCorpID;

    /**
     * 购买方名称
     */
    private String buyCorpName;

    /**
     * 购买方开户银行
     */
    private String buyKhyh;

    /**
     * 购买方银行账号
     */
    private String buyYhzh;


    /**
     * 收支项目
     */
    private String szxm;

    /**
     * 营业项目
     */
    private String yinyexm;

    /**
     * 物料基本分类名称
     */
    private String wljbflxm;

    /**
     * 物流费用项目名称
     */
    private String wlfyxm;

    /**
     * 地区分类码名称
     */
    private String dqflm;


    /**
     * 含税金额
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
     * 时间戳
     */
    private String rVTime;

    /**
     * 余额方向
     */
    private String balanceDirection;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(String periodMonth) {
        this.periodMonth = periodMonth;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @ManyToOne
    @JoinColumn(name = "cashBank")
    public CashBank getCashBank() {
        return cashBank;
    }

    public void setCashBank(CashBank cashBank) {
        this.cashBank = cashBank;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getSzxm() {
        return szxm;
    }

    public void setSzxm(String szxm) {
        this.szxm = szxm;
    }

    public String getYinyexm() {
        return yinyexm;
    }

    public void setYinyexm(String yinyexm) {
        this.yinyexm = yinyexm;
    }

    public String getWljbflxm() {
        return wljbflxm;
    }

    public void setWljbflxm(String wljbflxm) {
        this.wljbflxm = wljbflxm;
    }

    public String getWlfyxm() {
        return wlfyxm;
    }

    public void setWlfyxm(String wlfyxm) {
        this.wlfyxm = wlfyxm;
    }

    public String getDqflm() {
        return dqflm;
    }

    public void setDqflm(String dqflm) {
        this.dqflm = dqflm;
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

    public String getApplyDeptId() {
        return applyDeptId;
    }

    public void setApplyDeptId(String applyDeptId) {
        this.applyDeptId = applyDeptId;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getSaleCorpId() {
        return saleCorpId;
    }

    public void setSaleCorpId(String saleCorpId) {
        this.saleCorpId = saleCorpId;
    }

    public String getSaleCorpName() {
        return saleCorpName;
    }

    public void setSaleCorpName(String saleCorpName) {
        this.saleCorpName = saleCorpName;
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

    public String getBuyCorpID() {
        return buyCorpID;
    }

    public void setBuyCorpID(String buyCorpID) {
        this.buyCorpID = buyCorpID;
    }

    public String getBuyCorpName() {
        return buyCorpName;
    }

    public void setBuyCorpName(String buyCorpName) {
        this.buyCorpName = buyCorpName;
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

    public VoucherInfo() {
        super();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyBH() {
        return applyBH;
    }

    public void setApplyBH(String applyBH) {
        this.applyBH = applyBH;
    }

    public String getProceedId() {
        return proceedId;
    }

    public void setProceedId(String proceedId) {
        this.proceedId = proceedId;
    }

    public String getProceedName() {
        return proceedName;
    }

    public void setProceedName(String proceedName) {
        this.proceedName = proceedName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOnAccount() {
        return onAccount;
    }

    public void setOnAccount(String onAccount) {
        this.onAccount = onAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }
}
