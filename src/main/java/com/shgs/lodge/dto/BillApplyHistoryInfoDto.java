package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.HeaderEnum;

import java.util.ArrayList;
import java.util.List;

public class BillApplyHistoryInfoDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 单据编号
     */
    @HeaderEnum(field = "applyBH", title = "单据编号", width = 180, sortable = true, hidden = false)
    private String applyBH;

    /**
     * 申请部门
     */
    @HeaderEnum(field = "applyDeptName", title = "部门名称", width = 150, sortable = true, hidden = false)
    private String applyDeptName;

    /**
     * 申请部门
     */
    @HeaderEnum(field = "applyDeptBH", title = "部门编号", width = 100, sortable = true, hidden = true)
    private String applyDeptBH;

    /**
     * 申请人编号
     */
    private String applyUserBH;


    /**
     * 申请人编号
     */
    @HeaderEnum(field = "applyUserName", title = "申请人", width = 80, sortable = true, hidden = true)
    private String applyUserName;

    /**
     * 结算部门编号
     */
    @HeaderEnum(field = "setllDeptBH", title = "结算部门", width = 150, sortable = true, hidden = true)
    private String setllDeptBH;

    /**
     * 申请时间
     */
    @HeaderEnum(field = "applyDate", title = "申请时间", width = 150, sortable = true, hidden = false)
    private String applyDate;

    /**
     * 会计期间
     */
    @HeaderEnum(field = "periodMonth", title = "会计期间", width = 100, sortable = true, hidden = true)
    private String periodMonth;

    /**
     * 商家ID
     */
    private String saleCorpID;

    /**
     * 销售方名称
     */
    @HeaderEnum(field = "saleCorpName", title = "销售方", width = 150, sortable = true, hidden = false)
    private String saleCorpName;


    /**
     * 销售方开户银行
     */
    private String saleKhyh;

    /**
     * 销售方银行账号
     */
    @HeaderEnum(field = "saleYhzh", title = "销售方银行账号", width = 150, sortable = true, hidden = true)
    private String saleYhzh;


    /**
     * 客户ID
     */
    private String buyerCorpID;

    /**
     * 购买方名称
     */
    @HeaderEnum(field = "buyerCorpName", title = "购买方", width = 150, sortable = true, hidden = false)
    private String buyerCorpName;

    /**
     * 购买方开户银行
     */
    @HeaderEnum(field = "buyKhyh", title = "购买方银行账号", width = 150, sortable = true, hidden = true)
    private String buyKhyh;

    /**
     * 购买方银行账号
     */
    private String  buyYhzh;

    /**
     * 收支费项名称
     */
    @HeaderEnum(field = "proceedName", title = "收支费项", width = 150, sortable = true, hidden = true)
    private String  proceedName;

    /**
     * 收支费项编号
     */
    private String  proceedId;


    /**
     * 发票类型
     */
    @HeaderEnum(field = "billType", title = "发票类型", width = 120, sortable = true, hidden = false)
    private String billType;

    /**
     * 发票金额
     */
    @HeaderEnum(field = "billMoney", title = "发票金额", width = 120, sortable = true, hidden = false)
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
     * 结算方式
     */
    private String paymentMethod;

    /**
     * 是否挂账
     */
    private String onAccount;

    /**
     * 申请人
     */
    private String drawerUser;
    /**
     * 发票代码
     */
    private String billCode;

    /**
     * 发票号码
     */
    private String billNumber;

    /**
     * 进项税档案名称
     */
    private String jxsdaName;

    /**
     * 摘要描述
     */
    private String describe;


    /**
     * 单据状态
     */
    @HeaderEnum(field = "ztbz", title = "单据状态", width = 80, sortable = true, hidden = true)
    private String ztbz;

    /**
     * 备注
     */
    @HeaderEnum(field = "remarks", title = "备注", width = 150, sortable = false, hidden = true)
    private String remarks;


    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false, hidden = false)
    private String handle;


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

    public String getApplyDeptBH() {
        return applyDeptBH;
    }

    public void setApplyDeptBH(String applyDeptBH) {
        this.applyDeptBH = applyDeptBH;
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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getSaleCorpID() {
        return saleCorpID;
    }

    public void setSaleCorpID(String saleCorpID) {
        this.saleCorpID = saleCorpID;
    }

    public String getSaleCorpName() {
        return saleCorpName;
    }

    public void setSaleCorpName(String saleCorpName) {
        this.saleCorpName = saleCorpName;
    }

    public String getBuyerCorpID() {
        return buyerCorpID;
    }

    public void setBuyerCorpID(String buyerCorpID) {
        this.buyerCorpID = buyerCorpID;
    }

    public String getBuyerCorpName() {
        return buyerCorpName;
    }

    public void setBuyerCorpName(String buyerCorpName) {
        this.buyerCorpName = buyerCorpName;
    }

    public Double getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(Double billMoney) {
        this.billMoney = billMoney;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public BillApplyHistoryInfoDto() {
        super();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getHandle() {
        String copyRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='copyRow(&quot;" + this.id + "&quot;);' ><i class=\"fa fa-clipboard\"></i>复制</a>";
        return copyRow;
    }

    public void setHandle(String handle) {
        this.handle = handle;
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

    public String getProceedId() {
        return proceedId;
    }

    public void setProceedId(String proceedId) {
        this.proceedId = proceedId;
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

    public String getDrawerUser() {
        return drawerUser;
    }

    public void setDrawerUser(String drawerUser) {
        this.drawerUser = drawerUser;
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

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
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

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public BillApplyHistoryInfoDto(BillApplyInfo billApplyInfo) {
        if (billApplyInfo != null) {
            this.setId(billApplyInfo.getId());
            this.setApplyBH(billApplyInfo.getApplyBH());
            this.setSetllDeptBH(billApplyInfo.getSetllDeptBH());
            this.setApplyDeptBH(billApplyInfo.getApplyDept().getDeptID());
            this.setApplyDeptName(billApplyInfo.getApplyDept().getDeptName());
            this.setApplyUserBH(billApplyInfo.getApplyUserBH());
            this.setApplyUserName(billApplyInfo.getApplyUserName());
            this.setZtbz(billApplyInfo.getZtbz());
            this.setBillMoney(billApplyInfo.getBillMoney());
            this.setRemarks(billApplyInfo.getRemarks());
            this.setPeriodMonth(billApplyInfo.getPeriodMonth());
            this.setOnAccount(billApplyInfo.getOnAccount());
            this.setPaymentMethod(billApplyInfo.getPaymentMethod());
            this.setBuyKhyh(billApplyInfo.getBuyKhyh());
            this.setBuyYhzh(billApplyInfo.getBuyYhzh());
            this.setSaleKhyh(billApplyInfo.getSaleKhyh());
            this.setSaleYhzh(billApplyInfo.getSaleYhzh());
            this.setProceedName(billApplyInfo.getProceedName());
            this.setProceedId(billApplyInfo.getProceedId());
            this.setTaxMoney(billApplyInfo.getTaxMoney());
            this.setTaxRate(billApplyInfo.getTaxRate());
            this.setAmountMoney(billApplyInfo.getAmountMoney());
            this.setDrawerUser(billApplyInfo.getDrawerUser());
            this.setBillCode(billApplyInfo.getBillCode());
            this.setBillNumber(billApplyInfo.getBillNumber());
            this.setJxsdaName(billApplyInfo.getJxsdaName());
            this.setDescribe(billApplyInfo.getDescribe());
            this.setBillType(billApplyInfo.getBillType());
            if (billApplyInfo.getBuyerCorp() != null) {
                this.setBuyerCorpID(billApplyInfo.getBuyerCorp().getId());
                this.setBuyerCorpName(billApplyInfo.getBuyerCorp().getCorpMC());
            }
            if (billApplyInfo.getSaleCorp() != null) {
                this.setSaleCorpID(billApplyInfo.getSaleCorp().getId());
                this.setSaleCorpName(billApplyInfo.getSaleCorp().getCorpMC());
            }

            this.setApplyDate(billApplyInfo.getApplyDate() != null ? BeanUtil.timestampToStr(billApplyInfo.getApplyDate(), "yyyy-MM-dd HH:mm") : "");

        }

    }

    public List<BillApplyHistoryInfoDto> listHistoryInfoDto(List<BillApplyInfo> billApplyInfoList) {
        List<BillApplyHistoryInfoDto> dto = new ArrayList<>();
        if (billApplyInfoList.size() > 0) {
            for (BillApplyInfo mast : billApplyInfoList) {
                dto.add(new BillApplyHistoryInfoDto(mast));
            }
        }
        return dto;
    }

}
