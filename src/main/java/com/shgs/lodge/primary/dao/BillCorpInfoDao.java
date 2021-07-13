package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.CorpInfoListDto;
import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 团购挂单-公司信息持久接口实现类
 *
 * @author 雷智荣
 */
@Repository("billCorpInfoDao")
public class BillCorpInfoDao extends BaseDAO<BillCorpInfo, String> implements IBillCorpInfoDao {

    @Override
    public boolean deleteBillCorpInfo(String id) {
        Object o = super.executeByJpql("delete from BillCorpInfo b where b.id =?0", id);
        return o != null;
    }

    @Override
    public BillCorpInfo queryBillCorpInfoById(String id) {
        return (BillCorpInfo) super.queryObject("from BillCorpInfo b where b.id =?0", id);
    }

    @Override
    public Pager<BillCorpInfo> listBillCorpInfo(String corpType, String keyword, String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from BillCorpInfo b where 1=1 ");
        if (corpType != null && !corpType.isEmpty()) {
            jpql.append(" and b.corpType =:corpType ");
            alias.put("corpType", corpType);
        }
        if ("T".equals(ztbz)) {
            jpql.append(" and b.status =1 ");
        }
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( b.corpMC like:keyword or b.corpBM like:keyword or b.nsrNum like:keyword or b.dz like:keyword or b.lxdh like:keyword  ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        return super.find(jpql.toString(), alias);
    }

    @Override
    public int batchDeleteCorp(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", _ids);
            Object o = super.executeByAliasJpql("delete from BillCorpInfo b where b.id in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public boolean batchSaveBillCorpInfo(List<BillCorpInfo> list) {
        return super.batchSave(list);
    }

    @Override
    public boolean batchUpdateBillCorpInfo(List<BillCorpInfo> list) {
        return super.batchUpdate(list);
    }

    @Override
    public BillCorpInfo queryBillCorpInfoByCorpBM(String corpBM) {
        return (BillCorpInfo) super.queryObject("from BillCorpInfo b where b.corpBM =?0 or b.corpMC =?1", new Object[]{corpBM, corpBM});
    }

    @Override
    public boolean ChickBillCorpOtherByCorpBM(String corpBm, String id) {
        BillCorpInfo info = (BillCorpInfo) super.queryObject("from BillCorpInfo b where b.corpBM =?0 and b.id !=?1", new Object[]{corpBm, id});
        return info != null;
    }

    @Override
    public List<BillCorpInfo> listBillCorpInfo(String corpType, String keyword) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<BillCorpInfo> list=new ArrayList<>();
        sql.append("select b.id,b.corpType,b.corpMC,b.corpBM,b.nsrNum,b.dz,b.lxdh,b.remark,b.status,c.khyh,c.yhzh,b.userID from bill_corp_info b left join (select corpId,MIN(khyh) as khyh,MIN(yhzh) as yhzh from bill_bank_account t group by t.corpId )c on b.id=c.corpId where 1=1 ");
        if (corpType != null && !corpType.isEmpty()) {
            sql.append(" and b.corpType =:corpType ");
            alias.put("corpType", corpType);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" and ( b.corpMC like:keyword or b.corpBM like:keyword or b.nsrNum like:keyword or b.dz like:keyword or b.lxdh like:keyword  ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        List<Map> temp = super.listToMapByAliasSql(sql.toString(), alias);
        if(temp!=null&& temp.size()>0){
            for(Map map:temp){
                BillCorpInfo mast=new BillCorpInfo();
                mast.setId((String) map.get("id"));
                mast.setCorpType((String) map.get("corpType"));
                mast.setCorpMC((String) map.get("corpMC"));
                mast.setCorpBM((String) map.get("corpBM"));
                mast.setNsrNum((String) map.get("nsrNum"));
                mast.setDz((String) map.get("dz"));
                mast.setLxdh((String) map.get("lxdh"));
                mast.setRemark((String) map.get("remark"));
                mast.setStatus((Integer) map.get("status"));
                mast.setKhyh((String) map.get("khyh"));
                mast.setYhzh((String) map.get("yhzh"));
                mast.setUserID((String) map.get("userID"));
                list.add(mast);
            }
        }
        return list;
    }

    @Override
    public List<CorpInfoListDto> listCorpInfoListDto(String keyword, String pid) {
        StringBuilder jpql = new StringBuilder();
        List<BillCorpInfo> billCorpInfoList;
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from BillCorpInfo b where b.status =1 ");

        if (StringUtils.isNotEmpty(pid) && !"all".equals(pid)) {
            jpql.append(" and b.corpType =:pid ");
            alias.put("pid", pid);
        }

        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( b.corpMC like:keyword or b.corpBM like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        jpql.append(" order by b.corpBM asc ");
        billCorpInfoList = super.listByAlias(jpql.toString(), alias);
        if (billCorpInfoList != null && billCorpInfoList.size() > 0) {
            return new CorpInfoListDto().listCorpInfoListDto(billCorpInfoList, "F");
        } else {
            return new ArrayList<>();
        }
    }
}
