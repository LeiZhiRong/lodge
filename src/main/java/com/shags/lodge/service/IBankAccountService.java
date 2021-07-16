package com.shags.lodge.service;

import com.shags.lodge.dto.BankAccountListDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.primary.entity.BankAccount;

import java.util.List;

/**
 * 银行账号业务接口层
 * @author 雷智荣
 */
@SuppressWarnings("UnusedReturnValue")
public interface IBankAccountService {

    /**
     * 添加
     * @param bankAccount
     * @return
     */
    Message addBankAccount(BankAccount bankAccount);

    /**
     * 编辑
     * @param bankAccount
     * @return
     */
    Message updateBankAccount(BankAccount bankAccount);

    /**
     * 删除
     * @param id 银行账号ID
     * @return
     */
    Message deleteBankAccountById(String id);

    /**
     * 删除
     * @param corpId 客商ID
     * @return
     */
    Message deleteBankAccountByCorpId(String corpId);

    /**
     * 批量添加
     * @param list
     * @return
     */
    boolean batchSaveBankAccount(List<BankAccount> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    boolean batchUpdateBankAccount(List<BankAccount> list);

    /**
     * 获取列表
     * @param corpId 客商ID
     * @param ztbz 状态标识
     * @return
     */
    List<BankAccount> listBankAccount(String corpId,String ztbz);

    BankAccount queryBankAccount(String id);

    /**
     * 按客商编号批量删除银行信息
     * @param ids
     */
    void batchDeleteCorpBank(String ids);

    /**
     * 查询银行账号对象
     * @param corpId *客商ID
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

    BankAccount queryBankAccountByCorpId(String corpId);

}
