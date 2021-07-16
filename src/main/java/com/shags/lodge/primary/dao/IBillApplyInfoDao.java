package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.BillApplyInfo;
import com.shags.lodge.util.Pager;

import java.util.List;

/**
 * 开票申请持久接口类
 * @author 雷智荣
 */
public interface IBillApplyInfoDao extends IBaseDAO<BillApplyInfo> {

    /**
     * 按用户ID删除实例
     *
     * @param id ID
     * @return
     */
    boolean deleteBillApplyInfo(String id);

    /**
     * 按ID查询信息
     *
     * @param id ID
     * @return
     */
    BillApplyInfo queryBillApplyInfoById(String id);

    /**
     * 分页查询
     * @param field 字段名称
     * @param value 查询内容
     * @param starDate 开始日期
     * @param endDate 结束日期
     * @param ztbz 状态标识
     * @return list
     */
    Pager<BillApplyInfo> listBillApplyInfo(String field, String value, String starDate, String endDate, List<String> ztbz, String bookSet, String userBh);


    /**
     * 分页查询
     * @param field 字段名称
     * @param value 查询内容
     * @param starDate 开始日期
     * @param endDate 结束日期
     * @param ztbz 状态标识
     * @param bookSet 账套编号
     * @param applyDeptBH 申请部门编号,多个以“，”隔开
     * @param saleCorpID 销售方ID
     * @param buyerCorpID 购买方ID
     * @return list
     */
    Pager<BillApplyInfo> listBillApplyInfo( String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID);

    List<BillApplyInfo> listToBillApplyInfo( String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID);

    /**
     *
     * 批量删除信息
     * @param ids
     * @return
     */
    int batchDeleteApply(String ids);

    /**
     *
     * 批量添加公司信息
     * @param list
     * @return
     */
    boolean batchSaveBillApplyInfo(List<BillApplyInfo> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    boolean batchUpdateBillApplyInfo(List<BillApplyInfo> list);

    /**
     * 获取最大排序号
     * @return
     */
    Integer getMaxOrders();

    List<BillApplyInfo> exportBillApplyInfo(String field,  String value, String starDate, String endDate, String ztbz,String bookSet,String userBh);

    /**
     * 更新状态标识
     * @param ids
     * @param ztbz
     * @return
     */
    int batchExamine(String ids,String ztbz);


}
