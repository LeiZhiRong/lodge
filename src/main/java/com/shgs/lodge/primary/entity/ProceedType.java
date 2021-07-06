package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 收支项目
 */
@Entity
@Table(name = "proceed_type")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class ProceedType {

    /**
     * 关键字
     */
    private String id;

    /**
     * 项目编号
     */
    @NotEmpty(message = "费项编号不能为空")
    private String proceedBh;

    /**
     * 项目名称
     */
    @NotEmpty(message = "费项名称不能为空")
    private String name;

    /**
     * 项目类型
     */
    private  CustomParame  parame;

    /**
     * 描述
     */
    private String remarks;

    /**
     * 上级项目
     */
    private ProceedType parent;

    /**
     * 项目关联ids
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 是否为目录
     */
    private String contents;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProceedType() {
        super();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public ProceedType getParent() {
        return parent;
    }

    public void setParent(ProceedType parent) {
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @ManyToOne
    @JoinColumn(name = "parame")
    public CustomParame getParame() {
        return parame;
    }

    public void setParame(CustomParame parame) {
        this.parame = parame;
    }

    public String getProceedBh() {
        return proceedBh;
    }

    public void setProceedBh(String proceedBh) {
        this.proceedBh = proceedBh;
    }

}
