package com.shags.lodge.service.primary;

import com.shags.lodge.dto.ListGroup;
import com.shags.lodge.dto.Permissions;
import com.shags.lodge.util.Message;

import java.util.List;

/**
 * 角色权限管事业务层Service接口类
 * @author  雷智荣
 */
public interface IPermInfoService {
    /**
     * 批量添加角色权限
     * @param role_id
     * @param menu_ids
     * @return
     */
    Message batchSavePermInfo(String role_id, String menu_ids);

    /**
     * 批量删除角色权限
     * @param perm_ids
     * @return
     */
    Message batchDelePermInfo(String role_id,String perm_ids);

    /**
     * 获取角色已分配权限分组列表
     * @param role_id 角色ID
     * @param keyword 过滤关键字
     * @return
     */
    List<ListGroup> listPermInfo2ListGroup(String role_id, String keyword);

    /**
     * 根据授权用户拥有的菜单权限获取授权列表
     * @param menuIds  授权者拥有的菜单权限
     * @param role_id  授权角色ID
     * @param keyword 过滤关键字
     * @return
     */
    List<ListGroup> ListMeunInfo2ListGroup(List<String> menuIds,String role_id,String keyword,boolean isAdmin);

    /**
     * 获取角色权限
     * @param role_id
     * @return
     */
    List<Permissions> listRoleType(String role_id);

    /**
     * 根角色ID获取菜单权限
     * @param role_id
     * @return
     */
    List<String> getMenuIds(String role_id);

    /**
     * 按角色删除菜单权限
     * @param role_id
     * @return
     */
    Integer delePermInfoBYRoleID(String role_id);

}
