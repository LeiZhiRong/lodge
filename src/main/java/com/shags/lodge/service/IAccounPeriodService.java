package com.shags.lodge.service;

import com.shags.lodge.dto.AccounPeriodDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.entity.AccounPeriod;

import java.util.List;

/**
 * 会计期间业务层接口类
 * @author 雷智荣
 */
public interface IAccounPeriodService {

    /**
     * 新增
     * @param accounPeriod
     * @return msg
     */
    Message addAccounPeriod(AccounPeriod accounPeriod);


    /**
     * 更新
     * @param accounPeriod
     * @return msg
     */
    Message updateAccounPeriod(AccounPeriod accounPeriod);


    /**
     * 删除
     * @param id
     * @return msg
     */
    Message deleteAccounPeriod(String id);


    /**
     * 分页数据
     * @return page
     */
    Pager<AccounPeriodDto> findAccounPeriodDto();


    /**
     * 列表
     * @return list
     */
    List<AccounPeriodDto> listAccounPeriodDto();

    /**
     * query
     * @param id
     * @return accounPeriod
     */
    AccounPeriod load(String id);


    /**
     * 获取当前会计月份
     * @return
     */
    String getMonth();


    /**
     * 获取当前会计月份实例
     * @return
     */
    AccounPeriod getNowMonth();


}
