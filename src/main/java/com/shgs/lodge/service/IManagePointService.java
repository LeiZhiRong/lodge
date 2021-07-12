package com.shgs.lodge.service;

import com.shgs.lodge.dto.ManagePointDto;
import com.shgs.lodge.primary.entity.ManagePoint;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;

import java.util.List;

/**
 * 管理处业务处理接口类
 * @author 雷智荣
 */
public interface IManagePointService {
    /**
     * 按ID查询
     * @param id
     * @return
     */
    ManagePoint queryManagePointByID(String id);

    /**
     * 添加
     * @param managePoint
     * @return
     */
    Message addManagePoint(ManagePoint managePoint);

    /**
     * 更新
     * @param managePoint
     * @return
     */
    Message updateManagePoint(ManagePoint managePoint);

    /**
     * 按帐套获取
     * @param bookSet
     * @return
     */
    List<ManagePoint> listManagePoint(String bookSet);

    /**
     * 获取分页数据
     * @param bookSet 账套编号
     * @param keyword 过滤关键字
     * @return
     */
    Pager<ManagePointDto> findManagePointDto(String bookSet, String keyword);


    /**
     * 删除管理处
     * @param id
     * @return
     */
    Message deleteManagePointByID(String id);


    /**
     * 查询对象
     * @param bookSet 账套编号
     * @param keyword 关键字
     * @return
     */
    ManagePoint queryManagePoint(String bookSet,String keyword);


}
