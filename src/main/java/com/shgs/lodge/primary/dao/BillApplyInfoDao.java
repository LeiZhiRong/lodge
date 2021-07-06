package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开票申请持久接口实现类
 *
 * @author 雷智荣
 */

@Repository("billApplyInfoDao")
public class BillApplyInfoDao extends BaseDAO<BillApplyInfo,String> implements IBillApplyInfoDao {

    @Override
    public boolean deleteBillApplyInfo(String id) {
        Object o = super.executeByJpql("delete from BillApplyInfo b where b.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BillApplyInfo queryBillApplyInfoById(String id) {
        return (BillApplyInfo) super.queryObject("from BillApplyInfo b where b.id =?0", id);
    }

    @Override
    public Pager<BillApplyInfo> listBillApplyInfo(String field, String value, String starDate, String endDate, List<String> ztbz, String bookSet, String userBh) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        String values = "%" + value + "%";
        jpql.append(" from BillApplyInfo b where b.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (userBh != null && !userBh.isEmpty()) {
            jpql.append(" and b.applyUserBH =:userBh ");
            alias.put("userBh", userBh);
        }
        if (null != field && !"".equals(field) && !"all".equals(field)) {
            if ("billMoney".equals(field)) {
                jpql.append(" and " + field + " =:value ");
                alias.put("value", new Double(value));

            } else if (value != null && !value.isEmpty()) {
                jpql.append(" and " + field + " like:value");
                alias.put("value", values);
            }
        }
        if ("all".equals(field) && value != null && !value.isEmpty()) {
            jpql.append(" and ( b.buyerCorp.corpBM like:value ");
            jpql.append(" or b.buyerCorp.corpMC like:value  ");
            jpql.append(" or b.applyBH like:value ");
            jpql.append(" or b.applyDeptBH like:value ");
            jpql.append(" or b.remarks like:value  ) ");
            alias.put("value", values);
        }
        if (ztbz != null && ztbz.size() > 0) {
            jpql.append(" and b.ztbz in(:ztbz) ");
            alias.put("ztbz", ztbz);
        }
        if (starDate != null && !starDate.isEmpty()) {
            jpql.append(" and b.applyDate >=:starDate ");
            alias.put("starDate", BeanUtil.strToTimestampTime(starDate));
        }

        if (endDate != null && !endDate.isEmpty()) {
            jpql.append(" and b.applyDate <=:endDate ");
            alias.put("endDate", BeanUtil.strToTimestampTime(endDate));
        }

        return super.find(jpql.toString(), alias);

    }

    @Override
    public Pager<BillApplyInfo> listBillApplyInfo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        String values = "%" + value + "%";
        jpql.append(" from BillApplyInfo b where b.bookSet =:bookSet ");//账套必须
        alias.put("bookSet", bookSet);

        if (null != field && !"".equals(field) && !"all".equals(field)) {//按字段查询处理
            if ("billMoney".equals(field)) {
                jpql.append(" and " + field + " =:value ");
                alias.put("value", new Double(value));

            } else if (value != null && !value.isEmpty()) {
                jpql.append(" and " + field + " like:value");
                alias.put("value", values);
            }
        }
        if ("all".equals(field) && value != null && !value.isEmpty()) {//快速查询处理
            jpql.append(" and ( b.buyerCorp.corpBM like:value ");
            jpql.append(" or b.buyerCorp.corpMC like:value  ");
            jpql.append(" or b.applyBH like:value ");
            jpql.append(" or b.applyDeptBH like:value ");
            jpql.append(" or b.remarks like:value  ) ");
            alias.put("value", values);
        }
        if (ztbz != null && !ztbz.isEmpty()) {//按状态标识查询
            List<String> array = CmsUtils.string2Array(ztbz, ",");
            jpql.append(" and b.ztbz in(:ztbz) ");
            alias.put("ztbz", ztbz);
        }

        if (starDate != null && !starDate.isEmpty()) {//按开始时间查询
            jpql.append(" and b.applyDate >=:starDate ");
            alias.put("starDate", BeanUtil.strToTimestampTime(starDate));
        }

        if (endDate != null && !endDate.isEmpty()) { //按结束时间查询
            jpql.append(" and b.applyDate <=:endDate ");
            alias.put("endDate", BeanUtil.strToTimestampTime(endDate));
        }

        if (applyDeptBH != null && !applyDeptBH.isEmpty()) {//按部门编号查询
            List<String> applyDeptBHS = CmsUtils.string2Array(applyDeptBH, ",");
            jpql.append(" and b.applyDeptBH in(:deptBhs) ");
            alias.put("deptBhs", applyDeptBHS);
        }
        if (saleCorpID != null && !saleCorpID.isEmpty()) {//销售方ID查询处理
            jpql.append(" and b.saleCorp.id =:saleCorpID ");
            alias.put("saleCorpID", saleCorpID);
        }
        if (buyerCorpID != null && !buyerCorpID.isEmpty()) {//购买方ID查询处理
            jpql.append(" and b.buyerCorp.id =:buyerCorpID ");
            alias.put("buyerCorpID", buyerCorpID);
        }

