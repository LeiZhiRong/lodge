package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.ManagePoint;
import com.shags.lodge.util.CmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("managePointDao")
public class ManagePointDao extends LodgeBaseDAO<ManagePoint, String> implements IManagePointDao {
    @Override
    public List<Map> listNotInManagePoint(String bookSet, String name) {
        if (StringUtils.isEmpty(name))
            return null;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        name = name.replace("，",",");
        List<String> list = CmsUtils.string2Array(name, ",");
        jpql.append("SELECT name FROM ( ");
        int i = list.size() - 1;
        int h = 0;
        for (String str : list) {
            if (i == h) {
                jpql.append(" select '").append(list.get(h)).append("' as name ");
            } else {
                jpql.append(" select '").append(list.get(h)).append("' as name UNION ");
            }
            h++;
        }
        jpql.append(" ) a WHERE name NOT IN ( SELECT name from manage_point WHERE name IN (:ids) and bookSet =:bookSet )");
        alias.put("ids", list);
        alias.put("bookSet", bookSet);
        return super.listToMapByAliasSql(jpql.toString(), alias);
    }
}
