package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.dto.User;
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
     * @return
     */
    Message addAssetsInfo(AssetsInfo assetsInfo);

    /**
     * 批量添加
     * @param list 对象集合
     */
    void batchSave(List<AssetsInfo> list);

    /**
     * 更新
     * @param assetsInfo 楼栋实体类
     * @return
     */
    Message updateAssetsInfo(AssetsInfo assetsInfo);

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
     * @description: 分页查询
     * @param: [codeType, codeValue, corp_Name, dept_ID, managePoint_Name, sitDown_Name, assetsType_Name, user]
     * @author: ygLei
     * @return: {@link Pager< AssetsInfoDto>}
     * @date: 2022-01-18 15:44
     */
    Pager<AssetsInfoDto> listAssetsInfoDto(String codeType, String codeValue, String corp_Name, String dept_ID, String managePoint_Name, String sitDown_Name, String assetsType_Name, User user);

}
