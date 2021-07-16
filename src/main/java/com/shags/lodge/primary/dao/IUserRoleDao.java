package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.Role;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.UserRole;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;

import java.util.List;

/**
 * 用户角色关联持久类接口
 * @author 雷智荣
 */
public interface IUserRoleDao extends IBaseDAO<UserRole> {

    /**
     * 按ID删除用户角色关联信息
     * @param id
     * @return
     */
    boolean deleteUserRole(String id);

    /**
     * 按ID查询用户角色关联表
     * @param id
     * @return
     */
    UserRole queryUserRoleById(String id);

    /**
     * 按帐套、角色查询用户分页数据
     * @param bookSet_code 帐套编码
     * @param role_id 角色ID
     * @param keyword 关键字
     * @return
     */
    Pager<UserRole> listUserRole(String bookSet_code, String role_id, String keyword);


    /**
     * 按用户ID、帐套查询用户角色列表
     * @param userId 用户ID
     * @param bookSet_code 帐套ID
     * @param keyword 查询关键字
     * @return
     */
    List<SelectJson> listRoleInfo(String userId, String bookSet_code, String keyword);

    /**
     * 根据帐套和用户查询已授权角色ID
     * @param userId 用户ID
     * @param bookSet_code 帐套code
     * @return
     */
    List<String> getUserRoleID(String userId,String bookSet_code);


    /**
     * 根据用户ID，帐套获取用户角色集合
     * @param userId 用户ID
     * @param bookSet_code 帐套ID
     * @return
     */
    List<Role> getRoleById(String userId, String bookSet_code);

    /**
     * 按ID批量删除
     * @param ids
     * @return
     */
    int batchDeleteUserRole(String ids,String user_id);

    /**
     * 按用户ID解除用户角色关联表
     * @param IDS
     * @return
     */
    int batchDeleteUserRole(List<String> IDS);


    /**
     *
     * 批量添加用户角色关联表
     * @param list
     * @return
     */
    boolean batchSaveUserRole(List<UserRole> list);

    /**
     * 根据ids关联ID集合
     * @param userIDS 用户ID集合
     * @param roleIDS 角色ID集合
     * @return
     */
    List<String> getUserRoleIDS(List<String> userIDS,List<String> roleIDS);




}
