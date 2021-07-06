package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.CustomType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义类型接口Dao实现类
 *
 * @author 雷智荣
 */
@Repository("customTypeDao")
public class CustomTypeDao extends BaseDAO<CustomType, String> implements ICustomTypeDao {

    @Override
    public CustomType savaCustomType(CustomType customType) {
        return super.add(customType);
    }

    @Override
    public boolean updateCustomType(CustomType customType) {
        return super.update(customType);
    }

    @Override
    public boolean deleteCusTomType(String id) {
        Object o = super.executeByJpql("delete from CustomType c where c.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CustomType queryCustomType(String id) {
        return (CustomType) super.queryObject("from CustomType c where c.id =?0", id);
    }

    @Override
    public List<CustomType> listCustomType(String keyword) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append("from CustomType c where 1=1 ");
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( c.typeName like:keyword or c.typeCode like:keyword )");
            alias.put("keyword", "%" + keyword + "%");
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public List<TreeJson> findTreeJson() {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.typeName as text,m.pid as pid from custom_type m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText((String) map.get("text"));
                temp.setPid((String) map.get("pid"));
                cts.add(temp);
            }
        }
        return new TreeJson().getfatherNode(cts);
    }

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj = null;
        if (StringUtils.isNotEmpty(pid)) {
            obj = super.queryObject("select max(c.orders) from CustomType c where c.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(c.orders) from CustomType c where c.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }
}
