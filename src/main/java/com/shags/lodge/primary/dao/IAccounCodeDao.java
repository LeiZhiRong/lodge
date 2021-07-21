package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.AccounCode;

import java.util.List;

public interface IAccounCodeDao extends IBaseDAO<AccounCode> {

    /**
     * 按ID查询
     * @param id 关键字
     * @return object
     */
    AccounCode queryAccounCodeByID(String id);

    /**
     * 按帐套获取
     * @param bookSet 账套编号
     * @return list
     */
    List<AccounCode> listAccounCode(String bookSet);

    /**
     * 按ID删除
     * @param id 关键字
     * @return boolean
     */
    boolean deleteAccounCodeByID(String id);

    List<AccounCode> listAccounCode(String bookSet,String codeType);

    AccounCode queryAccounCode(String bookSet,String codeType);




}
