package com.shags.lodge.dto;

import com.shags.lodge.util.BeanUtil;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.primary.entity.BillApplyInfo;
import com.shags.lodge.primary.entity.CashBank;
import com.shags.lodge.primary.entity.ReveExpeItem;
import com.shags.lodge.primary.entity.VoucherInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ReveExpeItemListDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 收支类型
     */
    private String reType;

    /**
     * 所属费项ID
     */
    private String proccedID;

    /**
     * 所属费项编号
     */
    private String proccedBh;

    /**
     * 所属费项名称
     */
    private String proccedName;

    /**
     * 摘要规则
     */
    private String proccedDescribe;

    /**
     * 收支项目名称
     */
    private String szxmName;

    /**
     * 营业项目
     */
    private String yinyexmName;

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
     * 商家ID
     */
    private String saleCorpID;

    /**
     * 商家编号
     */
    private String saleCorpBh;

    /**
     * 商家名称
     */
    private String saleCorpName;

    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 部门编号
     */
    private String deptBH;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 科目
     */
    private CashBank cashBank;

    /**
     * 科目余额方向
     */
    private String balanceDirection;

    /**
     * 借贷方向
     */
    private String lendingDirection;

    /**
     * 正负同向
     */
    private String syntropy;


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

    public String getProccedID() {
        return proccedID;
    }

    public void setProccedID(String proccedID) {
        this.proccedID = proccedID;
    }

    public String getProccedBh() {
        return proccedBh;
    }

    public void setProccedBh(String proccedBh) {
        this.proccedBh = proccedBh;
    }

    public String getProccedName() {
        return proccedName;
    }

    public void setProccedName(String proccedName) {
        this.proccedName = proccedName;
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

    public String getSaleCorpID() {
        return saleCorpID;
    }

    public void setSaleCorpID(String saleCorpID) {
        this.saleCorpID = saleCorpID;
    }

    public String getSaleCorpBh() {
        return saleCorpBh;
    }

    public void setSaleCorpBh(String saleCorpBh) {
        this.saleCorpBh = saleCorpBh;
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

    public String getDeptBH() {
        return deptBH;
    }

    public void setDeptBH(String deptBH) {
        this.deptBH = deptBH;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public CashBank getCashBank() {
        return cashBank;
    }

    public void setCashBank(CashBank cashBank) {
        this.cashBank = cashBank;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    public String getLendingDirection() {
        return lendingDirection;
    }

    public void setLendingDirection(String lendingDirection) {
        this.lendingDirection = lendingDirection;
    }

    public String getSyntropy() {
        return syntropy;
    }

    public void setSyntropy(String syntropy) {
        this.syntropy = syntropy;
    }

    public String getProccedDescribe() {
        return proccedDescribe;
    }

    public void setProccedDescribe(String proccedDescribe) {
        this.proccedDescribe = proccedDescribe;
    }


    public ReveExpeItemListDto() {
        super();
    }

    public ReveExpeItemListDto(ReveExpeItem cts) {
        this.setId(cts.getId());
        this.setReType(cts.getReType());
        this.setSzxmName(cts.getSzxmName());
        this.setYinyexmName(cts.getYinyexmName());
        this.setWljbflName(cts.getWljbflName());
        this.setWlfyxmName(cts.getWlfyxmName());
        this.setDqflmName(cts.getDqflmName());
        this.setJxsdaName(cts.getJxsdaName());
        if (cts.getProceedType() != null) {
            this.setProccedID(cts.getProceedType().getId());
            this.setProccedBh(cts.getProceedType().getProceedBh());
            this.setProccedName(cts.getProceedType().getName());
            this.setProccedDescribe(cts.getDescribe());
        }
        if (cts.getSaleCorp() != null) {
            this.setSaleCorpID(cts.getSaleCorp().getId());
            this.setSaleCorpBh(cts.getSaleCorp().getCorpBM());
            this.setSaleCorpName(cts.getSaleCorp().getCorpMC());
        }
        if (cts.getDeptInfo() != null) {
            this.setDeptId(cts.getDeptInfo().getId());
            this.setDeptBH(cts.getDeptInfo().getDeptID());
            this.setDeptName(cts.getDeptInfo().getDeptName());
        }
        //余额方向
        this.setBalanceDirection(cts.getCashBank().getBalance());
        //科目
        if (cts.getCashBank() != null) {

            this.setCashBank(cts.getCashBank());
        }
        this.setLendingDirection(cts.getBalance());
        this.setSyntropy(cts.getSyntropy());
    }

    public List<ReveExpeItemListDto> listReveExpeItemListDto(List<ReveExpeItem> list) {
        List<ReveExpeItemListDto> dto = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ReveExpeItem mast : list) {
                dto.add(new ReveExpeItemListDto(mast));
            }
        }
        return dto;
    }

    public List<VoucherInfo> listVoucherInfo(List<ReveExpeItemListDto> cts, BillApplyInfo apply) {
        List<VoucherInfo> dts = new ArrayList<>();
        for (ReveExpeItemListDto ct : cts) {
            VoucherInfo mast = new VoucherInfo();
            mast.setApplyId(apply.getId());
            mast.setApplyBH(apply.getApplyBH());
            //会计期间
            mast.setPeriodMonth(apply.getPeriodMonth());
            //科目
            mast.setCashBank(ct.getCashBank()!=null?ct.getCashBank():null);
            //借贷方向
            mast.setDirection(ct.getLendingDirection());
            //余额方向
            mast.setBalanceDirection(ct.getBalanceDirection());
            //申请部门
            mast.setApplyDeptId(apply.getApplyDept().getId());
            List<String> str = CmsUtils.string2Array(apply.getApplyDept().getDeptName(), "-");
            if (str.size() > 1) {
                mast.setApplyDeptName(str.get(1));
            } else {
                mast.setApplyDeptName(str.get(0));
            }
            //部门核算
            if (StringUtils.isNotEmpty(ct.getDeptId())) {
                mast.setDeptId(ct.getDeptId());
                mast.setDeptName(ct.getDeptBH() + "\\" + ct.getDeptName());
            } else {
                mast.setDeptId(apply.getApplyDept().getId());
                mast.setDeptName(apply.getApplyDept().getDeptName());
            }
            //客商核算
            if (StringUtils.isNotEmpty(ct.getSaleCorpID())) {
                mast.setCorpId(ct.getSaleCorpID());
                mast.setCorpName(ct.getSaleCorpBh() + "\\" + ct.getSaleCorpName());
            } else {
                mast.setCorpId(apply.getBuyerCorp().getId());
                mast.setCorpName(apply.getBuyerCorp().getCorpMC());

            }
            //销售方
            mast.setSaleCorpId(apply.getSaleCorp().getId());
            mast.setSaleCorpName(apply.getSaleCorp().getCorpMC());
            mast.setSaleKhyh(apply.getSaleKhyh());
            mast.setSaleYhzh(apply.getSaleYhzh());
            //购买方
            mast.setBuyCorpID(apply.getBuyerCorp().getId());
            mast.setBuyCorpName(apply.getBuyerCorp().getCorpMC());
            mast.setBuyKhyh(apply.getBuyKhyh());
            mast.setBuyYhzh(apply.getBuyYhzh());

            //账套
            mast.setBookSet(apply.getBookSet());
            mast.setYinyexm(ct.getYinyexmName());
            mast.setWljbflxm(ct.getWljbflName());
            mast.setWlfyxm(ct.getWlfyxmName());
            mast.setDqflm(ct.getDqflmName());
            if ("T".equals(ct.getSyntropy()) && "支出".equals(ct.getReType())) {
                //含税金额
                mast.setBillMoney(CmsUtils.subtract(0.00,apply.getBillMoney()));
                //税率
                mast.setTaxRate(apply.getTaxRate());
                //税额
                mast.setTaxMoney(CmsUtils.subtract(0.00,apply.getTaxMoney()));
                //不含税金额
                mast.setAmountMoney(CmsUtils.subtract(0.00,apply.getAmountMoney()));
            } else {
                //含税金额
                mast.setBillMoney(apply.getBillMoney());
                //税率
                mast.setTaxRate(apply.getTaxRate());
                //税额
                mast.setTaxMoney(apply.getTaxMoney());
                //不含税金额
                mast.setAmountMoney(apply.getAmountMoney());
            }
            mast.setrVTime(CmsUtils.getTimeMillis());
            mast.setCreationTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
            mast.setProceedId(apply.getProceedId());
            mast.setProceedName(apply.getProceedName());
            mast.setAuditStatus(apply.getZtbz());
            mast.setOnAccount(apply.getOnAccount());
            mast.setPaymentMethod(apply.getPaymentMethod());
            //摘要
            if(StringUtils.isNotEmpty(apply.getDescribe())){
                mast.setDescribe(apply.getDescribe());
            }else {
                String d = ct.getProccedDescribe();
                d = StringUtils.replace(d, "{部门}", mast.getApplyDeptName());
                d = StringUtils.replace(d, "{客商}", mast.getBuyCorpName());
                d = d + "（发票" + mast.getBillMoney() + "）";
                mast.setDescribe(d);
            }
            mast.setSzxm(ct.getSzxmName());
            dts.add(mast);
        }
        return dts;
    }

}
