package com.shags.lodge.business.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * @author yglei
 * @Title: 资产管理-坐落位置实体类
 * @date 2021-07-2111:23
 */

@Entity
@Table(name = "location_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class LocationInfo {

    /**
     * 关键字
     */
    private String id;
    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 所属管理区
     */
    @NotEmpty(message = "请选择管理区")
    private String managerPoint;

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

    public LocationInfo() {
        super();
    }

}
