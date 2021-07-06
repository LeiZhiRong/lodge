package com.shgs.lodge.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 *
 * @param <T>
 * @author Administrator
 */
public class Pager<T> {

  private List<T> footer = new ArrayList<T>();
  /**
   * 分页的起始页
   */
  private int pageNumber;
  /**
   * 分页起始记录数
   */
  private int pageOffset;
  /**
   * 分页的大小
   */
  private int pageSize;
  /**
   * 分页的数据
   */
  private List<T> rows = new ArrayList<T>();
  /**
   * 总记录数
   */
  private long total;

  public List<T> getFooter() {
    return footer;
  }

  public void setFooter(List<T> footer) {
    this.footer = footer;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageOffset() {
    return pageOffset;
  }

  public void setPageOffset(int pageOffset) {
    this.pageOffset = pageOffset;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public List<T> getRows() {
    return rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }


}

