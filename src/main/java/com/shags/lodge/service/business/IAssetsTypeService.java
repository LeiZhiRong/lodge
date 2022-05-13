package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.AssetsType;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.business.AssetsTypeListDto;
import com.shags.lodge.dto.business.AssetsTypePage;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;

import java.util.List;

/**
 * @author yglei
 * @date 2021-07-1916:28
 */
public interface IAssetsTypeService {

    /**
     * 添加对象
     * @param assetsType 实例
     * @param pid 上级ID
     * @return message
     */
    Message addAssetsType(AssetsType assetsType, String pid);

    /**
     * 更新对象
     * @param assetsType 实例
     * @param pid 上级ID
     * @return message
     */
    Message updateAssetsType(AssetsType assetsType,String pid);

    /**
     * 按ID删除对象
     * @param id 关键字
     * @return message
     */
    Message deleteAssetsTypeById(String id);

    /**
     * 按ID查询对象
     * @param id 关键字
     * @return object
     */
    AssetsType queryAssetsTypeById(String id);

    /**
     * 按账套和名称查询
     * @param bookSet 账套ID
     * @param name 名称
     * @return object
     */
    AssetsType queryAssetsTypeByBookSetAndName(String bookSet,String name);

    /**
     * 按账套查询分页数据
     * @param bookSet 账套编号
     * @param pid 上级ID
     * @param value 过滤关键字
     * @return pager
     */
    Pager<AssetsTypePage> findAssetsTypeDto(String bookSet, String pid, String value);



    /**
     * 获取目录树
     * @param bookSet 账套ID
     * @return list
     */
    List<TreeJson> listTreeJson(String bookSet);

    /**
     * 客户端获取房产类型静静
     * @param keyword 已选择类型字符串,多个以";"隔开
     * @param bookSet 账套
     * @param ztBz 状态标识
     * @return list
     */
    List<TreeJson> getClientTreeJson(String keyword,String bookSet,String ztBz);

    /**
     * 获取房产类型列表
     * @param bookSet
     * @param pid
     * @param value
     * @param ztBz
     * @return
     */
    List<AssetsTypeListDto> listAssetsTypeListDto(String bookSet, String pid, String value, String ztBz);

    /**
     * 更新排序
     * @param ids array
     */
    void updateSort(String[] ids);











}
