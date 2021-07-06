package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.AccounCode;

import java.util.List;

public interface IAccounCodeDao extends IBaseDAO<AccounCode,String> {

    /**
     * 按ID查询
     * @param id
     * @return
     */
    AccounCode queryAccounCodeByID(String id);

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
    boolean deleteAccounCodeByID(String id);

    List<AccounCode> listAccounCode(String bookSet,String codeType);

    AccounCode queryAccounCode(String bookSet,String codeType);




}
