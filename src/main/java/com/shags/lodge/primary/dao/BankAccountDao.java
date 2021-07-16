package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.BankAccountListDto;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.BankAccount;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行信息持久层接口实现类
 *
 * @author 雷智荣
 */
@Repository("bankAccountDao")
public class BankAccountDao extends LodgeBaseDAO<BankAccount, String> implements IBankAccountDao {


    @Override
    public int batchDeleteCorpBank(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", _ids);
            Object o = super.executeByAliasJpql("delete from BankAccount b where b.corpId in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public Message deleteBankAccountByCorpId(String corpId) {
        Message msg = new Message(0, "删除失败");
        String jpql = "delete from BankAccount b where b.corpId =?0 ";
        Object o = super.executeByJpql(jpql, corpId);
        if (o != null) {
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public BankAccount queryBankAccount(String corpId, String khyh, String yhzh) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<String> str = new ArrayList<>();
        jpql.append(" from BankAccount b where b.corpId =:corpId ");
        alias.put("corpId", corpId);
        if (StringUtils.isNotEmpty(khyh)) {
            str.add("  b.khyh =:khyh  ");
            alias.put("khyh", khyh);
        }
        if (StringUtils.isNotEmpty(yhzh)) {
            str.add("  b.yhzh =:yhzh  ");
            alias.put("yhzh", yhzh);
        }
        if (str.size() > 0) {
            jpql.append(" and ( ").append(String.join("or", str)).append(" )");
        }
        return (BankAccount) super.queryObjectByAlias(jpql.toString(), alias);
    }

    @Override
    public List<BankAccountListDto> listBankAccountListDto(String corpId) {
        List<BankAccount> list = super.list("from BankAccount b where b.corpId =?0 and b.ztbz =?1", new Object[]{corpId, "T"});
        if (list != null && list.size() > 0) {
            return new BankAccountListDto().listBankAccountListDto(list);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void updateMorenBz(String corpId, String id) {
        List<BankAccount> dts = new ArrayList<>();
        List<BankAccount> list = super.list("from BankAccount b where b.corpId =?0 ", corpId);
        if (list != null && list.size() > 0) {
            for (BankAccount mast : list) {
                if (!id.equals(mast.getId())) {
                    mast.setMoren("F");
                    dts.add(mast);
                }
            }
        }
        if(dts.size()>0)
            super.batchUpdate(dts);

    }


}
