package com.shags.lodge.service.primary;

import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.StationInfo;

import java.util.List;

/**
 * 职位信息业务接口层
 * @author 雷智荣
 */
public interface IStationInfoService {

    /**
     * 添加
     * @param stationInfo
     * @return
     */
    Message addStationInfo(StationInfo stationInfo);

    /**
     * 编辑
     * @param stationInfo
     * @return
     */
    Message updateStationInfo(StationInfo stationInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    Message deleteStationInfo(String id);

    /**
     * 按ID查询对象
     * @param id
     * @return
     */
    StationInfo queryStationInfoByID(String id);

    /**
     * 按职务名称和账套查询对象
     * @param stationName 职务名称
     * @return
     */
    StationInfo queryStationInfoByStationName(String stationName);

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

    List<SelectJson> listStationInfoBYIDS(String ids);

    /**
     * 获取职务树
     * @param selectNames 标识勾选
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
