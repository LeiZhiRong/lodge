package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.Role;
import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.UserRole;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userRoleDao")
public class UserRoleDao extends BaseDAO<UserRole,String> implements IUserRoleDao {

    @Override
    public boolean deleteUserRole(String id) {
        Object o = super.executeByJpql("delete from UserRole u where u.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserRole queryUserRoleById(String id) {
        return (UserRole) super.queryObject("from UserRole u where u.id =?0", id);
    }

    @Override
    public Pager<UserRole> listUserRole(String bookSet_code, String role_id, String keyword) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append(" from UserRole u where 1=1 ");
        if (bookSet_code != null && !bookSet_code.isEmpty()) {
            jpql.append(" and u.role.bookSet =:bookSet ");
            alias.put("bookSet", bookSet_code);
        }
        if (role_id != null && !role_id.isEmpty()) {
            jpql.append(" and u.role.id =:role_id ");
            alias.put("role_id", role_id);
        }
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( u.user.userName like:keyword or u.user.loginAccount like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        return super.find(jpql.toString(), alias);
    }

    @Override
    public List<SelectJson> listRoleInfo(String userId, String bookSet_code, String keyword) {
        List<SelectJson> selects = new ArrayList<SelectJson>();
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append(" from UserRole u where 1=1 ");
        if (userId != null && !userId.isEmpty()) {
            jpql.append(" and u.user.id =:userId ");
            alias.put("userId", userId);
        }
        if (bookSet_code != null && !bookSet_code.isEmpty()) {
            jpql.append(" and u.role.bookSet =:bookSet ");
            alias.put("bookSet", bookSet_code);
        }
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and  u.role.roleName like:keyword ");
            alias.put("keyword", "%" + keyword + "%");
        }
        List<UserRole> list = super.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            for (UserRole mast : list) {
                SelectJson selectJson = new SelectJson();
                selectJson.setId(mast.getId());
                selectJson.setName(mast.getRole() != null ? mast.getRole().getRoleName() : null);
                selectJson.setValues(mast.getRole() != null ? mast.getRole().getId() : null);
                selects.add(selectJson);
            }
        }
        return selects;
    }

    @Override
    public List<String> getUserRoleID(String userId, String bookSet_code) {
        List<String> selects = new ArrayList<String>();
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append(" from UserRole u where 1=1 ");
        if (userId != null && !userId.isEmpty()) {
            jpql.append(" and u.user.id =:userId ");
            alias.put("userId", userId);
        }
        if (bookSet_code != null && !bookSet_code.isEmpty()) {
            jpql.append(" and u.role.bookSet =:bookSet ");
            alias.put("bookSet", bookSet_code);
        }
        List<UserRole> list = super.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            for (UserRole mast : list) {
                if (mast.getRole() != null && !selects.contains(mast.getRole().getId()))
                    selects.add(mast.getRole().getId());
            }
        }
        return selects;
    }

    @Override
    public List<Role> getRoleById(String userId, String bookSet_code) {
        List<Role> roles = new ArrayList<Role>();
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append(" from UserRole u where 1=1 ");
        if (userId != null && !userId.isEmpty()) {
            jpql.append(" and u.user.id =:userId ");
            alias.put("userId", userId);
        }
        if (bookSet_code != null && !bookSet_code.isEmpty()) {
            jpql.append(" and u.role.bookSet =:bookSet ");
            alias.put("bookSet", bookSet_code);
        }
        List<UserRole> list = super.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            for (UserRole mast : list) {
                Role role = new Role();
                role.setId(mast.getRole() != null ? mast.getRole().getId() : null);
                role.setRoleName(mast.getRole() != null ? mast.getRole().getRoleName() : null);
                roles.add(role);
            }

        }
        return roles;
    }

    @Override
    public int batchDeleteUserRole(String ids, String user_id) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<String, Object>();
            alias.put("ids", _ids);
            alias.put("userId", user_id);
            Object o = super.executeByAliasJpql("delete from UserRole u where u.user.id =:userId  and u.id in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public int batchDeleteUserRole(List<String> IDS) {
        Map<String, Object> alias = new HashMap<String, Object>();
        if (IDS != null && IDS.size() > 0) {
            alias.put("ids", IDS);
            Object o = super.executeByAliasSql(" delete from user_role  where id in(:ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public boolean batchSaveUserRole(List<UserRole> list) {
        return super.batchSave(list);
    }

    @Override
    public List<String> getUserRoleIDS(List<String> userIDS, List<String> roleIDS) {
        List<String> ids = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        sql.append("select id  from user_role  where 1=1 ");
        if (userIDS != null && userIDS.size() > 0) {
            sql.append(" and userID in(:ids) ");
            alias.put("ids", userIDS);
        }
        if (roleIDS != null && roleIDS.size() > 0) {
            sql.append(" and roleID in(:roleIDS) ");
            alias.put("roleIDS", roleIDS);
        }
        List<Map> o = super.listToMapByAliasSql(sql.toString(), alias);
        if (o != null && o.size() > 0) {
            for (Map map : o) {
                ids.add((String) map.get("id"));
            }
        }
        return ids;
    }

}
