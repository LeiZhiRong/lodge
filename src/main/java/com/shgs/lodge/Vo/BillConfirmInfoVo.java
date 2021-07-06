package com.shgs.lodge.Vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.CmsUtils;

import java.util.ArrayList;
import java.util.List;

public class BillConfirmInfoVo {

    /**
     * 申请部门
     */
    @Excel(name = "申请部门", orderNum = "0", width = 10)
    private String applyDeptBH = "";

    /**
     * 结算部门
     */
    @Excel(name = "结算部门", orderNum = "1", width = 10)
    private String setllDeptBH = "";

    /**
     * 销方名称
     */
    @Excel(name = "销售方", orderNum = "2", width = 20)
    private String saleCorpName = "";

    /**
     * 购方名称
     */
    @Excel(name = "购买方", orderNum = "3", width = 20)
    private String buyerCorpName = "";

    /**
     * 开票时间
     */
    @Excel(name = "开票时间", orderNum = "4", width = 15)
    private String invoiceDate = "";

    /**
     * 发票类型
     */
    @Excel(name = "发票类型", orderNum = "5", width = 10)
    private String billType = "";

    /**
     * 发票代码
     */
    @Excel(name = "发票代码", orderNum = "6", width = 15)
    private String billCode;

    /**
     * 发票号码
     */
    @Excel(name = "发票号码", orderNum = "7", width = 15)
    private String billNumber;

    /**
     * 开票金额
     */
    @Excel(name = "开票金额", orderNum = "8", width = 10)
    private String billMoney = "0.00";

    /**
     * 税率
     */
    @Excel(name = "税率(%)", orderNum = "9", width = 10)
    private String taxRate;

    /**
     * 税额
     */
    private String taxMoney;

    /**
     * 不含税金额
     */
    private String amountMoney;

    /**
     * 申请人编号
     */
    @Excel(name = "申请人", orderNum = "10", width = 10)
    private String applyUserBH = "";

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "11", width = 10)
    private String remarks = "";

    private String describe;

    /**
     * 检测结果：“T”通过检测，“F”未通过检测
     */
    private String checkStr;

    public String getApplyDeptBH() {
        return applyDeptBH;
    }

    public void setApplyDeptBH(String applyDeptBH) {
        this.applyDeptBH = applyDeptBH;
    }

    public String getSetllDeptBH() {
        return setllDeptBH;
    }

    public void setSetllDeptBH(String setllDeptBH) {
        this.setllDeptBH = setllDeptBH;
    }

    public String getSaleCorpName() {
        return saleCorpName;
    }

    public void setSaleCorpName(String saleCorpName) {
        this.saleCorpName = saleCorpName;
    }

    public String getBuyerCorpName() {
        return buyerCorpName;
    }

    public void setBuyerCorpName(String buyerCorpName) {
        this.buyerCorpName = buyerCorpName;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

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

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getApplyUserBH() {
        return applyUserBH;
    }

    public void setApplyUserBH(String applyUserBH) {
        this.applyUserBH = applyUserBH;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCheckStr() {
        return checkStr;
    }

    public void setCheckStr(String checkStr) {
        this.checkStr = checkStr;
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

    public BillConfirmInfoVo() {
        super();
    }

    public BillConfirmInfoVo formatBillApplyInfoDto(BillConfirmInfoVo vo) {
        BillConfirmInfoVo info = new BillConfirmInfoVo();
        if (vo != null) {
            info.setSetllDeptBH(CmsUtils.strToTrim(vo.getSetllDeptBH()));
            info.setApplyDeptBH(CmsUtils.strToTrim(vo.getApplyDeptBH()));
            info.setBillMoney(CmsUtils.strToTrim(vo.getBillMoney()));
            info.setBillType(CmsUtils.strToTrim(vo.getBillType()));
            info.setRemarks(CmsUtils.strToTrim(vo.getRemarks()));
            info.setBuyerCorpName(CmsUtils.strToTrim(vo.getBuyerCorpName()));
            info.setSaleCorpName(CmsUtils.strToTrim(vo.getSaleCorpName()));
            info.setAmountMoney(CmsUtils.strToTrim(vo.getAmountMoney()));
            info.setTaxMoney(CmsUtils.strToTrim(vo.getTaxMoney()));
            info.setTaxRate(CmsUtils.strToTrim(vo.getTaxRate()));
            info.setBillCode(CmsUtils.strToTrim(vo.getBillCode()));
            info.setBillNumber(CmsUtils.strToTrim(vo.getBillNumber()));
        }
        return info;
    }


    public BillConfirmInfoVo(BillApplyInfo billApplyInfo) {
        if (billApplyInfo != null) {
            this.setSetllDeptBH(billApplyInfo.getSetllDeptBH());
            this.setApplyDeptBH(billApplyInfo.getApplyDept().getDeptID());
            this.setApplyUserBH(billApplyInfo.getApplyUserBH());
            this.setBillMoney(String.valueOf(billApplyInfo.getBillMoney()));
            if (billApplyInfo.getTaxRate() != null)
                this.setTaxRate(String.valueOf(billApplyInfo.getTaxRate()));
            this.setBillNumber(billApplyInfo.getBillNumber());
            this.setBillCode(billApplyInfo.getBillCode());
            this.setRemarks(billApplyInfo.getRemarks());
            this.setBillType(billApplyInfo.getBillType());
            if (billApplyInfo.getBuyerCorp() != null) {
                this.setBuyerCorpName(billApplyInfo.getBuyerCorp().getCorpMC());
            }
            if (billApplyInfo.getSaleCorp() != null) {
                this.setSaleCorpName(billApplyInfo.getSaleCorp().getCorpMC());
            }
            if (billApplyInfo.getInvoiceDate() != null)
                this.setInvoiceDate(BeanUtil.timestampToStr(billApplyInfo.getInvoiceDate(), "yyyy-MM-dd HH:mm"));

        }

    }


    public List<BillConfirmInfoVo> listBillConfirmInfoVo(List<BillApplyInfo> info) {
        List<BillConfirmInfoVo> dto = new ArrayList<BillConfirmInfoVo>();
        if (info.size() > 0) {
            for (BillApplyInfo mast : info) {
                dto.add(new BillConfirmInfoVo(mast));
            }
        }
        return dto;
    }


}
