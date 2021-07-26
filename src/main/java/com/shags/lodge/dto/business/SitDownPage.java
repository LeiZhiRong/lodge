package com.shags.lodge.dto.business;

/**
 * @author yglei
 * @classname SitDownPage
 * @description TODO
 * @date 2021-07-23 12:38
 */
public class SitDownPage {

    /**
     * 关键字
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属管理区
     */
    private String managerPoint;

    /**
     * 状态标志
     */
    private String ztBz;


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

    public String getManagerPoint() {
        return managerPoint;
    }

    public void setManagerPoint(String managerPoint) {
        this.managerPoint = managerPoint;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public SitDownPage() {
        super();
    }
}
