package com.shags.lodge.business.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author yglei
 * @classname OperationLog
 * @description 操作日志记录表
 * @date 2022-01-11 15:57
 */
@Entity
@Table(name = "operation_log")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class OperationLog {

    /**
     * 关键字
     */
    private String id;

    /**
     * 业务名称
     */
    private String name;

    /**
     * 操作表名
     */
    private String table_name;

    /**
     * 操作表ID
     */
    private String table_id;

    /**
     * 操作类型
     */
    private String operation_type;

    /**
     * 操作人ID
     */
    private String operation_id;

    /**
     * 操作人
     */
    private String operation_name;

    /**
     * 操作时间
     */
    private Timestamp operation_time;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public Timestamp getOperation_time() {
        return operation_time;
    }

    public void setOperation_time(Timestamp operation_time) {
        this.operation_time = operation_time;
    }

    public String getsVTime() {
        return sVTime;
    }

    public void setsVTime(String sVTime) {
        this.sVTime = sVTime;
    }

    public OperationLog() {
        super();
    }


}
