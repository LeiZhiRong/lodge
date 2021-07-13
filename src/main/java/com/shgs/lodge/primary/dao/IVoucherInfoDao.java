package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.VoucherInfo;
import com.shgs.lodge.util.Message;

public interface IVoucherInfoDao extends IBaseDAO<VoucherInfo> {

    /**
     * 添加凭证记录
     * @param voucherInfo
     * @return
     */
    Message addVoucherInfo(VoucherInfo voucherInfo);

    /**
     * 删除凭证记录
     * @param applyId 单据ID
     * @param proceedId 费项ID
     * @param auditStatus 表单状态
     * @param onAccount 是否挂账
     * @param paymentMethod 结算方式
     */
     void deleteVoucherInfo(String applyId, String proceedId, String auditStatus, String onAccount, String paymentMethod);

    /**
     * 按订单号删除凭证记录
     * @param applyId
     */

    void batchDeleteVoucherInfo(String applyId);


}
