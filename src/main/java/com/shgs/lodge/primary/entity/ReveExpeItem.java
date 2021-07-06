package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 会计项目实体类
 *
 * @author 雷智荣
 */
@Entity
@Table(name = "revenue_expenditure")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class ReveExpeItem {

    /**
     * 关键字
     */
    private String id;

    /**
     * 收支类型
     */
    private String reType;

    /**
     * 所属费项
     */
    private ProceedType proceedType;

    /**
     * 收支项目名称
     */
    private String  szxmName;

    /**
     * 营业项目
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
     * 客商
     */
    private BillCorpInfo saleCorp;

    /**
     * 部门
     */
    private DeptInfo deptInfo;

    /**
     * 科目信息
     */
    private CashBank cashBank;

    /**
     * 借贷方向
     */
    private String balance;

    /**
     * 单据状态
     */
    private String auditStatus;

    /**
     * 是否挂账
     */
    private String onAccount;

    /**
     * 结算方式
     */
    private String paymentMethod;

    /**
     * 正负同向
     */
    private String syntropy;

    /**
     * 状态标识
     */
    private String ztbz;

    /**
     * 摘要规则
     */
    private String describe;

    @Id
    @GeneratedValue(generator = "uuid2")
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

    @ManyToOne
    @JoinColumn(name = "proceedType_id")
    public ProceedType getProceedType() {
        return proceedType;
    }

    public void setProceedType(ProceedType proceedType) {
        this.proceedType = proceedType;
    }

    @ManyToOne
    @JoinColumn(name = "cashBank_id")
    public CashBank getCashBank() {
        return cashBank;
    }

    public void setCashBank(CashBank cashBank) {
        this.cashBank = cashBank;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public ReveExpeItem() {
        super();
    }



    @ManyToOne
    @JoinColumn(name = "deptInfo_id")
    public DeptInfo getDeptInfo() {
        return deptInfo;
    }

    public void setDeptInfo(DeptInfo deptInfo) {
        this.deptInfo = deptInfo;
    }

    @Column(length = 1000)
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

    @Column(length = 1000)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @ManyToOne
    @JoinColumn(name = "corp_id")
    public BillCorpInfo getSaleCorp() {
        return saleCorp;
    }

    public void setSaleCorp(BillCorpInfo saleCorp) {
        this.saleCorp = saleCorp;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
