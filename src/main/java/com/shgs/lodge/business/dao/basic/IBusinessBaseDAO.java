package com.shgs.lodge.business.dao.basic;

import com.shgs.lodge.util.Pager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * jpql辅助工具
 * @author 雷智荣
 */
public interface IBusinessBaseDAO<T> {

    T load(Class<?> clz, String id);

    T load(Serializable id);

    T add(T entity);

    boolean batchSave(List<T> t);

    boolean delete(T entity);

    boolean delete(Serializable id);

    boolean update(T e);

    boolean batchUpdate(List<T> t);

    List<T> list(String jpql);

    List<T> list(String jpql, Object arg);

    List<T> list(String jpql, Object[] args);

    List<T> listByAlias(String jpql, Map<String, Object> alias);

    List<Map> listToMapBySql(String sql);

    List<Map> listToMapBySql(String sql, Object arg);

    List<Map> listToMapBySql(String sql, Object[] args);

    List<Map> listToMapByAliasSql(String sql, Map<String, Object> alias);

    Pager<T> find(String jpql);

    Pager<T> find(String jpql, Object arg);

    Pager<T> find(String jpql, Object[] args);

    Pager<T> find(String jpql, Map<String, Object> alias);

    Object queryObject(String jpql);

    Object queryObject(String jpql, Object arg);

    Object queryObject(String jpql, Object[] args);

    Object queryObjectByAlias(String jpql, Map<String, Object> alias);

    Object executeBySql(String sql);

    Object executeBySql(String sql, Object arg);

    Object executeBySql(String sql, Object[] args);

    Object executeByAliasSql(String sql, Map<String, Object> alias);

    Object executeByJpql(String jpql);

    Object executeByJpql(String jpql, Object arg);

    Object executeByJpql(String jpql, Object[] args);

    Object executeByAliasJpql(String jpql, Map<String, Object> alias);



}
