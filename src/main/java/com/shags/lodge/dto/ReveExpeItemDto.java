package com.shags.lodge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.HeaderEnum;
import com.shags.lodge.primary.entity.CashBank;
import com.shags.lodge.primary.entity.ProceedType;
import com.shags.lodge.primary.entity.ReveExpeItem;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class ReveExpeItemDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 借贷方向
     */
    @HeaderEnum(field = "balance", title = "借贷", width = 60)
    private String balance;

    /**
     * 编号名称组合
     */
    @HeaderEnum(field = "cashBankBhAndName", title = "科目", width = 200, sortable = false)
    private String cashBankBhAndName;

    /**
     * 收支类型
     */
    @NotEmpty(message = "收支类型不能为空")
    @HeaderEnum(field = "reType", title = "收支类型", width = 80,  hidden = true)
    private String reType;

    /**
     * 客商名称
     */
    @HeaderEnum(field = "saleCorpName", title = "客商", width = 200)
    private String saleCorpName;


    /**
     * 收支项目名称
     */
    @HeaderEnum(field = "szxmName", title = "收支项目", width = 200)
    private String  szxmName;


    /**
     * 部门名称
     */
    @HeaderEnum(field = "deptName", title = "部门", width = 200)
    private String deptName;

    /**
     * 所属费项名称
     */
    private String proceedTypeName;

    /**
     * 所属费项ID
     */
    private String proceedTypeId;

    /**
     * 客商Id
     */
    private String saleCorpId;
    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 单据状态
     */
    @NotEmpty(message = "单据状态不能为空")
    private String auditStatus;

    /**
     * 是否挂账
     */
    private String onAccount;


    /**
     * 科目编号
     */
    private String cashBankBh;

    /**
     * 结算方式
     */
    @NotEmpty(message = "结算方式不能为空")
    private String paymentMethod;

    /**
     * 科目ID
     */
    @NotEmpty(message = "科目编码不能为空")
    private String cashBankId;

    /**
     * 科目名称
     */
    private String cashBankName;
    /**
     * 正负同向
     */
     private String syntropy;

    /**
     * 状态标识
     */

    @JsonIgnore
    @HeaderEnum(field = "ztbz", title = "状态", width = 60, sortable = false)
    private String ztbz;

    @JsonProperty("ztbz")
    private String formatZtbz;

    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false)
    private String handle;

    /**
     * 营业项目名称
     */
    private String  yinyexmName;

    /**
     * 物料基本分类名称
     */
    private String wljbflName;

    /**
     * 物流费用项目名称
     */
    private String wlfyxmName;

    /**
     * 地区分类码名称
     */
    private String dqflmName;

    /**
     * 进项税档案名称
     */
    private String jxsdaName;

    /**
     * 摘要规则
     */
    private String describe;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReType() {
        return reType;
    }

    public void setReType(String reType) {
        this.reType = reType;
    }

    public String getProceedTypeName() {
        return proceedTypeName;
    }

    public void setProceedTypeName(String proceedTypeName) {
        this.proceedTypeName = proceedTypeName;
    }

    public String getCashBankBh() {
        return cashBankBh;
    }

    public void setCashBankBh(String cashBankBh) {
        this.cashBankBh = cashBankBh;
    }

    public String getCashBankName() {
        return cashBankName;
    }

    public void setCashBankName(String cashBankName) {
        this.cashBankName = cashBankName;
    }

    public String getCashBankBhAndName() {
        return cashBankBhAndName;
    }

    public void setCashBankBhAndName(String cashBankBhAndName) {
        this.cashBankBhAndName = cashBankBhAndName;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getProceedTypeId() {
        return proceedTypeId;
    }

    public void setProceedTypeId(String proceedTypeId) {
        this.proceedTypeId = proceedTypeId;
    }

    public String getCashBankId() {
        return cashBankId;
    }

    public void setCashBankId(String cashBankId) {
        this.cashBankId = cashBankId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public ReveExpeItemDto() {
        super();
    }

    public String getFormatZtbz() {
        return formatZtbz;
    }

    public void setFormatZtbz(String formatZtbz) {
        this.formatZtbz = formatZtbz;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSyntropy() {
        return syntropy;
    }

    public void setSyntropy(String syntropy) {
        this.syntropy = syntropy;
    }

    public String getSzxmName() {
        return szxmName;
    }

    public void setSzxmName(String szxmName) {
        this.szxmName = szxmName;
    }

    public String getYinyexmName() {
        return yinyexmName;
    }

    public void setYinyexmName(String yinyexmName) {
        this.yinyexmName = yinyexmName;
    }

    public String getWljbflName() {
        return wljbflName;
    }

    public void setWljbflName(String wljbflName) {
        this.wljbflName = wljbflName;
    }

    public String getWlfyxmName() {
        return wlfyxmName;
    }

    public void setWlfyxmName(String wlfyxmName) {
        this.wlfyxmName = wlfyxmName;
    }

    public String getDqflmName() {
        return dqflmName;
    }

    public void setDqflmName(String dqflmName) {
        this.dqflmName = dqflmName;
    }

    public String getJxsdaName() {
        return jxsdaName;
    }

    public void setJxsdaName(String jxsdaName) {
        this.jxsdaName = jxsdaName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ReveExpeItemDto(ReveExpeItem reveExpeItem) {
        String ztbz = "T".equals(reveExpeItem.getZtbz()) ? "<i class='fa fa-check fa-fw green '></i>" : "<i class='fa fa-close fa-fw red '></i>";
        this.setId(reveExpeItem.getId());
        this.setZtbz(reveExpeItem.getZtbz());
        this.setFormatZtbz(ztbz);
        this.setSzxmName(reveExpeItem.getSzxmName());
        this.setYinyexmName(reveExpeItem.getYinyexmName());
        this.setWlfyxmName(reveExpeItem.getWlfyxmName());
        this.setWljbflName(reveExpeItem.getWljbflName());
        this.setDqflmName(reveExpeItem.getDqflmName());
        this.setJxsdaName(reveExpeItem.getJxsdaName());
        this.setPaymentMethod(reveExpeItem.getPaymentMethod());
        if("1".equals(reveExpeItem.getBalance())){
            this.setBalance("贷");
        }else if("0".equals(reveExpeItem.getBalance())){
            this.setBalance("借");
        }else{
            this.setBalance("无");
        }
        this.setReType(reveExpeItem.getReType());
        this.setOnAccount(reveExpeItem.getOnAccount());
        String copyRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='copyRow(&quot;" + this.id + "&quot;);' ><i class=\"fa fa-clipboard\"></i>复制</a>&nbsp;&nbsp;";
        this.setHandle(copyRow+ CmsUtils.formatHandle(reveExpeItem.getId()));
        this.setAuditStatus(reveExpeItem.getAuditStatus());
        this.setSyntropy(reveExpeItem.getSyntropy());
        this.setDescribe(reveExpeItem.getDescribe());
        //部门
        if(reveExpeItem.getDeptInfo()!=null){
            this.setDeptId(reveExpeItem.getDeptInfo().getId());
            this.setDeptName(reveExpeItem.getDeptInfo().getDeptID()+"\\"+reveExpeItem.getDeptInfo().getDeptName());
        }
        //客商
        if(reveExpeItem.getSaleCorp()!=null){
            this.setSaleCorpId(reveExpeItem.getSaleCorp().getId());
            this.setSaleCorpName(reveExpeItem.getSaleCorp().getCorpBM()+"\\"+reveExpeItem.getSaleCorp().getCorpMC());
        }
        //科目
        if (reveExpeItem.getCashBank() != null) {
            CashBank cashBank = reveExpeItem.getCashBank();
            this.setCashBankId(cashBank.getId());
            this.setCashBankBh(cashBank.getKmBH());
            this.setCashBankName(cashBank.getKmMC());
            String str;
            if (cashBank.getParent() != null && cashBank.getParent().getParent() == null) {
                str =  cashBank.getKmBH() + "\\" + cashBank.getKmMC();
            } else if (cashBank.getParent() != null) {
                str = cashBank.getKmBH() + "\\" + cashBank.getParent().getKmMC() + "\\" + cashBank.getKmMC();
            } else {
                str = cashBank.getKmBH() + "\\" + cashBank.getKmMC();
            }
            this.setCashBankBhAndName(str);
        }
        //所属费项
        if (reveExpeItem.getProceedType() != null) {
            ProceedType proceedType = reveExpeItem.getProceedType();
            this.setProceedTypeId(proceedType.getId());
            this.setProceedTypeName(proceedType.getName());
        }

    }

    public List<ReveExpeItemDto> listReveExpeItemDto(List<ReveExpeItem> list) {
        List<ReveExpeItemDto> dto = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ReveExpeItem mast : list) {
                dto.add(new ReveExpeItemDto(mast));
            }
        }
        return dto;
    }

    public ReveExpeItem getReveExpeItem(ReveExpeItemDto dto) {
        ReveExpeItem mast = new ReveExpeItem();
        if (dto != null) {
            if (StringUtils.isNotEmpty(dto.getId()))
                mast.setId(dto.getId());
            mast.setZtbz(dto.getZtbz());
            mast.setReType(dto.getReType());
            mast.setAuditStatus(dto.getAuditStatus());
            mast.setOnAccount(dto.getOnAccount());
            mast.setBalance(dto.getBalance());
            mast.setSyntropy(dto.getSyntropy());
            mast.setSzxmName(dto.getSzxmName());
            mast.setYinyexmName(dto.getYinyexmName());
            mast.setWlfyxmName(dto.getWlfyxmName());
            mast.setWljbflName(dto.getWljbflName());
            mast.setDqflmName(dto.getDqflmName());
            mast.setJxsdaName(dto.getJxsdaName());
            mast.setPaymentMethod(dto.getPaymentMethod());
            mast.setDescribe(dto.getDescribe());
        }
        return mast;
    }

}
