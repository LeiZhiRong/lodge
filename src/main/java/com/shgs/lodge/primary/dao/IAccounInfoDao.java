package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.AccounInfoDto;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.AccounInfo;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;

import java.util.List;

/**
 * 账套信息持久层接口
 * @author 雷智荣
 */
public interface IAccounInfoDao extends IBaseDAO<AccounInfo,String> {

    /**
     * 按ID查询对象
     * @param id 关键字id
     * @return
     */
    AccounInfo queryAccounInfoByID(String id);

    /**
     * 按账套编号查询对象
     * @param bookSet 账套编号
     * @return
     */
    AccounInfo queryAccounInfoByBookSet(String bookSet);

    /**
     * 检测账套编号是否重名
     * @param id 排除的id
     * @param bookSet 账套编号
     * @return
     */
    AccounInfo queryAccounInfoByIDAndBookSet(String id,String bookSet);

    /**
     * 按关键字和状态标识查询列表
     * @param value 关键字
     * @param ztbz 状态标识
     * @return
     */
    List<SelectJson> listAccounInfoToSelectJson(String value,String ztbz);

    /**
     * 获取分页数据
     * @param value 关键字
     * @param ztbz 状态标识
     * @return
     */
    Pager<AccounInfoDto> findAccounInfoDto(String value, String ztbz);

    /**
     * 按ID删除对象
     * @param id
     * @return boolean
     */
    boolean deleteAccounInfo(String id);


}
