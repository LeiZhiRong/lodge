package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.BankAccountListDto;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.BankAccount;
import com.shgs.lodge.util.Message;

import java.util.List;

/**
 * 开户银行持久层接口类
 *
 * @author 雷智荣
 */
public interface IBankAccountDao extends IBaseDAO<BankAccount> {

    /**
     * 批量删除
     * @param ids  *银行账号ID，多个以“,”号隔开
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    int batchDeleteCorpBank(String ids);

    /**
     * 按客商ID删除所有银行账号
     * @param corpId *客商ID
     * @return
     */
    Message deleteBankAccountByCorpId(String corpId);

    /**
     * 查询银行账号信息
     * @param corpId  *客商ID
     * @param khyh  开户银行
     * @param yhzh  银行账号
     * @return
     */
    BankAccount queryBankAccount(String corpId,String khyh,String yhzh);

    /**
     * 按客商ID获取银行账号列表
     * @param corpId *客商ID
     * @return
     */
    List<BankAccountListDto> listBankAccountListDto(String corpId);

    /**
     * 更新默认状态
     * @param corpId
     * @param id
     */
    void updateMorenBz(String corpId,String id);

}
