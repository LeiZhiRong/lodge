package com.shags.lodge.service;

import com.shags.lodge.business.dao.ILocationInfoDao;
import com.shags.lodge.business.entity.LocationInfo;
import com.shags.lodge.service.business.ILocationInfoService;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yglei
 * @Title:
 * @date 2021-07-2115:18
 */
@Service("locationInfoService")
public class LocationInfoService implements ILocationInfoService {

    private ILocationInfoDao   locationInfoDao;

    @Autowired
    public void setLocationInfoDao(ILocationInfoDao locationInfoDao) {
        this.locationInfoDao = locationInfoDao;
    }

    @Override
    public Message addLocationInfo(LocationInfo locationInfo) {
        Message msg=new Message(0,"添加失败");
        if(locationInfoDao.add(locationInfo)!=null){
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    public Message updateLocationInfo(LocationInfo locationInfo) {
        Message msg=new Message(0,"保存失败");
        if(locationInfoDao.update(locationInfo)){
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public Message deleteLocationInfo(String id) {
        Message msg=new Message(0,"删除失败");
        if(locationInfoDao.delete(id)){
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public Pager<LocationInfo> findLocationInfo(String keyWord) {
        StringBuilder jpql=new StringBuilder();
        Map<String,Object> alias= new HashMap<>();
        jpql.append(" from LocationInfo l where 1=1 ");
        if(StringUtils.isNotEmpty(keyWord)){
            jpql.append(" and l.name like:keyword ");
            alias.put("keyword",keyWord+"%");
        }
        return locationInfoDao.find(jpql.toString(),alias);
    }

    @Override
    public LocationInfo queryLocationInfo(String id) {
        return locationInfoDao.load(id);
    }


}
