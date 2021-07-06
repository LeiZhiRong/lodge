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

@Entity
@Table(name = "user_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class UserInfo {
    /**
     * 关键字
     */
    private String id;

    /**
     * 用户姓名
     */
    @HeaderEnum(field = "userName", title = "用户名称", width = 150, sortable = true, hidden = false)
    @NotEmpty(message = "用户名称不能为空")
    private String userName;

    /**
     * 登录编号
     */
    @HeaderEnum(field = "loginAccount", title = "用户编号", width = 150, sortable = true, hidden = false)
    @NotEmpty(message = "用户编号不能为空")
    private String loginAccount;

    /**
     * 登录密码
     */
    @JsonIgnore
    private String loginPassword;

    /**
     * 管理部门
     */
    @HeaderEnum(field = "deptID", title = "管理部门", width = 200, sortable = true, hidden = true)
    @NotEmpty(message = "请选择管理部门")
    private String deptID;

    /**
     * 结算部门
     */
    @HeaderEnum(field = "settID", title = "结算部门", width = 200, sortable = true, hidden = true)
    @NotEmpty(message = "请选择结算部门")
    private String settID;

    /**
     * 职务
     */
    @HeaderEnum(field = "station", title = "岗位", width = 120, sortable = true, hidden = false)
    private String station;

    /**
     * 注册时间
     */
    @HeaderEnum(field = "registTime", title = "注册时间", width = 150, sortable = true, hidden = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp registTime;

    /**
     * 启用状态
     */
    @JsonIgnore
    private Integer status = 0;

    /**
     * 系统管理员标识("T"=是，“F”=否)
     */
    @JsonIgnore
    private String manager = "F";

    /**
     * 最后登录时间
     */
    @HeaderEnum(field = "loginTime", title = "最后登录时间", width = 150, sortable = true, hidden = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp loginTime;

    /**
     * 登录IP地址
     */
    @HeaderEnum(field = "loginIP", title = "最后登录IP", width = 150, sortable = true, hidden = false)
    private String loginIP;


    @JsonProperty("status")
    @HeaderEnum(field = "status", title = "状态", width = 80, sortable = true, hidden = false)
    private String formatter;

    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false, hidden = false)
    private String handle;


    public UserInfo() {
        super();
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Lob
    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    @Lob
    public String getSettID() {
        return settID;
    }

    public void setSettID(String settID) {
        this.settID = settID;
    }

    public Timestamp getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Timestamp registTime) {
        this.registTime = registTime;
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

    public UserInfo(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
