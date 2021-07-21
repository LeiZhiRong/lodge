package com.shags.lodge.service.primary;

import com.shags.lodge.dto.CashBankDto;
import com.shags.lodge.dto.CashBankListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.entity.CashBank;

import java.util.List;

/**
 * 科目管理业务接口类
 * @author 雷智荣
 */
public interface ICashBankService {

    /**
     * 添加
     * @param cashBank
     * @return Message
     */
    Message addCashBank(CashBank cashBank, String pid);

    /**
     * 更新
     * @param cashBank
     * @return Message
     */
    Message updateCashBank(CashBank cashBank,String pid);

    /**
     * 按ID删除
     * @param id
     * @return Message
     */
    Message deleteCashBank(String id);

    /**
     * 按ID查询
     * @param id
     * @return CashBank
     */
    CashBank queryCashBank(String id);

    /**
     * 根据PID获取下级科目
     * @param pid
     * @return list
     */
    List<CashBank> listByParent(String pid, String value);

    /**
     * 分页查询
     * @param pid
     * @return Pager
     */
    Pager<CashBankDto> findCashBankDto(String pid, String value);


    List<CashBankListDto> listCashBankDto(String pid, String value);


    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 按科目编号查询科目信息
     * @param kmBH
     * @return
     */
    CashBank queryByKmBH(String kmBH);

    /**
     * 获取科目目录树
     * @return
     */
    List<TreeJson> getCashBank2TreeJson(String keyword);

}
