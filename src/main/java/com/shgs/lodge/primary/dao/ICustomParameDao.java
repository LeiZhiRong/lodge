package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.CustomParame;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 自定义参数持久层Dao接口类
 * @author 雷智荣
 */
public interface ICustomParameDao extends IBaseDAO<CustomParame> {
    /**
     * 保存
     * @param customParame
     * @return
     */
    CustomParame savaCustomParame(CustomParame customParame);

    /**
     * 更新
     * @param customParame
     * @return
     */
    boolean updateCustomParame(CustomParame customParame);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteCustomParame(String id);

    /**
     * 批量删除
     * @param type_id 类型id
     * @param ids 参数ids
     * @return
     */
    Integer batchDeleCustomParame(String type_id,List<String> ids);

    /**
     * 根据ID获取实体类
     * @param id
     * @return
     */
    CustomParame queryCustomParame(String id);

    /**
     * 获取参数列表
     * @param type_id
     * @param keyword
     * @return
     */
    List<CustomParame> listCustomParame(String type_id,String keyword);

    /**
     * 获取排序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 按typeCode查询
     * @param typeCode
     * @param keyword
     * @param status
     * @return
     */
    List<SelectJson> listCustomParame(String typeCode,String keyword,boolean status);


    List<SelectJson> listCustomParameByCode(String typeCode);


    CustomParame queryCustomParame(String typeCode,String keyword);



}
