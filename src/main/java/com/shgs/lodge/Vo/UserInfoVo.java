package com.shgs.lodge.Vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用户信息导入模板
 */

public class UserInfoVo {

    /**
     * 编号
     */
    @Excel(name = "用户编号", width = 15)
    private String bh;

    /**
     * 用户名称
     */
    @Excel(name = "用户姓名",orderNum = "1",width = 15)
    private String name;

    /**
     * 初始密码
     */
    @Excel(name = "初始密码",orderNum = "2",width = 15)
    private String password;

    /**
     * 职务
     */
    @Excel(name = "岗位",orderNum = "3",width = 10)
    private String station;

    /**
     * 管理部门
     */
    @Excel(name = "管理部门",orderNum = "4",width = 30)
    private String glBh;

    /**
     * 结算部门
     */
    @Excel(name = "管理处",orderNum = "5",width = 30)
    private String jsBh;

    /**
     * 状态标识
     */
    @Excel(name = "状态标识",orderNum = "6",width = 15)
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 检测结果：“T”通过检测，“F”未通过检测
     */
    private String checkStr;

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlBh() {
        return glBh;
    }

    public void setGlBh(String glBh) {
        this.glBh = glBh;
    }

    public String getJsBh() {
        return jsBh;
    }

    public void setJsBh(String jsBh) {
        this.jsBh = jsBh;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UserInfoVo() {
        super();
    }

    public String getCheckStr() {
        return checkStr;
    }

    public void setCheckStr(String checkStr) {
        this.checkStr = checkStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
