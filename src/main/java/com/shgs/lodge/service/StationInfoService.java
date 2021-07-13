package com.shgs.lodge.service;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.IStationInfoDao;
import com.shgs.lodge.primary.entity.StationInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职位信息业务接口层实现类
 * @author 雷智荣
 */
@Service("stationInfoService")
public class StationInfoService implements IStationInfoService {

    private IStationInfoDao stationInfoDao;

    @Autowired
    public void setStationInfoDao(IStationInfoDao stationInfoDao) {
        this.stationInfoDao = stationInfoDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addStationInfo(StationInfo stationInfo) {
        Message msg=new Message(0,"添加失败");
        if(stationInfoDao.add(stationInfo)!=null){
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateStationInfo(StationInfo stationInfo) {
        Message msg=new Message(0,"更新失败");
        if(stationInfoDao.update(stationInfo)){
            msg.setCode(1);
            msg.setMessage("更新成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteStationInfo(String id) {
        Message msg=new Message(0,"删除失败");
        if(stationInfoDao.deleteStationInfo(id)){
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public StationInfo queryStationInfoByID(String id) {
        return stationInfoDao.queryStationInfoByID(id);
    }

    @Override
    public StationInfo queryStationInfoByStationName(String stationName) {
        return stationInfoDao.queryStationInfoByStationName(stationName);
    }


    @Override
    public List<SelectJson> listStationInfoToSelectJson(String value, String ztbz) {
        return stationInfoDao.listStationInfoToSelectJson( value, ztbz);
    }

    @Override
    public Pager<StationInfo> findStationInfoDto(String value, String ztbz) {
        return stationInfoDao.findStationInfoDto(value, ztbz);
    }

    @Override
    public List<SelectJson> listStationInfoBYIDS(String ids) {
        return stationInfoDao.listStationInfoBYIDS(ids);
    }

    @Override
    public List<TreeJson> listStationInfoByBookSet(String selectNames) {
        return stationInfoDao.listStationInfoByBookSet(selectNames);
    }

    @Override
    public List<SelectJson> listNodeStationInfo(String station) {
        return stationInfoDao.listNodeStationInfo(station);
    }

}
