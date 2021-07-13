package com.shgs.lodge.primary.dao;


import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.TableHeader;

import java.util.List;

public interface ITableHeaderDao extends IBaseDAO<TableHeader> {

  /**
   * 获取列表排序号
   *
   * @param user_id
   * @param table_name
   * @return
   */
  int getMaxOrderByParent(String user_id, String table_name);

  void updateSort(List<String> ids);

  List<TableHeader> listTableHeader(String user_id, String table_name);

  boolean deleteTableHeader(String user_id, String table_name);

  boolean batchSaveTableHeader(List<TableHeader> tableHeaders);

  boolean batchUpdateTableHeader(List<TableHeader> tableHeaders);

  int batchDeleteTableHeader(String[] ids);

  TableHeader queryTableHeader(String id);

  boolean addHeaderColumns(String user_id, String table_name, String clas);

}