        return super.find(jpql.toString(), alias);
    }

    @Override
    public List<BillApplyInfo> listToBillApplyInfo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        String values = "%" + value + "%";
        jpql.append(" from BillApplyInfo b where b.bookSet =:bookSet ");//账套必须
        alias.put("bookSet", bookSet);

        if (null != field && !"".equals(field) && !"all".equals(field)) {//按字段查询处理
            if ("billMoney".equals(field)) {
                jpql.append(" and " + field + " =:value ");
                alias.put("value", new Double(value));

            } else if (value != null && !value.isEmpty()) {
                jpql.append(" and " + field + " like:value");
                alias.put("value", values);
            }
        }
        if ("all".equals(field) && value != null && !value.isEmpty()) {//快速查询处理
            jpql.append(" and ( b.buyerCorp.corpBM like:value ");
            jpql.append(" or b.buyerCorp.corpMC like:value  ");
            jpql.append(" or b.applyBH like:value ");
            jpql.append(" or b.applyDeptBH like:value ");
            jpql.append(" or b.remarks like:value  ) ");
            alias.put("value", values);
        }
        if (ztbz != null && !ztbz.isEmpty()) {//按状态标识查询
            List<String> array = CmsUtils.string2Array(ztbz, ",");
            jpql.append(" and b.ztbz in(:ztbz) ");
            alias.put("ztbz", ztbz);
        }

        if (starDate != null && !starDate.isEmpty()) {//按开始时间查询
            jpql.append(" and b.applyDate >=:starDate ");
            alias.put("starDate", BeanUtil.strToTimestampTime(starDate));
        }

        if (endDate != null && !endDate.isEmpty()) { //按结束时间查询
            jpql.append(" and b.applyDate <=:endDate ");
            alias.put("endDate", BeanUtil.strToTimestampTime(endDate));
        }

        if (applyDeptBH != null && !applyDeptBH.isEmpty()) {//按部门编号查询
            List<String> applyDeptBHS = CmsUtils.string2Array(applyDeptBH, ",");
            jpql.append(" and b.applyDeptBH in(:deptBhs) ");
            alias.put("deptBhs", applyDeptBHS);
        }
        if (saleCorpID != null && !saleCorpID.isEmpty()) {//销售方ID查询处理
            jpql.append(" and b.saleCorp.id =:saleCorpID ");
            alias.put("saleCorpID", saleCorpID);
        }
        if (buyerCorpID != null && !buyerCorpID.isEmpty()) {//购买方ID查询处理
            jpql.append(" and b.buyerCorp.id =:buyerCorpID ");
            alias.put("buyerCorpID", buyerCorpID);
        }

        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public int batchDeleteApply(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<String, Object>();
            alias.put("ids", _ids);
            Object o = super.executeByAliasJpql("delete from BillApplyInfo b where b.id in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public boolean batchSaveBillApplyInfo(List<BillApplyInfo> list) {
        return super.batchSave(list);
    }

    @Override
    public boolean batchUpdateBillApplyInfo(List<BillApplyInfo> list) {
        return super.batchUpdate(list);
    }

    @Override
    public Integer getMaxOrders() {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append("select max(m.orders) from BillApplyInfo m where m.applyDate between :stardate  and  :enddate ");
        alias.put("stardate", BeanUtil.strToTimestampTime(BeanUtil.getTodayStart(CmsUtils.getNowDate())));
        alias.put("enddate", BeanUtil.strToTimestampTime(BeanUtil.getTodayEnd(CmsUtils.getNowDate())));
        Object obj = super.queryObjectByAlias(jpql.toString(), alias);
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public List<BillApplyInfo> exportBillApplyInfo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String userBh) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        String values = "%" + value + "%";
        jpql.append(" from BillApplyInfo b where b.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (userBh != null && !userBh.isEmpty()) {
            jpql.append(" and b.applyUserBH =:userBh ");
            alias.put("userBh", userBh);
        }
        if (null != field && !"".equals(field) && !"all".equals(field)) {
            if ("billMoney".equals(field)) {
                jpql.append(" and " + field + " =:value ");
                alias.put("value", new Double(value));

            } else if (value != null && !value.isEmpty()) {
                jpql.append(" and " + field + " like:value");
                alias.put("value", values);
            }
        }
        if ("all".equals(field) && value != null && !value.isEmpty()) {
            jpql.append(" and ( b.buyerCorp.corpBM like:value ");
            jpql.append(" or b.buyerCorp.corpMC like:value  ");
            jpql.append(" or b.applyBH like:value ");
            jpql.append(" or b.applyDeptBH like:value ");
            jpql.append(" or b.remarks like:value  ) ");
            alias.put("value", values);
        }
        if (ztbz != null && !ztbz.isEmpty()) {
            List<String> array = CmsUtils.string2Array(ztbz, ",");
            jpql.append(" and b.ztbz in(:ztbz) ");
            alias.put("ztbz", array);
        }
        if (starDate != null && !starDate.isEmpty()) {
            jpql.append(" and b.applyDate >=:starDate ");
            alias.put("starDate", BeanUtil.strToTimestampTime(starDate));
        }

        if (endDate != null && !endDate.isEmpty()) {
            jpql.append(" and b.applyDate <=:endDate ");
            alias.put("endDate", BeanUtil.strToTimestampTime(endDate));
        }
        jpql.append(" order by b.applyBH asc ");
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public int batchExamine(String ids, String ztbz) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<String, Object>();
            alias.put("ids", _ids);
            alias.put("ztbz", ztbz);
            Object o = super.executeByAliasJpql("update BillApplyInfo b set b.ztbz =:ztbz  where b.id in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }
}
