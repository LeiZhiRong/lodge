package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 账套信息实体类
 *
 * @author 雷智荣
 */
@Entity
@Table(name = "ancillary_projects")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class AncillaryProjects {

    /**
     * 关键字
     */
    private String id;

    /**
     * 项目编号
     */
    private String projectsBh;

    /**
     * 项目名称
     */
    private String projectsName;


    /**
     * 上级项目
     */
    private AncillaryProjects parent;

    /**
     * 项目关联ids
     */
    private String ids;

    /**
     * 项目类型
     */
    private CustomParame projectType;

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

    @ManyToOne
    @JoinColumn(name = "pid")
    public AncillaryProjects getParent() {
        return parent;
    }

    public void setParent(AncillaryProjects parent) {
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

    public AncillaryProjects() {
        super();
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getProjectsName() {
        return projectsName;
    }

    public void setProjectsName(String projectsName) {
        this.projectsName = projectsName;
    }

    public String getProjectsBh() {
        return projectsBh;
    }

    public void setProjectsBh(String projectsBh) {
        this.projectsBh = projectsBh;
    }

    @ManyToOne
    @JoinColumn(name = "t_id")
    public CustomParame getProjectType() {
        return projectType;
    }

    public void setProjectType(CustomParame projectType) {
        this.projectType = projectType;
    }
}
