package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.AccounInfoDto;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.AccounInfo;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("accounInfoDao")
public class AccounInfoDao extends LodgeBaseDAO<AccounInfo,String> implements IAccounInfoDao {

    @Override
    public AccounInfo queryAccounInfoByID(String id) {
        return (AccounInfo) super.queryObject("from AccounInfo a where a.id =?0", id);
    }

    @Override
    public AccounInfo queryAccounInfoByBookSet(String bookSet) {
        return (AccounInfo) super.queryObject("from AccounInfo a where a.bookSet =?0", bookSet);
    }

    @Override
    public AccounInfo queryAccounInfoByIDAndBookSet(String id, String bookSet) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AccounInfo a where a.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (id != null && !id.isEmpty()) {
            jpql.append(" and a.id !=:id ");
            alias.put("id", id);
        }
        return (AccounInfo) super.queryObjectByAlias(jpql.toString(), alias);
    }

    @Override
    public List<SelectJson> listAccounInfoToSelectJson(String value, String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<SelectJson> select= new ArrayList<>();
        jpql.append("from AccounInfo a where 1=1 ");
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( a.bookSet like:value or a.accounName like:value or a.remarks like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        if (ztbz != null && !ztbz.isEmpty()) {
             jpql.append(" and a.ztbz =: ztbz");
             alias.put("ztbz",ztbz);
        }
        List<AccounInfo> list=super.listByAlias(jpql.toString(),alias);
        if(list!=null&& list.size()>0){
            for(AccounInfo mast:list){
                select.add(new SelectJson(mast.getId(),mast.getBookSet()+" "+mast.getAccounName(),mast.getBookSet()));
            }
        }
        return select;
    }

    @Override
    public Pager<AccounInfoDto> findAccounInfoDto(String value, String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        Pager<AccounInfoDto> pager= new Pager<>();
        jpql.append("from AccounInfo a where 1=1 ");
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( a.bookSet like:value or a.accounName like:value or a.remarks like:value or a.corpName like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        if (ztbz != null && !ztbz.isEmpty()) {
            jpql.append(" and a.ztbz =: ztbz");
            alias.put("ztbz",ztbz);
        }
        Pager<AccounInfo> accounInfoPager =super.find(jpql.toString(),alias);
        if(accounInfoPager!=null){
            pager.setPageNumber(accounInfoPager.getPageNumber());
            pager.setTotal(accounInfoPager.getTotal());
            pager.setPageOffset(accounInfoPager.getPageOffset());
            pager.setPageSize(accounInfoPager.getPageSize());
            pager.setRows(new AccounInfoDto().listAccounInfoDto(accounInfoPager.getRows()));
        }
        return pager;
    }

    @Override
    public boolean deleteAccounInfo(String id) {
        Object o = super.executeByJpql("delete from AccounInfo a where a.id =?0", id);
        return o != null;
    }
}
