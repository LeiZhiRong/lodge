package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.VoucherInfo;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("voucherInfoDao")
public class VoucherInfoDao extends LodgeBaseDAO<VoucherInfo,String> implements IVoucherInfoDao {

    @Override
    public Message addVoucherInfo(VoucherInfo voucherInfo) {
        Message msg = new Message(0, "新增失败");
        if(super.add(voucherInfo)!=null){
            msg.setMessage("新增成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public void deleteVoucherInfo(String applyId, String proceedId, String auditStatus, String onAccount, String paymentMethod) {
        String jpql = "delete from VoucherInfo v where v.applyId =:applyId  and v.proceedId =:proceedId and v.auditStatus =:auditStatus and v.onAccount =:onAccount and v.paymentMethod =:paymentMethod ";
        Map<String, Object> alias = new HashMap<>();
        alias.put("applyId",applyId);
        alias.put("proceedId",proceedId);
        alias.put("auditStatus",auditStatus);
        alias.put("onAccount",onAccount);
        alias.put("paymentMethod",paymentMethod);
        Object o = super.executeByAliasJpql(jpql, alias);
    }

    @Override
    public void batchDeleteVoucherInfo(String applyId) {
        if (applyId != null && !applyId.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(applyId, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", _ids);
            Object o = super.executeByAliasJpql("delete from VoucherInfo b where b.applyId in( :ids) ", alias);
        }


    }

}
