package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.UserInfo;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 用户管理持久类接口DAO
 *
 * @author 雷智荣
 */
@SuppressWarnings("UnusedReturnValue")
public interface IUserInfoDao extends IBaseDAO<UserInfo> {

    /**
     * 按用户ID删除实例
     *
     * @param id 用户ID
     * @return
     */
    boolean deleteUserInfo(String id);

    /**
     * 按用户ID查询用户信息
     *
     * @param id 用户ID
     * @return
     */
    UserInfo queryUserInfoById(String id);

    /**
     * 按关键字查询用户分页信息
     *
     * @param keyword 关键字
     * @return
     */
    Pager<UserInfo> listUserInfo(String keyword,boolean isAdmin);

    /**
     *
     * 批量删除用户信息
     * @param ids
     * @return
     */
    int batchDeleteUser(String ids);

    /**
     * 按编号获取用户信息
     * @param loginAccount
     * @return
     */
    UserInfo queryByAccount(String loginAccount);

    /**
     *
     * 批量添加用户信息
     * @param list
     * @return
     */
    boolean batchSaveUserInfo(List<UserInfo> list);

    /**
     * 按用户IDS查询用户集合
     * @param userIDS
     * @return
     */
    List<UserInfo> lisetUserInfo(List<String> userIDS);

    /**
     * 按关键字查询用户信息
     * @param keyword 关键字
     * @return
     */
    List<SelectJson> listToSelectJson(String keyword);


}
