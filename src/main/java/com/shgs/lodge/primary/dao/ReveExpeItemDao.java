package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.ReveExpeItemListDto;
import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.ReveExpeItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reveExpeItemDao")
public class ReveExpeItemDao extends BaseDAO<ReveExpeItem, String> implements IReveExpeItemDao {
    @Override
    public List<ReveExpeItemListDto> listReveExpeItemListDto(String proceedId, String auditStatus, String onAccount, String paymentMethod) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ReveExpeItem r where r.proceedType.id =:proceedId  ");
        alias.put("proceedId", proceedId);
        if (StringUtils.isNotEmpty(auditStatus)) {
            jpql.append(" and r.auditStatus =:auditStatus ");
            alias.put("auditStatus", auditStatus);
        }
        if (StringUtils.isNotEmpty(onAccount)) {
            jpql.append(" and r.onAccount =:onAccount");
            alias.put("onAccount", onAccount);
        }
        if (StringUtils.isNotEmpty(paymentMethod)) {
            jpql.append(" and r.paymentMethod like:paymentMethod");
            alias.put("paymentMethod", "%" + paymentMethod + "%");
        }
        List<ReveExpeItem> cts = super.listByAlias(jpql.toString(), alias);
        if (cts != null && cts.size() > 0) {
            return new ReveExpeItemListDto().listReveExpeItemListDto(cts);
        }
        return null;
    }
}
