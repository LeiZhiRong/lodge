package com.shags.lodge.business.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yglei
 * @classname OperationLog
 * @description 操作日志详细表
 * @date 2022-01-11 15:57
 */
@Entity
@Table(name = "operation_log_detail")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class OperationLogDetail {

    /**
     * 关键字
     */
    private String id;

    /**
     * 操作日志ID
     */
    private String operation_log_id;

    /**
     * 字段名
     */
    private String clm_name;

    /**
     * 字段名描述
     */
    private String clm_comment;

    /**
     * 旧值
     */
    private String old_string;

    /**
     * 新值
     */
    private String new_string;

    /**
     * 时间戳
     */
    private String sVTime;


    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation_log_id() {
        return operation_log_id;
    }

    public void setOperation_log_id(String operation_log_id) {
        this.operation_log_id = operation_log_id;
    }

    public String getClm_name() {
        return clm_name;
    }

    public void setClm_name(String clm_name) {
        this.clm_name = clm_name;
    }

    public String getClm_comment() {
        return clm_comment;
    }

    public void setClm_comment(String clm_comment) {
        this.clm_comment = clm_comment;
    }

    public String getOld_string() {
        return old_string;
    }

    public void setOld_string(String old_string) {
        this.old_string = old_string;
    }

    public String getNew_string() {
        return new_string;
    }

    public void setNew_string(String new_string) {
        this.new_string = new_string;
    }

    public String getsVTime() {
        return sVTime;
    }

    public void setsVTime(String sVTime) {
        this.sVTime = sVTime;
    }

    public OperationLogDetail() {
        super();
    }




}
