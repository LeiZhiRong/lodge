package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.HeaderEnum;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 开票确认
 */
public class BillConfirmInfoDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 申请单编号
     */
    @HeaderEnum(field = "applyBH", title = "单据编号", width = 150, sortable = true, hidden = false)
    private String applyBH;

    /**
     * 申请部门编号
     */
    @HeaderEnum(field = "applyDeptBH", title = "申请部门", width = 80, sortable = true, hidden = false)
    private String applyDeptBH;

    /**
     * 申请人编号
     */
    @HeaderEnum(field = "applyUserBH", title = "申请人", width = 80, sortable = true, hidden = true)
    private String applyUserBH;

    /**
     * 结算部门编号
     */
    @HeaderEnum(field = "setllDeptBH", title = "可结算部门", width = 150, sortable = true, hidden = true)
    private String setllDeptBH;

    /**
     * 申请时间
     */
    @HeaderEnum(field = "applyDate", title = "申请时间", width = 150, sortable = true, hidden = false)
    private String applyDate;

    /**
     * 销售方ID
     */
    @NotEmpty(message = "销售方不能为空")
    private String saleCorpID;

    /**
     * 销售方名称
     */
    @HeaderEnum(field = "saleCorpName", title = "销售方", width = 200, sortable = true, hidden = false)
    private String saleCorpName;

    /**
     * 购买方ID
     */
    @NotEmpty(message = "购买方不能为空")
    private String buyerCorpID;

    /**
     * 购买方名称
     */
    @HeaderEnum(field = "buyerCorpName", title = "购买方", width = 200, sortable = true, hidden = false)
    private String buyerCorpName;

    /**
     * 发票类型
     */
    @HeaderEnum(field = "billType", title = "发票类型", width = 120, sortable = true, hidden = false)
    private String billType;

    /**
     * 发票金额
     */
    @HeaderEnum(field = "billMoney", title = "发票金额", width = 120, sortable = true, hidden = false)
    private double billMoney;

    /**
     * 发票代码
     */
    @HeaderEnum(field = "billCode", title = "发票代码", width = 120, sortable = true, hidden = true)
    private String billCode;

    /**
     * 发票号码
     */
    @HeaderEnum(field = "billNumber", title = "发票号码", width = 120, sortable = true, hidden = true)
    private String billNumber;

    /**
     * 税率
     */
    @HeaderEnum(field = "taxRate", title = "税率(%)", width = 80, sortable = true, hidden = true)
    private Double taxRate;

    /**
     * 税额
     */
    @HeaderEnum(field = "taxMoney", title = "税额", width = 100, sortable = true, hidden = true)
    private Double taxMoney;

    /**
     * 不含税金额
     */
    @HeaderEnum(field = "amountMoney", title = "不含税金额", width = 120, sortable = true, hidden = true)
    private Double amountMoney;

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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public double getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(double billMoney) {
        this.billMoney = billMoney;
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

    public String getHandle() {
        String confirmRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='confirmRow(&quot;" + id + "&quot;);' ><i class=\"fa fa-check\"></i>开票确认</a>";
        String rejectRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='rejectRow(&quot;" + id + "&quot;);' ><i class=\"fa fa-share\"></i>驳回申请</a>";
        return confirmRow + "&nbsp;&nbsp;" + rejectRow;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public BillConfirmInfoDto() {
        super();
    }

    public BillConfirmInfoDto(BillApplyInfo billApplyInfo) {
        if(billApplyInfo!=null){
            this.setId(billApplyInfo.getId());
            this.setApplyBH(billApplyInfo.getApplyBH());
            this.setSetllDeptBH(billApplyInfo.getSetllDeptBH());
            this.setApplyDeptBH(billApplyInfo.getApplyDept().getDeptID());
            this.setApplyUserBH(billApplyInfo.getApplyUserBH());
            this.setZtbz(billApplyInfo.getZtbz());
            this.setBillMoney(billApplyInfo.getBillMoney());
            this.setRemarks(billApplyInfo.getRemarks());
            this.setBillType(billApplyInfo.getBillType());
            if(billApplyInfo.getBuyerCorp()!=null){
                this.setBuyerCorpID(billApplyInfo.getBuyerCorp().getId());
                this.setBuyerCorpName(billApplyInfo.getBuyerCorp().getCorpMC());
            }
            if(billApplyInfo.getSaleCorp()!=null){
                this.setSaleCorpID(billApplyInfo.getSaleCorp().getId());
                this.setSaleCorpName(billApplyInfo.getSaleCorp().getCorpMC());
            }
           this.setApplyDate(billApplyInfo.getApplyDate() != null ? BeanUtil.timestampToStr(billApplyInfo.getApplyDate(), "yyyy-MM-dd HH:mm") : "");

        }

    }

    public List<BillConfirmInfoDto> listBillConfirmInfoDto(List<BillApplyInfo> billApplyInfoList) {
        List<BillConfirmInfoDto> dto = new ArrayList<BillConfirmInfoDto>();
        if (billApplyInfoList.size() > 0) {
            for (BillApplyInfo mast : billApplyInfoList) {
                dto.add(new BillConfirmInfoDto(mast));
            }
        }
        return dto;
    }


}
