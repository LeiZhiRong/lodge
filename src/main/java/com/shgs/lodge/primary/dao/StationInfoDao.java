package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.StationInfo;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位信息持久层接口实现类
 *
 * @author 雷智荣
 */
@Repository("stationInfoDao")
public class StationInfoDao extends BaseDAO<StationInfo,String> implements IStationInfoDao {


    @Override
    public StationInfo queryStationInfoByID(String id) {
        return (StationInfo) super.queryObject("from StationInfo s where s.id =?0",id);
    }

    @Override
    public StationInfo queryStationInfoByStationName(String stationName) {
        return (StationInfo) super.queryObject("from StationInfo s where s.stationName =?0 ",stationName);
    }

    @Override
    public StationInfo queryStationInfoByStationName(String stationName,  String id) {
        return (StationInfo) super.queryObject("from StationInfo s where s.stationName =?0  and s.id !=?1",new Object[]{stationName,id});
    }

    @Override
    public List<SelectJson> listStationInfoToSelectJson(String value,  String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<SelectJson> selectJsonList = new ArrayList<>();
        jpql.append("from StationInfo s where 1=1 ");
        if (StringUtils.isNotBlank(value)) {
            jpql.append(" and s.stationName like:value ");
            alias.put("value", "%" + value + "%");
        }
        if (StringUtils.isNotBlank(ztbz)) {
            jpql.append(" and s.ztbz =:ztbz ");
            alias.put("ztbz", ztbz);
        }
        List<StationInfo> list = super.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            for(StationInfo mast:list){
                selectJsonList.add(new SelectJson(mast.getId(),mast.getStationName(),mast.getStationName()));
            }
        }
        return selectJsonList;
    }

    @Override
    public Pager<StationInfo> findStationInfoDto(String value, String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<SelectJson> selectJsonList = new ArrayList<>();
        jpql.append("from StationInfo s where 1=1 ");
         if (StringUtils.isNotBlank(value)) {
            jpql.append(" and s.stationName like:value ");
            alias.put("value", "%" + value + "%");
        }
        if (StringUtils.isNotBlank(ztbz)) {
            jpql.append(" and s.ztbz =:ztbz ");
            alias.put("ztbz", ztbz);
        }
        return super.find(jpql.toString(), alias);
    }

    @Override
    public boolean deleteStationInfo(String id) {
        Object o = super.executeByJpql("delete from StationInfo a where a.id =?0", id);
        return o != null;
    }

    @Override
    public List<SelectJson> listStationInfoBYIDS(String ids) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<SelectJson> selectJsonList = new ArrayList<>();
        jpql.append("from StationInfo s where s.ztbz =:ztbz  ");
        alias.put("ztbz","T");
        if(StringUtils.isNotEmpty(ids)) {
            jpql.append(" and s.id in(:ids) ");
            alias.put("ids", CmsUtils.string2Array(ids, ","));
        }
        List<StationInfo> list = super.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            for(StationInfo mast:list){
                selectJsonList.add(new SelectJson(mast.getId(),mast.getStationName(),mast.getId()));
            }
        }
        return selectJsonList;
    }

    @Override
    public List<TreeJson> listStationInfoByBookSet(String selectNames) {
        Map<String, Object> alias = new HashMap<>();
        List<TreeJson> cts = new ArrayList<>();
        alias.put("ztbz","T");
        List<StationInfo> dts = super.listByAlias("from StationInfo s where  s.ztbz =:ztbz ", alias);
        if (dts != null && dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(selectNames, ",");
            for(StationInfo mast:dts){
                TreeJson temp = new TreeJson();
                temp.setId(mast.getId());
                temp.setText(mast.getStationName());
                temp.setPid(null);
                if (list.contains(mast.getStationName()))
                    temp.setChecked(true);
                cts.add(temp);
            }
        }
        return cts;
    }

    @Override
    public List<SelectJson> listNodeStationInfo(String station) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<SelectJson> cts = new ArrayList<>();
        jpql.append("from StationInfo s where s.ztbz =:ztbz ");
         alias.put("ztbz","T");
        if(StringUtils.isNotEmpty(station)){
            jpql.append(" and s.stationName in(:stationName) ");
            alias.put("stationName",CmsUtils.string2Array(station,","));
        }
        List<StationInfo> dts = super.listByAlias(jpql.toString(), alias);
        if (dts != null && dts.size() > 0) {
             for(StationInfo mast:dts){
                cts.add(new SelectJson(mast.getStationName(),mast.getStationName(),mast.getId()));
            }
        }
        return cts;
    }
}
