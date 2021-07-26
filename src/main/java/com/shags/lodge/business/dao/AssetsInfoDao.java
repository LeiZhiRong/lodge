package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.AssetsInfo;
import org.springframework.stereotype.Repository;


/**
 * @author yglei
 * @classname AssetsInfoDao
 * @description 房屋资产Dao接口层实现类
 * @date 2021-07-26 17:30
 */
@Repository("assetsInfoDao")
public class AssetsInfoDao extends BusinessBaseDAO<AssetsInfo,String> implements IAssetsInfoDao{

}
