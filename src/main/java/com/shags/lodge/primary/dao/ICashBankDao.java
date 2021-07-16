package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.CashBankDto;
import com.shags.lodge.dto.CashBankListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.CashBank;
import com.shags.lodge.util.Pager;

import java.util.List;

/**
 * 科目管理持久接口类
 * @author 雷智荣
 */
public interface ICashBankDao extends IBaseDAO<CashBank> {

    /**
     * 获取最大序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

    /**
     * 按ID删除科目信息
     * @param id
     * @return
     */
    boolean deleteCashBank(String id);

    /**
     * 按ID查询科目信息
     * @param id
     * @return
     */
    CashBank queryCashBank(String id);


    /**
     * 按科目编号查询科目信息
     * @param kmBH
     * @return
     */
    CashBank queryByKmBH(String kmBH);

    /**
     * 获取下属部门总数
     * @param pid
     * @return
     */
    int getCountCashBankByPid(String pid);

    /**
     * 按上级科目ID获取分页数据
     * @param pid
     * @param value
     * @return
     */
    Pager<CashBankDto> findCashBankDto(String pid, String value);


    List<CashBankListDto> listCashBankDto(String pid, String value);


    /**
     * 按上级科目ID获取列表
     * @param pid
     * @param value
     * @return
     */
    List<CashBank> listCashBank(String pid, String value);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(String ids);

    /**
     * 更新科目关联信息
     * @param id
     * @param oldIds
     * @param newIds
     */
    void executeIds(String id, String oldIds, String newIds);

    /**
     * 检测科目编号是否重复
     * @param id
     * @param pid
     * @param kmBH
     * @return
     */
    Integer countKmBH(String id,String pid,String kmBH );


    /**
     * 获取科目目录树
     * @return
     */
    List<TreeJson> getCashBank2TreeJson(String keyword);

}
