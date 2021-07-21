package com.shags.lodge.service.primary;

import com.shags.lodge.dto.BankAccountListDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.primary.dao.IBankAccountDao;
import com.shags.lodge.primary.entity.BankAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行账号业务接口实现类
 *
 * @author 雷智荣
 */
@Service("bankAccountService")
public class BankAccountService implements IBankAccountService {

    private IBankAccountDao bankAccountDao;

    @Autowired
    public void setBankAccountDao(IBankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addBankAccount(BankAccount bankAccount) {
        Message msg = new Message(0, "添加失败");
        BankAccount dts = bankAccountDao.add(bankAccount);
        if (dts != null) {
            if ("T".equals(dts.getMoren()))
                bankAccountDao.updateMorenBz(dts.getCorpId(), dts.getId());
            msg.setMessage("添加成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateBankAccount(BankAccount bankAccount) {
        Message msg = new Message(0, "更新失败");
        if (bankAccountDao.update(bankAccount)) {
            if ("T".equals(bankAccount.getMoren()))
                bankAccountDao.updateMorenBz(bankAccount.getCorpId(), bankAccount.getId());
            msg.setMessage("更新成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteBankAccountById(String id) {
        Message msg = new Message(0, "删除失败");
        if (bankAccountDao.delete(id)) {
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteBankAccountByCorpId(String corpId) {
        return bankAccountDao.deleteBankAccountByCorpId(corpId);
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchSaveBankAccount(List<BankAccount> list) {
        return bankAccountDao.batchSave(list);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchUpdateBankAccount(List<BankAccount> list) {
        return bankAccountDao.batchUpdate(list);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<BankAccount> listBankAccount(String corpId, String ztbz) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from BankAccount b where b.corpId =:corpId ");
        alias.put("corpId", corpId);
        if (StringUtils.isNotEmpty(ztbz)) {
            jpql.append(" and b.ztbz =:ztbz ");
            alias.put("ztbz", ztbz);
        }
        return bankAccountDao.listByAlias(jpql.toString(), alias);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public BankAccount queryBankAccount(String id) {
        return bankAccountDao.load(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void batchDeleteCorpBank(String ids) {
        bankAccountDao.batchDeleteCorpBank(ids);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public BankAccount queryBankAccount(String corpId, String khyh, String yhzh) {
        return bankAccountDao.queryBankAccount(corpId, khyh, yhzh);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<BankAccountListDto> listBankAccountListDto(String corpId) {
        return bankAccountDao.listBankAccountListDto(corpId);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public BankAccount queryBankAccountByCorpId(String corpId) {
        return (BankAccount) bankAccountDao.queryObject("from BankAccount b where b.corpId =?0 and b.ztbz =?1 order by b.moren desc ", new Object[]{corpId, "T"});
    }

}
