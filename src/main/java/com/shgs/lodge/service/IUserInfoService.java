package com.shgs.lodge.service;

import com.shgs.lodge.primary.entity.UserInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 用户信息业务接口类Service
 *
 * @author 雷智荣
 */
public interface IUserInfoService {

    /**
     * 保存用户信息
     *
     * @param userInfo
     * @return
     */
    Message saveUserINfo(UserInfo userInfo);

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @return
     */
    Message updateUserinfo(UserInfo userInfo);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    Message deleteUserInfo(String id);

    /**
     * 获取用户分页信息
     *
     * @param keyword 关键字
     * @return
     */
    Pager<UserInfo> listUserInfo( String keyword,boolean isAdmin);

    /**
     * 按用户ID获取用户信息
     *
     * @param id
     * @return
     */
    UserInfo queryUserInfoById(String id);

    /**
     * 按ID批量删除用户信息
     *
     * @param ids
     * @return
     */
    Message batchDeleteUser(String ids);

    /**
     * 按编号查询用户信息
     *
     * @param loginAccount
     * @return
     */
    UserInfo queryByAccount(String loginAccount);

    /**
     * 批量添加用户信息
     * @param list
     */
    void batchSaveUserInfo(List<UserInfo> list);

    /**
     * 登录验证
     * @param loginAccount 帐号
     * @param password 登录密码
     * @return
     */
    Message loginUser(String loginAccount, String password);

    /**
     * 查询用户信息
     * @param keyword
     * @return
     */
    List<SelectJson> listToSelectJson(String keyword);

    /**
     * 更新岗位
     * @param oldStation
     * @param newStation
     */
    void updateUserStation(String oldStation,String newStation);


}
