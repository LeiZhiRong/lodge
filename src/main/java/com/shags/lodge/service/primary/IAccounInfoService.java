package com.shags.lodge.service.primary;

import com.shags.lodge.dto.AccounInfoDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.AccounInfo;

import java.util.List;

/**
 * 账套信息业务接口层
 * @author 雷智荣
 */
public interface IAccounInfoService {

    /**
     * 添加对象
     * @param accounInfo
     * @return msg
     */
    Message addAccounInfo(AccounInfo accounInfo);

    /**
     * 更新对象
     * @param accounInfo
     * @return msg
     */
    Message updateAccounInfo(AccounInfo accounInfo);

    /**
     * 按ID删除对象
     * @param id
     * @return msg
     */
    Message deleteAccounInfo(String id);

    /**
     * 按ID查询对象
     * @param id 关键字id
     * @return Object
     */
    AccounInfo queryAccounInfoByID(String id);

    /**
     * 按账套编号查询对象
     * @param bookSet 账套编号
     * @return Object
     */
    AccounInfo queryAccounInfoByBookSet(String bookSet);

     /**
     * 按关键字和状态标识查询列表
     * @param value 关键字
     * @param ztbz 状态标识
     * @return list
     */
    List<SelectJson> listAccounInfoToSelectJson(String value, String ztbz);

    /**
     * 获取分页数据
     * @param value 关键字
     * @param ztbz 状态标识
     * @return list
     */
    Pager<AccounInfoDto> findAccounInfoDto(String value, String ztbz);
}
