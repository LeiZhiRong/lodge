package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.StationInfo;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 职位信息持久接口层
 * @author 雷智荣
 */
public interface IStationInfoDao extends IBaseDAO<StationInfo> {

      /**
     * 按ID查询对象
     * @param id
     * @return
     */
    StationInfo queryStationInfoByID(String id);

    /**
     * 按职务名称和账套编号查询对象
     * @param stationName 职务名称
     * @return
     */
    StationInfo queryStationInfoByStationName(String stationName);

    StationInfo queryStationInfoByStationName(String stationName,String id);

    /**
     * 获取职位列表
     * @param value 查询内容
     * @param ztbz 状态标识
     * @return
     */
    List<SelectJson> listStationInfoToSelectJson(String value, String ztbz);

    /**
     * 获取分页数据
     * @param value 查询内容
     * @param ztbz 状态标识
     * @return
     */
    Pager<StationInfo> findStationInfoDto(String value, String ztbz);

    /**
     * 按ID删除对象
     * @param id
     * @return boolean
     */
    boolean deleteStationInfo(String id);

    List<SelectJson> listStationInfoBYIDS(String ids);

    /**
     * 按账套获取职务
     * @param selectNames 标识已选择职务名称
     * @return
     */
    List<TreeJson> listStationInfoByBookSet(String selectNames);

    /**
     * 按职位名称获取职位信息
     * @param station 职位名称,多个以逗号隔开
     * @return
     */
    List<SelectJson> listNodeStationInfo(String station);
    
}
