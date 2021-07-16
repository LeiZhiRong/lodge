package com.shags.lodge.Vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.shags.lodge.util.CmsUtils;

public class BillApplyInfoImportVo {

    /**
     * 申请部门编号
     */
    @Excel(name = "申请部门*", width = 15)
    private String applyDept="";

    /**
     * 购方名称
     */
    @Excel(name = "购买方*", orderNum = "2", width = 15)
    private String buyerCorp="";

    /**
     * 购方开户银行
     */
    @Excel(name = "购买方开户银行", orderNum = "3", width = 15)
     private String buyKhyh="";

    /**
     * 购方银行账号
     */
    @Excel(name = "购买方银行账号", orderNum = "4", width = 15)
    private String  buyYhzh="";

    /**
     * 销方名称
     */
    @Excel(name = "销售方*", orderNum = "5", width = 15)
    private String saleCorp="";

    /**
     * 销方开户银行
     */
    @Excel(name = "销售方开户银行", orderNum = "6", width = 15)
    private String saleKhyh="";

    /**
     * 销方银行账号
     */
    @Excel(name = "销售方银行账号", orderNum = "7", width = 15)
    private String saleYhzh="";

    /**
     * 费项名称
     */
    @Excel(name = "收支费项*", orderNum = "8", width = 15)
    private String  proceed="";

    /**
     * 发票类型
     */
    @Excel(name = "发票类型*", orderNum = "9", width = 15)
    private String billType="";

    /**
     * 发票代码
     */
    @Excel(name = "发票代码", orderNum = "10", width = 15)
    private String billCode;

    /**
     * 发票号码
     */
    @Excel(name = "发票号码", orderNum = "11", width = 15)
    private String billNumber;

    /**
     * 发票金额
     */
    @Excel(name = "发票金额*", orderNum = "12", width = 15)
    private String billMoney="0.00";

    /**
     * 税率
     */
    @Excel(name = "税率(%)*", orderNum = "13", width = 15)
    private String taxRate="0.00";

    /**
     * 结算方式
     */
    @Excel(name = "结算方式*", orderNum = "14", width = 15)
    private String paymentMethod="";

    /**
     * 是否挂账
     */
    @Excel(name = "是否挂账*", orderNum = "15", width = 15)
    private String onAccount="";

    /**
     * 备注
     */
    @Excel(name = "开票备注", orderNum = "16", width = 15)
    private String remarks="";


    @Excel(name = "摘要", orderNum = "17", width = 15)
    private String summary="";


    private String describe;

    /**
     * 检测结果：“T”通过检测，“F”未通过检测
     */
    private String checkStr;


    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckStr() {
        return checkStr;
    }

    public void setCheckStr(String checkStr) {
        this.checkStr = checkStr;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
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

    public BillApplyInfoImportVo() {
        super();
    }


    public BillApplyInfoImportVo formatBillApplyInfoDto(BillApplyInfoImportVo vo) {
        BillApplyInfoImportVo info = new BillApplyInfoImportVo();
        if (vo != null) {
            info.setApplyDept(CmsUtils.strToTrim(vo.getApplyDept()));
            info.setBuyerCorp(CmsUtils.strToTrim(vo.getBuyerCorp()));
            info.setBillMoney(CmsUtils.strToTrim(vo.getBillMoney()));
            info.setBillType(CmsUtils.strToTrim(vo.getBillType()));
            info.setTaxRate(CmsUtils.strToTrim(vo.getTaxRate()));
            info.setRemarks(CmsUtils.strToTrim(vo.getRemarks()));
            info.setBuyKhyh(CmsUtils.strToTrim(vo.getBuyKhyh()));
            info.setBuyYhzh(CmsUtils.strToTrim(vo.getBuyYhzh()));
            info.setSaleCorp(CmsUtils.strToTrim(vo.getSaleCorp()));
            info.setSaleKhyh(CmsUtils.strToTrim(vo.getSaleKhyh()));
            info.setSaleYhzh(CmsUtils.strToTrim(vo.getSaleYhzh()));
            info.setProceed(CmsUtils.strToTrim(vo.getProceed()));
            info.setBillType(CmsUtils.strToTrim(vo.getBillType()));
            info.setPaymentMethod(CmsUtils.strToTrim(vo.getPaymentMethod()));
            info.setOnAccount(CmsUtils.strToTrim(vo.getOnAccount()));
            info.setSummary(CmsUtils.strToTrim(vo.getSummary()));
            info.setRemarks(CmsUtils.strToTrim(vo.getRemarks()));
            info.setBillCode(CmsUtils.strToTrim(vo.getBillCode()));
            info.setBillNumber(CmsUtils.strToTrim(vo.getBillNumber()));
        }
        return info;
    }

}
