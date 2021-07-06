package com.shgs.lodge.Vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.BeanUtil;

import java.util.ArrayList;
import java.util.List;

public class BillApplyInfoExportVo {

    /**
     * 申请单编号
     */
    @Excel(name = "单据编号", orderNum = "0", width = 20)
    private String applyBH;

    /**
     * 申请日期
     */
    @Excel(name = "申请日期", orderNum = "1", width = 10)
    private String applyDate;


    /**
     * 申请部门
     */
    @Excel(name = "申请部门", orderNum = "2", width = 20)
    private String applyDept = "";

    /**
     * 购方名称
     */
    @Excel(name = "购买方", orderNum = "3", width = 20)
    private String buyerCorp = "";

    /**
     * 购方开户银行
     */
    @Excel(name = "购买方开户银行", orderNum = "4", width = 20)
    private String buyKhyh = "";

    /**
     * 购方银行账号
     */
    @Excel(name = "购买方银行账号", orderNum = "5", width = 20)
    private String buyYhzh = "";

    /**
     * 销方名称
     */
    @Excel(name = "销售方", orderNum = "6", width = 20)
    private String saleCorp = "";

    /**
     * 销方开户银行
     */
    @Excel(name = "销售方开户银行", orderNum = "7", width = 20)
    private String saleKhyh = "";

    /**
     * 销方银行账号
     */
    @Excel(name = "销售方银行账号", orderNum = "8", width = 20)
    private String saleYhzh = "";

    /**
     * 费项名称
     */
    @Excel(name = "收支费项", orderNum = "9", width = 15)
    private String proceed = "";

    /**
     * 结算方式
     */
    @Excel(name = "结算方式", orderNum = "10", width = 15)
    private String paymentMethod = "";

    /**
     * 发票类型
     */
    @Excel(name = "发票类型", orderNum = "11", width = 15)
    private String billType = "";

    /**
     * 发票代码
     */
    @Excel(name = "发票代码", orderNum = "12", width = 15)
    private String billCode;

    /**
     * 发票号码
     */
    @Excel(name = "发票号码", orderNum = "13", width = 15)
    private String billNumber;

    /**
     * 发票金额
     */
    @Excel(name = "发票金额(元)", orderNum = "14", numFormat = "#,##0.00", width = 15)
    private Double billMoney;

    /**
     * 税率
     */
    @Excel(name = "税率", orderNum = "15", numFormat = "0", suffix = "%", width = 10)
    private Double taxRate;

    /**
     * 税额
     */
    @Excel(name = "税额(元)", orderNum = "16", numFormat = "#,##0.00", width = 10)
    private Double taxMoney;

    /**
     * 不含税金额
     */
    @Excel(name = "不含税金额(元)", orderNum = "17", numFormat = "#,##0.00", width = 15)
    private Double amountMoney;


    @Excel(name = "摘要", orderNum = "18", width = 15)
    private String summary = "";


    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }


    public String getApplyBH() {
        return applyBH;
    }

    public void setApplyBH(String applyBH) {
        this.applyBH = applyBH;
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

    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
    }

    public String getBuyerCorp() {
        return buyerCorp;
    }

    public void setBuyerCorp(String buyerCorp) {
        this.buyerCorp = buyerCorp;
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

    public String getSaleCorp() {
        return saleCorp;
    }

    public void setSaleCorp(String saleCorp) {
        this.saleCorp = saleCorp;
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

    public String getProceed() {
        return proceed;
    }

    public void setProceed(String proceed) {
        this.proceed = proceed;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
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

    public BillApplyInfoExportVo() {
        super();
    }


    public BillApplyInfoExportVo(BillApplyInfo info) {
        this.setApplyBH(info.getApplyBH());
        this.setApplyDept(info.getApplyDept() != null ? info.getApplyDept().getDeptName() : "");
        this.setAmountMoney(info.getAmountMoney());
        this.setApplyDate(info.getApplyDate() != null ? BeanUtil.timestampToStr(info.getApplyDate(), "yyyy-MM-dd") : "");
        this.setBillCode(info.getBillCode());
        this.setBillMoney(info.getBillMoney());
        this.setBillNumber(info.getBillNumber());
        this.setBillType(info.getBillType());
        this.setBuyKhyh(info.getBuyKhyh());
        this.setBuyYhzh(info.getBuyYhzh());
        this.setBuyerCorp(info.getBuyerCorp() != null ? info.getBuyerCorp().getCorpMC() : "");
        this.setSaleCorp(info.getSaleCorp() != null ? info.getSaleCorp().getCorpMC() : "");
        this.setSaleKhyh(info.getSaleKhyh());
        this.setSaleYhzh(info.getSaleYhzh());
        this.setSummary(info.getDescribe());
        this.setPaymentMethod(info.getPaymentMethod());
        this.setProceed(info.getProceedName());
        this.setTaxMoney(info.getTaxMoney());
        this.setTaxRate(info.getTaxRate());
    }

    public List<BillApplyInfoExportVo> listBillApplyInfoExportVo(List<BillApplyInfo> billApplyInfoList) {
        List<BillApplyInfoExportVo> dto = new ArrayList<>();
        if (billApplyInfoList.size() > 0) {
            for (BillApplyInfo mast : billApplyInfoList) {
                dto.add(new BillApplyInfoExportVo(mast));
            }
        }
        return dto;
    }

}
