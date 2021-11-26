package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.IBusinessBaseDAO;
import com.shags.lodge.business.entity.AssetsInfo;

/**
 * @author yglei
 * @classname IAssetsInfoDao
 * @description 楼栋信息持久层接口
 * @date 2021-07-26 17:29
 */
public interface IAssetsInfoDao extends IBusinessBaseDAO<AssetsInfo> {

    /**
     * 按卡编号查询对象
     * @param cardNumber 卡片编号
     * @param notInId 过滤ID
     * @return
     */
    AssetsInfo queryAssetsInfoByCardNumber(String cardNumber,String notInId);

    /**
     *获取自定义卡片最后自编号
     * @return int
     */
    public int getMaxCardNumber();

}
