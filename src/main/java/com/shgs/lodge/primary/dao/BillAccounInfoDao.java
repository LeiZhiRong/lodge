package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.BillAccounInfo;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.CmsUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 团购挂单持久层接口实现类
 *
 * @author 雷智荣
 */
@Repository("billAccounInfoDao")
public class BillAccounInfoDao extends BaseDAO<BillAccounInfo,String> implements IBillAccounInfoDao {

    @Override
    public BillAccounInfo queryBillAccounInfoByID(String id) {

        return (BillAccounInfo) super.queryObject("from BillAccounInfo b where b.id =?0", id);
    }

    @Override
    public boolean deleteBillAccounInfoByID(String id) {
        Object o = super.executeByJpql("delete from BillAccounInfo b where b.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BillAccounInfo queryMaxBillAccounInfo(String saleCorpID, String buyerCorpID, String ztbz) {
        List<BillAccounInfo> list = super.list("select b from BillAccounInfo b where b.saleCorp.id =?0 and b.buyerCorp.id =?1 order by rVTime desc ", new Object[]{saleCorpID, buyerCorpID});
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer getMaxOrders() {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<String, Object>();
        jpql.append("select max(b.orders) from BillAccounInfo b where b.entryTime between :stardate  and  :enddate ");
        alias.put("stardate", BeanUtil.strToTimestampTime(BeanUtil.getTodayStart(CmsUtils.getNowDate())));
        alias.put("enddate", BeanUtil.strToTimestampTime(BeanUtil.getTodayEnd(CmsUtils.getNowDate())));
        Object obj = super.queryObjectByAlias(jpql.toString(), alias);
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

}
