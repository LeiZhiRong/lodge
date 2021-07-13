package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.BillAccounInfo;

/**
 * 团购挂单账务明细持久层接口类
 * @author 雷智荣
 */
public interface IBillAccounInfoDao extends IBaseDAO<BillAccounInfo> {

    /**
     * 按ID查询实体对象
     * @param id
     * @return object
     */
    BillAccounInfo queryBillAccounInfoByID(String id);

    /**
     * 按ID删除实体对象
     * @param id
     * @return boolean
     */
    boolean deleteBillAccounInfoByID(String id);

    /**
     * 按时间获取最近一条记录
     * @param saleCorpID 销售方ID
     * @param buyerCorpID 购买方ID
     * @param ztbz 状态标识
     * @return
     */
    BillAccounInfo queryMaxBillAccounInfo(String saleCorpID,String buyerCorpID,String ztbz);

    /**
     * 按天获取最大排序号
     * @return
     */
    Integer getMaxOrders();

}
