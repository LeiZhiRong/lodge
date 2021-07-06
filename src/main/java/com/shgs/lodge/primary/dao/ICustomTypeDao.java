package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.CustomType;

import java.util.List;

/**
 * 自定义参数类型持久层Dao接口类
 * @author 雷智荣
 */
public interface ICustomTypeDao extends IBaseDAO<CustomType,String> {

    /**
     * 保存
     * @param customType
     * @return
     */
    CustomType savaCustomType(CustomType customType);

    /**
     * 更新
     * @param customType
     * @return
     */
    boolean updateCustomType(CustomType customType);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteCusTomType(String id);

    /**
     * 根据ID获取实体类
     * @param id
     * @return
     */
    CustomType queryCustomType(String id);

    /**
     * 获取列表
     * @param keyword 关键字
     * @return
     */
    List<CustomType> listCustomType(String keyword);

    /**
     * 获取目录树
     * @return
     */
    List<TreeJson> findTreeJson();

    /**
     * 获取排序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

}
