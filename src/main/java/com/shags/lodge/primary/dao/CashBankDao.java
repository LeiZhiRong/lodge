package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.CashBankDto;
import com.shags.lodge.dto.CashBankListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.CashBank;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科目管理持久接口实现类
 * @author 雷智荣
 */
@Repository("cashBankDao")
public class CashBankDao extends LodgeBaseDAO<CashBank,String> implements ICashBankDao {

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from CashBank m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from CashBank m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public boolean deleteCashBank(String id) {
        Object o = super.executeByJpql("delete from CashBank m where m.id =?0", id);
        return o != null;
    }

    @Override
    public CashBank queryCashBank(String id) {
        return (CashBank) super.queryObject("from CashBank m where m.id =?0", id);
    }

    @Override
    public CashBank queryByKmBH(String kmBH) {
        return (CashBank) super.queryObject("from CashBank m where m.kmBH =?0", kmBH);
    }

    @Override
    public int getCountCashBankByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from CashBank m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public Pager<CashBankDto> findCashBankDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        Pager<CashBankDto> list = new Pager<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from CashBank m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.kmBH like:value or m.kmMC like:value  or m.kmJC like:value  or m.kmPYM like:value or m.remarks like:value  ) ");
            alias.put("value", "%" + value + "%");
        }
        Pager<CashBank> mast = super.find(jpql.toString(), alias);
        if (mast != null) {
            list.setTotal(mast.getTotal());
            list.setPageNumber(mast.getPageNumber());
            list.setPageOffset(mast.getPageOffset());
            list.setPageSize(mast.getPageSize());
            list.setRows(new CashBankDto().listCashBankDto(mast.getRows()));
        }
        return list;
    }

    @Override
    public List<CashBankListDto> listCashBankDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        List<CashBankListDto> list = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from CashBank m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.kmBH like:value or m.kmMC like:value  or m.kmJC like:value  or m.kmPYM like:value or m.remarks like:value  ) ");
            alias.put("value", "%" + value + "%");
        }
        List<CashBank> mast = super.listByAlias(jpql.toString(), alias);
        if (mast != null) {
            list=new CashBankListDto().listCashBankListDto(mast);
        }
        return list;
    }

    @Override
    public List<CashBank> listCashBank(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from CashBank m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.kmBH like:value or m.kmMC like:value  or m.kmJC like:value  or m.kmPYM like:value or m.remarks like:value  ) ");
            alias.put("value", "%" + value + "%");
        }
        return super.listByAlias(jpql.toString(),alias);
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update CashBank m set m.orders=?0 where m.id=?1";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public int batchDelete(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> list = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", list);
            return (int) super.executeByAliasJpql("delete from CashBank where id in(:ids)", alias);
        }
        return 0;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<CashBank> list = super.list("from CashBank m  where m.ids like ?0", "%" + id + "%");
        List<CashBank> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (CashBank temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);
    }

    @Override
    public Integer countKmBH(String id, String pid, String kmBH) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" select count(d) from CashBank d where d.kmBH =:kmBH ");
        alias.put("kmBH", kmBH);
        if (pid != null && !pid.isEmpty()) {
            jpql.append(" and d.parent.id =:pid ");
            alias.put("pid", pid);
        } else {
            jpql.append(" and d.parent is null ");
        }
        if (id != null && !id.isEmpty()) {
            jpql.append(" and d.id !=:id ");
            alias.put("id", id);
        }
        Object o = super.queryObjectByAlias(jpql.toString(), alias);
        if (o == null)
            return 0;
        return ((Number) o).intValue();
    }

    @Override
    public List<TreeJson> getCashBank2TreeJson(String keyword) {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.kmBH as kmBH,m.kmMC as text,m.pid as pid,m.contents as contents from cash_bank m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText(map.get("kmBH") + " " + map.get("text"));
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("kmBH"));
                if (list.contains(map.get("kmBH")))
                    temp.setChecked(true);
                temp.setArg1(map.get("contents"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }

}
