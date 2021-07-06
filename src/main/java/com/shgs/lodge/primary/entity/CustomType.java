package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 自定义类型实体类
 */
@Entity
@Table(name = "custom_type")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class CustomType {
    /**
     * 关键字
     */
    private String id;
    /**
     * 类型名称
     */
    @NotEmpty(message = "名称不能为空")
    private String typeName;

    /**
     * 类型编码
     */
    @NotEmpty(message = "编码不能为空")
    private String typeCode;

    /**
     * 所属类型
     */
    private CustomType parent;

    /**
     * 排序号
     */
    private Integer orders;


    @Id
    @GeneratedValue(generator = "uuid2" )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public CustomType getParent() {
        return parent;
    }

    public void setParent(CustomType parent) {
        this.parent = parent;
    }

    public CustomType() {
        super();
    }
}
