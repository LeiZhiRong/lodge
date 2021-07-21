package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.AccounInfoDto;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.AccounInfo;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;

import java.util.List;

/**
 * 账套信息持久层接口
 * @author 雷智荣
 */
public interface IAccounInfoDao extends IBaseDAO<AccounInfo> {

    /**
     * 按ID查询对象
     * @param id 关键字id
     * @return object
     */
    AccounInfo queryAccounInfoByID(String id);

    /**
     * 按账套编号查询对象
     * @param bookSet 账套编号
     * @return object
     */
    AccounInfo queryAccounInfoByBookSet(String bookSet);

    /**
     * 检测账套编号是否重名
     * @param id 排除的id
     * @param bookSet 账套编号
     * @return object
     */
    AccounInfo queryAccounInfoByIDAndBookSet(String id,String bookSet);

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
     * @return pager
     */
    Pager<AccounInfoDto> findAccounInfoDto(String value, String ztbz);

    /**
     * 按ID删除对象
     * @param id 关键字
     * @return boolean
     */
    boolean deleteAccounInfo(String id);


}
