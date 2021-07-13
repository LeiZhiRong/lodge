package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.PaymentMethodDto;
import com.shgs.lodge.dto.PaymentMethodListDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.PaymentMethod;
import com.shgs.lodge.util.Pager;

import java.util.List;

public interface IPaymentMethodDao extends IBaseDAO<PaymentMethod> {

    /**
     * 获取最大序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

    /**
     * 按ID删除
     * @param id
     * @return
     */
    boolean deletePaymentMethod(String id);

    /**
     * 按ID查询
     * @param id
     * @return
     */
    PaymentMethod queryPaymentMethod(String id);


    /**
     * 按编号查询
     * @param paymentBh
     * @return
     */
    PaymentMethod queryByPaymentBh(String paymentBh);

    /**
     * 获取下属总数
     * @param pid
     * @return
     */
    int getCountPaymentMethodByPid(String pid);

    /**
     * 按上级ID获取分页数据
     * @param pid
     * @param value
     * @return
     */
    Pager<PaymentMethodDto> findPaymentMethodDto(String pid, String value);


    /**
     * 获取选择器数据
     * @param pid
     * @param value
     * @return
     */
    List<PaymentMethodListDto> listPaymentMethodListDto(String pid, String value);


    /**
     * 按上级ID获取列表
     * @param pid
     * @param value
     * @return
     */
    List<PaymentMethod> listPaymentMethod(String pid, String value);

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
     * 更新关联信息
     * @param id
     * @param oldIds
     * @param newIds
     */
    void executeIds(String id, String oldIds, String newIds);

    /**
     * 检测编号是否重复
     * @param id
     * @param pid
     * @param paymentBh
     * @return
     */
    Integer countProjectsBh(String id,String pid,String paymentBh );


    /**
     * 获取目录树
     * @return
     */
    List<TreeJson> getPaymentMethod2TreeJson(String keyword);

    /**
     * 获取结算方式列表
     * @return
     */
    List<TreeJson> listPaymentMethod();
}
