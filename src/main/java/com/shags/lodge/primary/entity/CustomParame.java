package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * 自定义参数实体类
 */
@Entity
@Table(name = "custom_parame")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class CustomParame {
    /**
     * 关键字
     */
    private String id;
    /**
     * 参数名称
     */
    @NotEmpty(message = "名称不能为空")
    private String parameName;
    /**
     * 参数编码
     */
     private String parameCode;
    /**
     * 参数描述
     */
    private String describe;

    /**
     * 启用状态
     */
    private Integer status = 0;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 所属参数类型ID
     */
    private String typeId;

    @Id
    @GeneratedValue(generator = "uuid2" )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameName() {
        return parameName;
    }

    public void setParameName(String parameName) {
        this.parameName = parameName;
    }

    public String getParameCode() {
        return parameCode;
    }

    public void setParameCode(String parameCode) {
        this.parameCode = parameCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public CustomParame() {
        super();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}
