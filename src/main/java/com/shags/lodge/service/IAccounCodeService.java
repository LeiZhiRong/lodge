package com.shags.lodge.service;

import com.shags.lodge.util.Message;
import com.shags.lodge.primary.entity.AccounCode;

import java.util.List;

/**
 * 系统编号管理业务接口类
 * @author 雷智荣
 */
public interface IAccounCodeService {
    /**
     * 按ID查询
     * @param id
     * @return
     */
    AccounCode queryAccounCodeByID(String id);

    /**
     * 添加
     * @param accounCode
     * @return
     */
    Message addAccounCode(AccounCode accounCode);

    /**
     * 更新
     * @param accounCode
     * @return
     */
    Message updateAccounCode(AccounCode accounCode);

    /**
     * 按帐套获取
     * @param bookSet
     * @return
     */
    List<AccounCode> listAccounCode(String bookSet);

    /**
     * 按ID删除
     * @param id
     * @return
     */
    Message deleteAccounCodeByID(String id);

    AccounCode queryAccounCode(String bookSet,String codeType);


}
