package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.PaymentMethodDto;
import com.shags.lodge.dto.PaymentMethodListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.PaymentMethod;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("paymentMethodDao")
public class PaymentMethodDao extends LodgeBaseDAO<PaymentMethod, String> implements IPaymentMethodDao {

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from PaymentMethod m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from PaymentMethod m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public boolean deletePaymentMethod(String id) {
        Object o = super.executeByJpql("delete from PaymentMethod m where m.id =?0", id);
        return o != null;
    }

    @Override
    public PaymentMethod queryPaymentMethod(String id) {
        return (PaymentMethod) super.queryObject("from PaymentMethod m where m.id =?0", id);
    }

    @Override
    public PaymentMethod queryByPaymentBh(String paymentBh) {
        return (PaymentMethod) super.queryObject("from PaymentMethod m where m.paymentBh =?0 or m.paymentName =?1 ", new Object[]{paymentBh,paymentBh});
    }


    @Override
    public int getCountPaymentMethodByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from PaymentMethod m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public Pager<PaymentMethodDto> findPaymentMethodDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        Pager<PaymentMethodDto> list = new Pager<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from PaymentMethod m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.paymentBh like:value or m.paymentName like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        Pager<PaymentMethod> mast = super.find(jpql.toString(), alias);
        if (mast != null) {
            list.setTotal(mast.getTotal());
            list.setPageNumber(mast.getPageNumber());
            list.setPageOffset(mast.getPageOffset());
            list.setPageSize(mast.getPageSize());
            list.setRows(new PaymentMethodDto().listPaymentMethodDto(mast.getRows()));
        }
        return list;
    }

    @Override
    public List<PaymentMethodListDto> listPaymentMethodListDto(String pid, String value) {
        List<PaymentMethodListDto> list = new ArrayList<>();
        List<PaymentMethod> mast = this.listPaymentMethod(pid, value);
        if (mast != null) {
            list = new PaymentMethodListDto().listPaymentMethodListDto(mast);
        }
        return list;
    }

    @Override
    public List<PaymentMethod> listPaymentMethod(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from PaymentMethod m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.paymentBh like:value or m.paymentName like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        jpql.append(" order by m.orders asc ");
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update PaymentMethod m set m.orders=?0 where m.id=?1";
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
            return (int) super.executeByAliasJpql("delete from PaymentMethod where id in(:ids)", alias);
        }
        return 0;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<PaymentMethod> list = super.list("from PaymentMethod m  where m.ids like ?0", "%" + id + "%");
        List<PaymentMethod> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PaymentMethod temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);

    }

    @Override
    public Integer countProjectsBh(String id, String pid, String paymentBh) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" select count(d) from PaymentMethod d where d.paymentBh =:paymentBh ");
        alias.put("paymentBh", paymentBh);
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
    public List<TreeJson> getPaymentMethod2TreeJson(String keyword) {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.paymentBh as paymentBh,m.paymentName as text,m.pid as pid,m.contents as contents from payment_method m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                String paymentBh = (String) map.get("paymentBh");
                if (StringUtils.isNotEmpty(paymentBh)) {
                    temp.setText(paymentBh + "\\" + map.get("text"));
                } else {
                    temp.setText((String) map.get("text"));
                }
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("paymentBh"));
                if (list.contains(map.get("paymentBh")))
                    temp.setChecked(true);
                temp.setArg1(map.get("contents"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    public List<TreeJson> listPaymentMethod() {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.paymentBh as paymentBh,m.paymentName as text,m.pid as pid,m.contents as contents from payment_method m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("text"));
                temp.setText((String) map.get("text"));
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("paymentBh"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }
}
