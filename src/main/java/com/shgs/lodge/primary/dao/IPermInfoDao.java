package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.PermInfo;

import java.util.List;

/**
 * 角色权限持久层Dao接口类
 */
public interface IPermInfoDao extends IBaseDAO<PermInfo,String> {
    /**
     * 批量添加角色权限
     *
     * @param permInfoList
     * @return
     */
    boolean batchSavePermInfo(List<PermInfo> permInfoList);

    /**
     * 通过模块ID获取所关联的角色列表
     * @param meun_id
     * @return
     */
    List<PermInfo> listPermInfoByMenuID(String meun_id);

    /**
     * 批量删除角色权限
     *
     * @param role_id
     * @param perm_ids
     * @return
     */
    Integer batchDelePermInfo(String role_id, List<String> perm_ids);

    /**
     * 获取角色已分配权限分组列表
     *
     * @param role_id
     * @param keyword
     * @return
     */
    List<PermInfo> listPermInfo(String role_id, String keyword);

    /**
     * 获取角色已分配权限模块IDS
     *
     * @param role_id
     * @return
     */
    List<String> getMenuIds(String role_id);

    /**
     * 按角色删除权限模块
     * @param role_id
     * @return
     */
    Integer delePermInfoBYRoleID(String role_id);



}
