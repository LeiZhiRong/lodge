package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.AssetsType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yglei
 * @Title 资产分类业务接口实现类
 * @date 2021-07-1916:23
 */
@Repository("assetsTypeDao")
public class AssetsTypeDao extends BusinessBaseDAO<AssetsType, String> implements IAssetsTypeDao {


    @Override
    public int getMaxOrderByParent(String bookSet,String pid) {
        Object obj;
        if (StringUtils.isNotEmpty(pid)) {
            obj = super.queryObject("select max(m.orders) from AssetsType m where m.parent.id=?0 and m.bookSet =?1 ", new Object[]{pid,bookSet});
        } else {
            obj = super.queryObject("select max(m.orders) from AssetsType m where m.parent is null and m.bookSet =?0",bookSet);
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<AssetsType> list = super.list("from AssetsType m  where m.ids like ?0", "%" + id + "%");
        List<AssetsType> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AssetsType temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);
    }

    @Override
    public int getCountByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from AssetsType m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public boolean checkAssetsType(String bookSet, String bh, String name, String id) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<String> tj = new ArrayList<>();
        jpql.append("from AssetsType a where a.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(id)) {
            jpql.append(" and a.id !=:id ");
            alias.put("id", id);
        }
        if (StringUtils.isNotEmpty(bh)) {
            tj.add(" a.bh =:bh ");
            alias.put("bh", bh);
        }
        if (StringUtils.isNotEmpty(name)) {
            tj.add(" a.name =:name ");
            alias.put("name", name);
        }
        if (tj.size() > 0) {
            jpql.append(" and ( ").append(StringUtils.join(tj, "or")).append(" ) ");
        }
        return super.queryObjectByAlias(jpql.toString(), alias) != null;
    }


}
