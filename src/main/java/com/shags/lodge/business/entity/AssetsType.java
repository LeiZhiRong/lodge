package com.shags.lodge.business.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 房屋资产分类实体类
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "assets_type")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class AssetsType {

    /**
     * 关键字
     */
    private String id;

    /**
     * 分类编号
     */
     private String bh;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属大类
     */
    private AssetsType parent;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 关联ID
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 状态标志
     */
    private String ztBz;

    /**
     * 帐套编号
     */
    private String bookSet;

    /**
     * 时间戳
     */
    private String rVTime;


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

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public AssetsType getParent() {
        return parent;
    }

    public void setParent(AssetsType parent) {
        this.parent = parent;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Column(length = 1500)
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public AssetsType() {
        super();
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }
}
