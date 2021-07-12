package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 部门信息实体类
 */
@Entity
@Table(name = "dept_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class DeptInfo {
    /**
     * 关键字
     */

    private String id;
    /**
     * 部门编号
     */

    private String deptID;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门简称
     */
    private String deptJc;

    /**
     * 上级部门
     */
    private DeptInfo parent;

    /**
     * 部门关联ids
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 启用状态
     */
    private Integer status = 0;

    /**
     * 是否为目录
     */
    private String contents;


    @Id
    @GeneratedValue(generator = "uuid2" )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public DeptInfo getParent() {
        return parent;
    }

    public void setParent(DeptInfo parent) {
        this.parent = parent;
    }

    @Column(length = 1500)
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DeptInfo() {
        super();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDeptJc() {
        return deptJc;
    }

    public void setDeptJc(String deptJc) {
        this.deptJc = deptJc;
    }
}
