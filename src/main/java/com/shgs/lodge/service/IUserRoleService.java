package com.shgs.lodge.service;

import com.shgs.lodge.dto.Role;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 用户角色关联业务层
 * @author 雷智荣
 */
public interface IUserRoleService {

    /**
     * 保存用户角色关联
     * @param user_id 用户ID
     * @param role_ids 角色IDS，多个以”，“隔开
     * @return
     */
    Message saveUserRole(String user_id,String role_ids);


    /**
     * 删除用户角色信息
     *
     * @param id
     * @return
     */
    Message deleteUserRole(String id);

    /**
     * 根据用户ID、帐套ID查询角色信息
     * @param userId 用户ID
     * @param bookSet_code 帐套ID
     * @return
     */
    List<SelectJson> listRoleInfo(String userId, String bookSet_code,String keyword);


    /**
     * 根据用户ID、帐套CODE查询角色信息
     * @param userId 用户ID
     * @param bookSet_code 帐套CODE
     * @return
     */
    List<Role> getRoleById(String userId, String bookSet_code);


    /**
     * 按ID批量删除
     * @param ids
     * @return
     */
    Message batchDeleteUserRole(String ids,String user_id);


    /**
     * 按用户批量添加角色
     * @param userIds 用户IDs
     * @param roleIds 角色IDS
     * @return
     */
    Message batchSaveUserRole(String userIds,String roleIds);

    @SuppressWarnings("UnusedReturnValue")
    int batchDeleteUserRole(String userIds);

    /**
     * 按用户和帐套CODE查询已授权角色IDS
     * @param userId 用户ID
     * @param bookSet_code 帐套CODE
     * @return
     */
    List<String> getUserRoleID(String userId,String bookSet_code);


}
