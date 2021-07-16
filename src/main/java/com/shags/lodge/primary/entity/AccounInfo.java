package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 账套信息实体类
 *
 * @author 雷智荣
 */
@Entity
@Table(name = "accoun_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class AccounInfo {
    /**
     * 关键字
     */
    private String id;
    /**
     * 账套编号(唯一)
     */
    private String bookSet;
    /**
     * 账套名称
     */
    private String accounName;
    /**
     * 注册时间
     */
    private Timestamp regTime;

    /**
     * 系统图标
     */
    private String icons;
    /**
     * 描述
     */
    private String remarks;

    /**
     * 时间戳
     */
    private String rVTime;

    /**
     * 客户名称
     */
    private String corpName;

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

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getAccounName() {
        return accounName;
    }

    public void setAccounName(String accounName) {
        this.accounName = accounName;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }

    public AccounInfo() {
        super();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }
}
