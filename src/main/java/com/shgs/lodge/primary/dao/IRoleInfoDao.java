package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.entity.RoleInfo;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 角色信息管理持久层Dao接口类
 *
 * @author 雷智荣
 */
public interface IRoleInfoDao {
    /**
     * 保存角色
     *
     * @param roleInfo
     * @return
     */
    RoleInfo addRoleInfo(RoleInfo roleInfo);

    /**
     * 更新角色
     *
     * @param roleInfo
     * @return
     */
    boolean updateRoleInfo(RoleInfo roleInfo);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    boolean delRoleINfo(String id);

    /**
     * 角色列表
     *
     * @return
     */
    List<RoleInfo> listRoleInfo(String keyword);

    /**
     * 获取角色列表
     * @param keyword 查询关键字
     * @param bookSet 账套
     * @return
     */
    List<RoleInfo> listRoleInfo(String keyword,String bookSet);

    /**
     * 根据ID获取角色对象
     *
     * @param id
     * @return
     */
    RoleInfo getRoleInfo(String id);

    /**
     * 按角色名称查询角色信息
     * @param roleName
     * @return
     */
    RoleInfo queryByRoleName(String roleName);

    /**
     * 按角色ID集合查询角色列表
     * @param ids
     * @return
     */
    List<RoleInfo> listRoleInfo(List<String> ids);

    /**
     * 按帐套、关键字获取未授权角色列表
     * @param roleIds 已授权角色IDS
     * @param bookSet 帐套CODE
     * @param keword 关键字过滤
     * @return
     */
    List<SelectJson> listRoleInfo(List<String> roleIds,String bookSet,String keword);
}
