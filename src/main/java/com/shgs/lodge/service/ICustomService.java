package com.shgs.lodge.service;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.CustomParame;
import com.shgs.lodge.primary.entity.CustomType;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 自定义参数业务层Service接口类
 * @author 雷智荣
 */
public interface ICustomService {
    /**
     * 根据ID获取自定义类型实体类
     * @param id
     * @return
     */
    CustomType getCustomType(String id);
    /**
     * 保存自定义类型
     * @param customType
     * @return
     */
    Message savaCustomType(CustomType customType,String pid);

    /**
     * 更新自定义类型
     * @param customType
     * @return
     */
    Message updateCustomType(CustomType customType,String pid);

    /**
     * 删除自定义类型
     * @param id
     * @return
     */
    Message deleteCustomType(String id);

    /**
     * 获取自定义类型列表数据
     * @param keyword
     * @return
     */
    List<CustomType> listCustomType(String keyword);

    /**
     * 保存自定义参数
     * @param customParame
     * @return
     */
    Message savaCustomParame(CustomParame customParame);

    /**
     * 更新自定义参数
     * @param customParame
     * @return
     */
    Message updateCustomParame(CustomParame customParame);

    /**
     * 删除自定义参数
     * @param id
     * @return
     */
    Message deleteCustomParame(String id);

    /**
     * 根据类型ID删除自定义参数
     * @param type_id
     * @param param_ids
     * @return
     */
    Message batchDeleCustomParame(String type_id,String param_ids);

    /**
     * 根据ID获取自定义参数实体类
     * @param id
     * @return
     */
    CustomParame queryCustomParame(String id);

    /**
     * 获取自定义参数列表
     * @param type_id
     * @param keyword
     * @return
     */
    List<CustomParame> listCustomParame(String type_id,String keyword);

    /**
     * 更新自定义参数排序
     * @param ids 参数ID，多个参数以“，”隔开
     */
    void updateCusTomParamSort(String[] ids);

    /**
     * 获取参数列表
     * @param typeCode 类型code
     * @param keyword 查询关键字
     * @param status 状态标识
     * @return list
     */
    List<SelectJson> listCustomParame(String typeCode, String keyword, boolean status);

    /**
     * 获取参数列表
     * @param typeCode 参数类型code
     * @return
     */
    List<SelectJson> listCustomParameByCode(String typeCode);

    /**
     * 查询自定义参数实例
     * @param typeCode 参数类型code
     * @param keyword 关键字
     * @return Object
     */
    CustomParame queryCustomParame(String typeCode, String keyword);

    /**
     * 获取参数类型目录树
     * @return list
     */
    List<TreeJson> findTreeJson();

    List<SelectJson> listSelectJson();

}
