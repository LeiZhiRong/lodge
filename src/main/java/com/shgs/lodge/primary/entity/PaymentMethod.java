package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 收款方式
 */
@Entity
@Table(name = "payment_method")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class PaymentMethod {

    /**
     * 关键字
     */
    private String id;

    /**
     * 编号
     */
    private String paymentBh;

    /**
     * 名称
     */
    private String paymentName;

    /**
     * 上级项目
     */
    private PaymentMethod parent;

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

    /**
     * 状态标识
     */
    private String ztbz;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentBh() {
        return paymentBh;
    }

    public void setPaymentBh(String paymentBh) {
        this.paymentBh = paymentBh;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public PaymentMethod getParent() {
        return parent;
    }

    public void setParent(PaymentMethod parent) {
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

    public PaymentMethod() {
        super();
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

}
