package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.dto.business.AssetsInfoDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;

import java.util.List;

/**
 * @author yglei
 * @classname IAssetsInfoService
 * @description 楼栋信息业务接口层
 * @date 2021-07-26 17:34
 */
public interface IAssetsInfoService {
    /**
     * 添加楼栋信息
     * @param assetsInfo 楼栋实体类
     * @param assetsTypeId 楼栋类型ID
     * @return
     */
    Message addAssetsInfo(AssetsInfo assetsInfo,String assetsTypeId);

    /**
     * 批量添加
     * @param list 对象集合
     */
    void batchSave(List<AssetsInfo> list);

    /**
     * 更新
     * @param assetsInfo 楼栋实体类
     * @param assetsTypeId 楼栋类型ID
     * @return
     */
    Message updateAssetsInfo(AssetsInfo assetsInfo,String assetsTypeId);

    /**
     * 按ID删除
     * @param id 关键字
     * @return Message
     */
    Message deleteAssetsInfo(String id);

    /**
     * 按ID查询
     * @param id 关键字
     * @return Object
     */
    AssetsInfo queryAssetsInfo(String id);

    /**
     * 获取分页数据
     * @param dto 查询实体类
     * @return
     */
    Pager<AssetsInfoDto> getAssetsInfoDto( AssetsInfoDto dto,boolean isAdmin );

}
