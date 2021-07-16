package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.ReveExpeItemListDto;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.ReveExpeItem;

import java.util.List;

public interface IReveExpeItemDao extends IBaseDAO<ReveExpeItem> {
    /**
     * 按费项获取入账规则
     * @param proceedId  *费项ID*
     * @param auditStatus *表单状态*
     * @param onAccount *是否挂账*
     * @param paymentMethod *结算方式*
     * @return
     */
   List<ReveExpeItemListDto> listReveExpeItemListDto(String proceedId, String auditStatus, String onAccount, String paymentMethod);

}
