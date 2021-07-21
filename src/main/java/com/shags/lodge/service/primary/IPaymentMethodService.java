package com.shags.lodge.service.primary;


import com.shags.lodge.dto.PaymentMethodDto;
import com.shags.lodge.dto.PaymentMethodListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.entity.PaymentMethod;

import java.util.List;

public interface IPaymentMethodService {

    /**
     * 添加
     *
     * @param paymentMethod
     * @return Message
     */
    Message addPaymentMethod(PaymentMethod paymentMethod, String pid);

    /**
     * 更新
     *
     * @param paymentMethod
     * @return Message
     */
    Message updatePaymentMethod(PaymentMethod paymentMethod, String pid);

    /**
     * 按ID删除
     *
     * @param id
     * @return Message
     */
    Message deletePaymentMethod(String id);

    /**
     * 按ID查询
     *
     * @param id
     * @return PaymentMethod
     */
    PaymentMethod queryPaymentMethod(String id);

    /**
     * 根据PID获取下级数据
     *
     * @param pid
     * @return list
     */
    List<PaymentMethod> listByParent(String pid, String value);

    /**
     * 分页查询
     *
     * @param pid
     * @return Pager
     */
    Pager<PaymentMethodDto> findPaymentMethodDto(String pid, String value);


    List<PaymentMethodListDto> listPaymentMethodDto(String pid, String value);


    /**
     * 更新排序号
     *
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 按编号查询
     *
     * @param paymentBh
     * @return
     */
    PaymentMethod queryByPaymentBh(String paymentBh);

    /**
     * 获取目录树
     *
     * @return
     */
    List<TreeJson> getPaymentMethod2TreeJson(String keyword);

    /**
     * 获取结算方式列表
     * @return
     */
    List<TreeJson> listPaymentMethod();


}
