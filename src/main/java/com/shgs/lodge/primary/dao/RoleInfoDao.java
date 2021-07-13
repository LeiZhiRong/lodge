package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.RoleInfo;
import com.shgs.lodge.util.SelectJson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色信息管理持久层Dao接口实现类
 *
 * @author 雷智荣
 */
@Repository("roleInfoDao")
public class RoleInfoDao extends BaseDAO<RoleInfo,String> implements IRoleInfoDao {

    @Override
    public RoleInfo addRoleInfo(RoleInfo roleInfo) {
        return super.add(roleInfo);
    }

    @Override
    public boolean updateRoleInfo(RoleInfo roleInfo) {
        return super.update(roleInfo);
    }

    @Override
    public boolean delRoleINfo(String id) {
        Object o = super.executeByJpql("delete from RoleInfo r where r.id =?0", id);
        return o != null;
    }

    @Override
    public List<RoleInfo> listRoleInfo(String keyword) {
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("from RoleInfo r where 1=1 ");
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and r.roleName like:keyword ");
            alias.put("keyword", "%" + keyword + "%");
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public List<RoleInfo> listRoleInfo(String keyword, String bookSet) {
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("from RoleInfo r where r.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and r.roleName like:keyword ");
            alias.put("keyword", "%" + keyword + "%");
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public RoleInfo getRoleInfo(String id) {
        return (RoleInfo) super.queryObject("from RoleInfo r where r.id =?0", id);
    }

    @Override
    public RoleInfo queryByRoleName(String roleName) {
        return (RoleInfo) super.queryObject("from RoleInfo r where r.roleName =?0", roleName);
    }

    @Override
    public List<RoleInfo> listRoleInfo(List<String> ids) {
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("from RoleInfo r where 1=1 ");
        if (ids != null && ids.size() > 0) {
            jpql.append(" and r.id in(:ids) ");
            alias.put("ids", ids);
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public List<SelectJson> listRoleInfo(List<String> roleIds, String bookSet, String keyword) {
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        List<SelectJson> selectJsonList = new ArrayList<>();
        jpql.append("from RoleInfo r where 1=1 ");
        if (roleIds != null && roleIds.size() > 0) {
            jpql.append(" and r.id not in(:ids) ");
            alias.put("ids", roleIds);
        }
        if (bookSet != null && !bookSet.isEmpty()) {
            jpql.append(" and r.bookSet =:bookSet ");
            alias.put("bookSet", bookSet);
        }
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and r.roleName like:keyword ");
            alias.put("keyword", "%" + keyword + "%");
        }
        List<RoleInfo> roleInfoList = super.listByAlias(jpql.toString(), alias);
        if (roleInfoList != null && roleInfoList.size() > 0) {
            for (RoleInfo roleInfo : roleInfoList) {
                selectJsonList.add(new SelectJson(roleInfo.getId(), roleInfo.getRoleName()));
            }
        }
        return selectJsonList;
    }
}
