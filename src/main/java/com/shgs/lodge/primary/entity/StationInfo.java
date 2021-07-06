package com.shgs.lodge.primary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.HeaderEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 职务（岗位）信息实体类
 * @author 雷智荣
 */
@Entity
@Table(name = "station_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class StationInfo {

    /**
     * 关键字
     */
    private String id;

    /**
     * 职务名称
     */
    @NotEmpty(message = "岗位名称不能为空")
    @HeaderEnum(field = "stationName", title = "岗位名称", width = 150, sortable = true, hidden = false)
    private String stationName;

    /**
     * 时间戳
     */
    private String rVTime;

    /**
     * 状态标识
     */
    @JsonIgnore
    private String ztbz;

    /**
     * 状态格式化
     */
    @JsonProperty("ztbz")
    @HeaderEnum(field = "ztbz", title = "状态标识", width = 80, sortable = true, hidden = false)
    private String ztFormatter;

    /**
     * 关联操作
     */
    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false, hidden = false)
    private String handle;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }


    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public StationInfo() {
        super();
    }

    @Transient
    public String getHandle() {
        return CmsUtils.formatHandle(this.id);
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Transient
    public String getZtFormatter() {
        return "T".equals(this.ztbz) ? "<i class='fa fa-check fa-fw green '></i>" : "<i class='fa fa-close fa-fw red '></i>";
    }

    public void setZtFormatter(String ztFormatter) {
        this.ztFormatter = ztFormatter;
    }
}
