package com.shags.lodge.service;


import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.util.Message;
import com.shags.lodge.primary.entity.TableHeader;

import java.util.List;

public interface ITableHeaderService {

  /**
   * 更新排序号
   * @param ids
   */
    void updateSort(String ids);

  /**
   * 获取列表
   * @param user_id
   * @param table_name
   * @return
   */
    List<TableHeader> listTableHeader(String user_id, String table_name);

  /**
   * 批量添加
   * @param tableHeaders
   * @return
   */
    Message batchSaveTableHeader(List<TableHeader> tableHeaders);

  /**
   * 批量更新
   * @param tableHeaders
   * @return
   */
    Message batchUpdateTableHeader(List<TableHeader> tableHeaders);

  /**
   * 批量删除
   * @param ids
   * @return
   */
    Message batchDeleteTableHeader(String[] ids);

  /**
   * 按ID查询对象
   * @param id
   * @return
   */
    TableHeader queryTableHeader(String id);

  /**
   * 初始化列表
   * @param user_id
   * @param table_name
   * @param clas
   * @return
   */
  List<HeaderColumns> listHeaderColumns(String user_id, String table_name, String clas);

  /**
   * 重置列表
   * @param user_id
   * @param table_name
   * @return
   */
  Message deleteTableHeader(String user_id, String table_name);

}

