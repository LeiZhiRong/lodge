package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.PermInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("permInfoDao")
public class PermInfoDao extends BaseDAO<PermInfo,String> implements IPermInfoDao {

    @Override
    public boolean batchSavePermInfo(List<PermInfo> permInfoList) {
        return super.batchSave(permInfoList);
    }

    @Override
    public List<PermInfo> listPermInfoByMenuID(String meun_id) {
        return super.list("from PermInfo p where p.menuInfo.id =?0",meun_id);
    }

    @Override
    public Integer batchDelePermInfo(String role_id,List<String> perm_ids) {
        String jpql = "delete from PermInfo p where p.role_id =:role_id and p.id in(:ids)";
        Map<String, Object> alias = new HashMap<>();
        alias.put("ids", perm_ids);
        alias.put("role_id",role_id);
        Object o = super.executeByAliasJpql(jpql, alias);
        if (o == null)
            return 0;
        return (Integer) o;
    }

    @Override
    public List<PermInfo> listPermInfo(String role_id, String keyword) {
        if (role_id == null || role_id.isEmpty())
            return null;
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("from PermInfo p where p.role_id =:role_id ");
        alias.put("role_id", role_id);
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and p.menuInfo.name like:keyword ");
            alias.put("keyword", "%" + keyword + "%");
        }
         return super.listByAlias(jpql.toString(), alias);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public List<String> getMenuIds(String role_id) {
        if (role_id == null || role_id.isEmpty())
            return null;
        List<String> meun_ids = new ArrayList<>();
        String jpql = "select menu_id as menu_id from perm_info where role_id =?0 ";
        Map<String, Object> alias = new HashMap<>();
        alias.put("role_id", role_id);
        List<Map> mapList = super.listToMapBySql(jpql, role_id);
        if (mapList != null && mapList.size() > 0) {
            for (Map map : mapList) {
                meun_ids.add((String) map.get("menu_id"));
            }
        }
        return meun_ids;
    }

    @Override
    public Integer delePermInfoBYRoleID(String role_id) {
        String jpql = "delete from PermInfo p where p.role_id =:role_id";
        Map<String, Object> alias = new HashMap<>();
        alias.put("role_id",role_id);
        Object o = super.executeByAliasJpql(jpql, alias);
        if (o == null)
            return 0;
        return (Integer) o;
    }

}
