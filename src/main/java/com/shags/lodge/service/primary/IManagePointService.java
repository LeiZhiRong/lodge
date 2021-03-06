package com.shags.lodge.service.primary;

import com.shags.lodge.dto.ManagePointDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.User;
import com.shags.lodge.dto.business.ManagePointListDto;
import com.shags.lodge.primary.entity.ManagePoint;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;

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
     * 获取列表
     * @param bookSet
     * @return
     */
    List<SelectJson> listManagePointToSelectJson(String bookSet);

    /**
     * 获取登录用户授权管理处列表
     * @param user 登录用户信息
     * @return selectJson
     */
    List<SelectJson> getClientManagePointByUser(User user);

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


    /**
     * 检测管理处是否存在
     * @param bookSet 账套
     * @param name 管理处名称
     * @return
     */
    String listNotInManagePoint(String bookSet,String name);


    /**
     * 获取用户授权管理处列表
     * @param bookSet 账套
     * @param keyword 关键字
     * @param ztbz 状态标识
     * @param userBalanceDept 授权列表
     * @return
     */
    List<ManagePointListDto> listManagePointListDto(String bookSet,String keyword, String ztbz,String userBalanceDept);


    List<TreeJson> getClientManagePointToTreeJson(String bookSet,String keyword,  String ztbz,String userManageDept);




}
