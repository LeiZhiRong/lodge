package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.CustomParame;
import com.shgs.lodge.util.SelectJson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义参数持久层Dao接口实现类
 *
 * @author 雷智荣
 */
@Repository("customParameDao")
public class CustomParameDao extends BaseDAO<CustomParame,String> implements ICustomParameDao {

    @Override
    public CustomParame savaCustomParame(CustomParame customParame) {
        return super.add(customParame);
    }

    @Override
    public boolean updateCustomParame(CustomParame customParame) {
        return super.update(customParame);
    }

    @Override
    public boolean deleteCustomParame(String id) {
        Object o = super.executeByJpql("delete from CustomParame c where c.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer batchDeleCustomParame(String type_id, List<String> ids) {
        if (type_id == null || type_id.isEmpty())
            return 0;
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append("delete from CustomParame c where c.typeId =:type_id ");
        alias.put("type_id", type_id);
        if (ids != null && ids.size() > 0) {
            jpql.append(" and c.id in(:ids) ");
            alias.put("ids", ids);
        }
        Object o = super.executeByAliasJpql(jpql.toString(), alias);
        if (o == null)
            return 0;
        return (Integer) o;
    }

    @Override
    public CustomParame queryCustomParame(String id) {
        return (CustomParame) super.queryObject("from CustomParame c where c.id =?0", id);
    }

    @Override
    public List<CustomParame> listCustomParame(String type_id, String keyword) {
        List<CustomParame> mast = new ArrayList<CustomParame>();
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append("from CustomParame c where 1=1 ");
        if (type_id != null && !type_id.isEmpty()) {
            jpql.append(" and c.typeId =:typeId ");
            alias.put("typeId", type_id);
        }
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( c.parameName like:keyword or c.parameCode like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        jpql.append(" order by c.orders asc ");
        List<CustomParame> temp = super.listByAlias(jpql.toString(), alias);
        if (temp != null && temp.size() > 0) {
            mast = temp;
        }
        return mast;
    }

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj = super.queryObject("select max(c.orders) from CustomParame c where c.typeId=?0", pid);
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update CustomParame c set c.orders =?0 where c.id =?1 ";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public List<SelectJson> listCustomParame(String typeCode, String keyword, boolean status) {
        List<SelectJson> selectJsons = new ArrayList<SelectJson>();
        StringBuffer sql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        sql.append(" select a.id as id, a.parameCode as code, a.parameName as name  from custom_parame a left join custom_type b on a.typeId =b.id where b.typeCode =:typeCode ");
        alias.put("typeCode", typeCode);
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" and ( a.parameName =:keyword or a.parameCode =:keyword )");
            alias.put("keyword",keyword);
        }
        if(status){
            sql.append(" and a.status =1 ");
        }
        sql.append(" order by a.orders asc ");
        List<Map> list = super.listToMapByAliasSql(sql.toString(), alias);
        if (list != null && list.size() > 0) {
            for (Map map : list) {
                String id = (String) map.get("id");
                selectJsons.add(new SelectJson((String) map.get("id"), (String) map.get("code")+":"+ (String) map.get("name"), (String) map.get("name")));
            }
        }
        return selectJsons;
    }

    @Override
    public List<SelectJson> listCustomParameByCode(String typeCode) {
        List<SelectJson> selectJsons = new ArrayList<SelectJson>();
        StringBuffer sql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        sql.append(" select a.id as id, a.parameCode as code, a.parameName as name  from custom_parame a left join custom_type b on a.typeId =b.id where b.typeCode =:typeCode ");
        alias.put("typeCode", typeCode);
        sql.append(" order by a.orders asc ");
        List<Map> list = super.listToMapByAliasSql(sql.toString(), alias);
        if (list != null && list.size() > 0) {
            for (Map map : list) {
                String id = (String) map.get("id");
                selectJsons.add(new SelectJson((String) map.get("id"), (String) map.get("name"), (String) map.get("code")));
            }
        }
        return selectJsons;
    }

    @Override
    public CustomParame queryCustomParame(String typeCode, String keyword) {

        StringBuffer sql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        sql.append(" select a from CustomParame  a left join CustomType b on a.typeId =b.id where b.typeCode =:typeCode and ( a.parameName =:keyword or a.parameCode =:keyword ) ");
        alias.put("typeCode", typeCode);
        alias.put("keyword",keyword);
        return (CustomParame) super.queryObjectByAlias(sql.toString(),alias);
    }
}
