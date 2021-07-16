package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.ProceedTypeListDto;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.ProceedType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("proceedTypeDao")
public class ProceedTypeDao extends LodgeBaseDAO<ProceedType,String> implements IProceedTypeDao {

    @Override
    public boolean deleteProceedTypeById(String id) {
        Object o = super.executeByJpql("delete from ProceedType m where m.id =?0", id);
        return o != null;
    }

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from ProceedType m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from ProceedType m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<ProceedType> list = super.list("from ProceedType m  where m.ids like ?0", "%" + id + "%");
        List<ProceedType> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ProceedType temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);

    }

    @Override
    public int getCountProceedTypeByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from ProceedType m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public List<ProceedTypeListDto> listProceedTypeListDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        List<ProceedTypeListDto> list = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from ProceedType m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.name like:value or m.proceedBh like:value  ) ");
            alias.put("value", "%" + value + "%");
        }
        List<ProceedType> mast = super.listByAlias(jpql.toString(), alias);
        if (mast != null) {
            list=new ProceedTypeListDto().listProceedTypeListDto(mast);
        }
        return list;
    }

}
