package com.shgs.lodge.primary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.HeaderEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

/**
 * 团购挂单-公司信息实体类
 */
@Entity
@Table(name = "bill_corp_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class BillCorpInfo {

    /**
     * 关键字
     */
    private String id;

    /**
     * 客户类型
     */
    @NotEmpty(message = "请选择客商类型")
    @HeaderEnum(field = "corpType", title = "客商类型", width = 80, sortable = true, hidden = true)
    private String corpType;

    /**
     * 客户编码
     */
    @NotEmpty(message = "客商编号不能为空")
    @HeaderEnum(field = "corpBM", title = "客商编号", width = 100, sortable = true, hidden = false)
    private String corpBM;

    /**
     * 客户名称
     */
    @NotEmpty(message = "客商名称不能为空")
    @HeaderEnum(field = "corpMC", title = "客商名称", width = 300, sortable = true, hidden = false)
    private String corpMC;

    /**
     * 纳税人识别号
     */
    @HeaderEnum(field = "nsrNum", title = "纳税人识别号", width = 150, sortable = true, hidden = true)
    private String nsrNum;

    /**
     * 联系地址
     */
    @HeaderEnum(field = "dz", title = "联系地址", width = 300, sortable = true, hidden = false)
    private String dz;

    /**
     * 联系电话
     */
    @HeaderEnum(field = "lxdh", title = "联系电话", width = 120, sortable = true, hidden = false)
    private String lxdh;

    /**
     * 备注
     */
    @HeaderEnum(field = "remark", title = "备注", width = 100, sortable = true, hidden = true)
    private String remark;

    /**
     * 用户ID
     */
    @HeaderEnum(field = "userID", title = "创建人", width = 80, sortable = true, hidden = true)
    private String userID;

    /**
     * 状态日期
     */
    @HeaderEnum(field = "ztrq", title = "状态日期", width = 150, sortable = true, hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp ztrq;

    /**
     * 状态标识
     */
    @JsonIgnore
    private Integer status;

    @JsonProperty("status")
    @HeaderEnum(field = "status", title = "状态", width = 50, sortable = true, hidden = false)
    private String formatter;


    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false, hidden = false)
    private String handle;

    /**
     * 开户银行
     */
    private String khyh;

    /**
     * 银行帐号
     */
    private String yhzh;


    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getCorpMC() {
        return corpMC;
    }

    public void setCorpMC(String corpMC) {
        this.corpMC = corpMC;
    }

    public String getCorpBM() {
        return corpBM;
    }

    public void setCorpBM(String corpBM) {
        this.corpBM = corpBM;
    }

    public String getNsrNum() {
        return nsrNum;
    }

    public void setNsrNum(String nsrNum) {
        this.nsrNum = nsrNum;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getZtrq() {
        return ztrq;
    }

    public void setZtrq(Timestamp ztrq) {
        this.ztrq = ztrq;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public BillCorpInfo() {
        super();
    }
    @Transient
    public String getFormatter() {
        return CmsUtils.formatStatus(this.status);
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @Transient
    public String getHandle() {
        return CmsUtils.formatHandle(this.id);
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Transient
    public String getKhyh() {
        return khyh;
    }

    public void setKhyh(String khyh) {
        this.khyh = khyh;
    }

    @Transient
    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public BillCorpInfo(String id, String corpType, String corpBM, String corpMC) {
        this.id = id;
        this.corpType = corpType;
        this.corpBM = corpBM;
        this.corpMC = corpMC;
    }

    public BillCorpInfo(String corpType,  String corpBM, String corpMC, String nsrNum, String dz, String lxdh, String remark, Integer status, String khyh, String yhzh) {
        this.corpType = corpType;
        this.corpBM = corpBM;
        this.corpMC = corpMC;
        this.nsrNum = nsrNum;
        this.dz = dz;
        this.lxdh = lxdh;
        this.remark = remark;
        this.status = status;
        this.khyh = khyh;
        this.yhzh = yhzh;
    }
}
