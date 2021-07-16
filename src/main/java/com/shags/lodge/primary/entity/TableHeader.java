package com.shags.lodge.primary.entity;

import com.shags.lodge.dto.HeaderColumns;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "table_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class TableHeader {

  /**
   * id
   */
  private String id;
  /**
   * 用户ID
   */
  private String user_id;
  /**
   * 表名
   */
  private String table_name;
  /**
   * 字段名
   */
  private String field;
  /**
   * 标题
   */
  private String title;
  /**
   * 显示名称
   */
  private String name;
  /**
   * 宽度
   */
  private Integer width;
  /**
   * 排序
   */
  private Integer sortable;
  /**
   * 隐藏
   */
  private Integer hidden;
  /**
   * 排序号
   */
  private Integer orders;

  private Integer status = 1;


  @Id
  @GeneratedValue(generator = "uuid2" )
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getTable_name() {
    return table_name;
  }

  public void setTable_name(String table_name) {
    this.table_name = table_name;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getSortable() {
    return sortable;
  }

  public void setSortable(Integer sortable) {
    this.sortable = sortable;
  }

  public Integer getHidden() {
    return hidden;
  }

  public void setHidden(Integer hidden) {
    this.hidden = hidden;
  }

  public Integer getOrders() {
    return orders;
  }

  public void setOrders(Integer orders) {
    this.orders = orders;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public TableHeader() {
    super();
  }

  public TableHeader(HeaderColumns columns) {
    this.setField(columns.getField());
    this.setWidth(columns.getWidth());
    this.setTitle(columns.getTitle());
    this.setHidden(columns.isHidden() ? 1 : 0);
    this.setSortable(columns.isSortable() ? 1 : 0);
    this.setName(columns.getTitle());
  }

}

