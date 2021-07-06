package com.shgs.lodge.service;

import com.shgs.lodge.primary.entity.RoleInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 角色信息管理业务层Service接口类
 * @author 雷智荣
 */
public interface IRoleInfoService {
    /**
     * 保存角色
     * @param roleInfo
     * @return
     */
    Message addRoleInfo(RoleInfo roleInfo);

    /**
     * 更新角色
     * @param roleInfo
     * @return
     */
    Message updateRoleInfo(RoleInfo roleInfo);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Message delRoleINfo(String id);

    /**
     * 角色列表
     * @return
     */
    List<RoleInfo> listRoleInfo(String keyword );

    /**
     * 按账号查询角色列表
     * @param keyword
     * @param bookSet
     * @return
     */
    List<RoleInfo> listRoleInfo(String keyword, String bookSet);

    /**
     * 根据ID获取角色对象
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

    List<SelectJson> listRoleInfo(List<String> roleIds,String bookSet, String keyword);

}
