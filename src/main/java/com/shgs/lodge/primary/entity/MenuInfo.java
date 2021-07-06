package com.shgs.lodge.primary.entity;

import com.shgs.lodge.util.RoleType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 模块信息实体类
 */
@Entity
@Table(name = "menu_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class MenuInfo {

    /**
     * 关键字
     */
    private String id;

    /**
     * 模块名称
     */
    private String name;


    /**
     * 模块图标
     */
    private String icons;

    /**
     * 模块排序
     */
    private Integer orders;

    /**
     * 上级模块
     */
    private MenuInfo parent;

    /**
     * 访问路径
     */
    private String pathUrl;

    /**
     * 是否启用
     */
    private int status = 0;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 模块认证
     */
    private RoleType type;
    /**
     * 关联ID
     */
    private String ids;

    /**
     * 首页显示
     */
    private String hompPage;

    /**
     * 是否下发
     */
    private String modelGrant;

    @Id
    @GeneratedValue(generator = "uuid2" )
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

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public MenuInfo getParent() {
        return parent;
    }

    public void setParent(MenuInfo parent) {
        this.parent = parent;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MenuInfo() {
        super();
    }

    @Column(length = 10)
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Column(length = 1500)
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getHompPage() {
        return hompPage;
    }

    public void setHompPage(String hompPage) {
        this.hompPage = hompPage;
    }

    public String getModelGrant() {
        return modelGrant;
    }

    public void setModelGrant(String modelGrant) {
        this.modelGrant = modelGrant;
    }
}
