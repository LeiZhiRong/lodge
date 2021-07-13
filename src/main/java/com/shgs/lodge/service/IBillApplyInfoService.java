package com.shgs.lodge.service;

import com.shgs.lodge.Vo.BillConfirmInfoVo;
import com.shgs.lodge.dto.BillApplyHistoryInfoDto;
import com.shgs.lodge.dto.BillApplyInfoDto;
import com.shgs.lodge.dto.BillConfirmInfoDto;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;

import java.util.List;

/**
 * 开票申请业务接口类
 * @author 雷智荣
 */
public interface IBillApplyInfoService {

    /**
     * 保存
     * @param billApplyInfo
     * @return
     */
    Message saveBillApplyInfo(BillApplyInfo billApplyInfo);

    /**
     * 更新
     * @param billApplyInfo
     * @param proccedID
     * @return
     */
    Message updateBillApplyInfo(BillApplyInfo billApplyInfo,String proccedID);

    /**
     * 按用户ID删除实例
     *
     * @param id ID
     * @return
     */
    Message deleteBillApplyInfo(String id);

    /**
     * 按ID查询信息
     *
     * @param id ID
     * @return
     */
    BillApplyInfo queryBillApplyInfoById(String id);

    /**
     * 分页查询
     * @param field
      * @param ztbz
     * @return
     */
    Pager<BillApplyInfoDto> listBillApplyInfoDto(String field,  String value,  String ztbz,String bookSet,String userBh);

    /**
     * 开票申请历史数据
     * @param field
     * @param value
     * @param starDate
     * @param endDate
     * @param ztbz
     * @param bookSet
     * @param userBh
     * @return
     */
    Pager<BillApplyHistoryInfoDto> listHistoryInfoDto(String field, String value, String starDate, String endDate, List<String> ztbz, String bookSet, String userBh);

    /**
     * 开票确认分页数据
     * @param field  查询字段
     * @param value  查询内容
     * @param starDate  开始时间
     * @param endDate  结束时间
     * @param ztbz  状态标识
     * @param bookSet 账套编号
     * @param applyDeptBH 申请部门编号，多个以“，”隔开
     * @param saleCorpID 销售方ID
     * @param buyerCorpID 购买方ID
     * @return
     */
    Pager<BillConfirmInfoDto> listBillConfirmInfoDto(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID);

    List<BillConfirmInfoVo> listBillConfirmInfoVo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID);

    /**
     *
     * 批量删除信息
     * @param ids
     * @return
     */
    Message batchDeleteApply(String ids);

    /**
     *
     * 批量添加公司信息
     * @param list
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean batchSaveBillApplyInfo(List<BillApplyInfo> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    boolean batchUpdateBillApplyInfo(List<BillApplyInfo> list);

    /**
     * 获取开票申请单编号
     * @return
     */
    String getOrderCode(String bookSet, String codeType,String deptBh,String corpBh);


    List<BillApplyInfo> exportBillApplyInfo(String field, String value, String starDate, String endDate, String ztbz,String bookSet,String userBh);

    Integer getMaxOrders();

    /**
     * 更新票据状态
     * @param ids
     * @param ztbz
     * @return
     */
    Message batchExamine(String ids, String ztbz);

}
