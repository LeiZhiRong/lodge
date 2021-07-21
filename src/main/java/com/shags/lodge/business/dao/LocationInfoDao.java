package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.LocationInfo;
import org.springframework.stereotype.Repository;

/**
 * @author yglei
 * @Title: 资产管理-坐落位置Dao接口实现类
 * @date 2021-07-2112:22
 */
@Repository("locationInfoDao")
public class LocationInfoDao extends BusinessBaseDAO<LocationInfo,String> implements ILocationInfoDao {

}
