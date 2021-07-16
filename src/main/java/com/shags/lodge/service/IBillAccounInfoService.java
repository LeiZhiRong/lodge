package com.shags.lodge.service;

import com.shags.lodge.util.Message;
import com.shags.lodge.primary.entity.BillAccounInfo;

/**
 * 团购挂单-账务信息业务接口层
 *
 * @author 雷智荣
 */
public interface IBillAccounInfoService {
    /**
     * 添加
     * @param billAccounInfo
     * @return msg
     */
    @SuppressWarnings("UnusedReturnValue")
    Message addBillAccounInfo(BillAccounInfo billAccounInfo);

    /**
     * 更新
     * @param billAccounInfo
     * @return msg
     */
    Message updateAccounInfo(BillAccounInfo billAccounInfo);

    /**
     * 按ID删除
     * @param id
     * @return msg
     */
    Message deleteBillAccounInfoById(String id);

    /**
     * 按ID查询
     * @param id
     * @return Object
     */
    BillAccounInfo queryBillAccounInfoByID(String id);

    /**
     * 按时间获取最近一条记录
     * @param saleCorpID 销售方id
     * @param buyerCorpID 购买方ID
     * @param ztbz 状态标识
     * @return
     */
    BillAccounInfo queryMaxBillAccounInfo(String saleCorpID, String buyerCorpID, String ztbz);


    /**
     * 按天获取最大排序号
     * @return
     */
    Integer getMaxOrders();
}
