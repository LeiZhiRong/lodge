package com.shags.lodge.primary.dao.basic;

import com.shags.lodge.util.Pager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * IBaseDao 接口类
 * @param <T> 实体类
 */
@SuppressWarnings({"rawtypes", "unused"})
public interface IBaseDAO<T> {

    /**
     * 查询对象
     * @param clz 实体类Class
     * @param id 关键字
     * @return T
     */
    T load(Class<?> clz, String id);

    /**
     * 查询对象
     * @param id 关键字
     * @return T
     */
    T load(Serializable id);

    /**
     * jpql查询对象
     * @param jpql jpql语句
     * @return Object
     */
    Object queryObject(String jpql);

    /**
     * jpql查询对象
     * @param jpql jpql语句,参数格式[arg1=?0]
     * @param arg 参数 格式[Object]
     * @return Object
     */
    Object queryObject(String jpql, Object arg);

    /**
     * jpql查询对象
     * @param jpql jpql语句,参数格式[arg1=?0,arg2=?1...]
     * @param args 参数 格式[new Object[]{arg1,arg2...}]
     * @return Object
     */
    Object queryObject(String jpql, Object[] args);

    /**
     * jpql查询对象
     * @param jpql jpql语句，参数格式[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数 格式 [Object[]{arg1,arg2...}]
     * @return Object
     */
    Object queryObjectByAlias(String jpql, Map<String, Object> alias);

    /**
     * 新增对象
     * @param entity T
     * @return T
     */
    T add(T entity);

    /**
     * 批量新增对象
     * @param t 对象集合List<T> t
     * @return boolean
     */
    boolean batchSave(List<T> t);

    /**
     * 删除对象
     * @param entity T
     * @return boolean
     */
    boolean delete(T entity);

    /**
     * 删除对象
     * @param id 关键字
     * @return boolean
     */
    boolean delete(Serializable id);

    /**
     * 更新对象
     * @param e T
     * @return boolean
     */
    boolean update(T e);

    /**
     * 批量更新对象
     * @param t 对象集合List<T> t
     * @return boolean
     */
    boolean batchUpdate(List<T> t);

    /**
     * jpql-查询对象列表
     * @param jpql jpql语句
     * @return list<T>
     */
    List<T> list(String jpql);

    /**
     * jpql-查询对象列表
     * @param jpql jpql语句，参数表达式[arg=?0]
     * @param arg 参数 格式 [Object]
     * @return list<T>
     */
    List<T> list(String jpql, Object arg);

    /**
     * jpql-查询对象列表
     * @param jpql jpql语句，参数表达式[arg1=?0,arg2=?01...]
     * @param args 参数 格式 [Object[]{arg1,arg2...}]
     * @return list<T>
     */
    List<T> list(String jpql, Object[] args);

    /**
     * jpql-查询对象列表
     * @param jpql  jpql语句，参数表达式[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数 格式 [Map<String,Object>]
     * @return list<T>
     */
    List<T> listByAlias(String jpql, Map<String, Object> alias);

    /**
     * sql原生-查询对象列表
     * @param sql sql语句
     * @return list<Map>
     */
    List<Map> listToMapBySql(String sql);

    /**
     * sql原生-查询对象列表
     * @param sql sql语句，参数表达式[arg1=?0]
     * @param arg 参数 格式[Object]
     * @return list<Map>
     */
    List<Map> listToMapBySql(String sql, Object arg);

    /**
     * sql原生-查询对象列表
     * @param sql sql语句，参数表达式[arg1=?0,arg2=?1....]
     * @param args 参数 格式[Object[]{arg1,arg2...}]
     * @return list<Map>
     */
    List<Map> listToMapBySql(String sql, Object[] args);

    /**
     * sql原生-查询对象列表
     * @param sql sql语句，参数表达式[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数 格式[Map<String, Object>]
     * @return @return list<Map>
     */
    List<Map> listToMapByAliasSql(String sql, Map<String, Object> alias);

    /**
     * jpql分页查询
     * @param jpql jpql语句
     * @return pager<T>
     */
    Pager<T> find(String jpql);

    /**
     * jpql分页查询
     * @param jpql jpql语句,参娄表达式[arg1=?0]
     * @param arg 参数 格式[Object]
     * @return pager<T>
     */
    Pager<T> find(String jpql, Object arg);

    /**
     * jpql分页查询
     * @param jpql jpql语句，参数表达式[arg1=?0,arg2=?1...]
     * @param args 参数 格式[Object[]{arg1,arg2...}]
     * @return pager<T>
     */
    Pager<T> find(String jpql, Object[] args);

    /**
     * jpql分页查询
     * @param jpql jpql语句，参数表达式[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数 格式[Map<String, Object>]
     * @return  pager<T>
     */
    Pager<T> find(String jpql, Map<String, Object> alias);

    /**
     * jpql executeUpdate更新
     * @param jpql jpql语句
     * @return Object
     */
    Object executeByJpql(String jpql);

    /**
     * jpql executeUpdate更新
     * @param jpql jpql语句,参娄表达式[arg1=?0]
     * @param arg  参数 格式[Object]
     * @return Object
     */
    Object executeByJpql(String jpql, Object arg);

    /**
     * jpql executeUpdate更新
     * @param jpql jpql语句,参娄表达式[arg1=?0,arg2=?01...]
     * @param args 参数 格式[new Object[]{arg1,arg2...}]
     * @return Object
     */
    Object executeByJpql(String jpql, Object[] args);

    /**
     * jpql executeUpdate更新
     * @param jpql jpql语句,参娄表达式[arg1=:arg1,arg2=:arg2...]
     * @param alias  参数 格式[Map<String, Object>]
     * @return Object
     */
    Object executeByAliasJpql(String jpql, Map<String, Object> alias);

    /**
     * sql executeUpdate更新
     * @param sql sql语句
     * @return Object
     */
    Object executeBySql(String sql);

    /**
     * sql executeUpdate更新
     * @param sql sql,参娄表达式[arg1=?0]
     * @param arg  参数 格式[Object]
     * @return Object
     */
    Object executeBySql(String sql, Object arg);

    /**
     * sql executeUpdate更新
     * @param sql sql,参娄表达式[arg1=?0,arg2=?01...]
     * @param args 参数 格式[new Object[]{arg1,arg2...}]
     * @return Object
     */
    Object executeBySql(String sql, Object[] args);

    /**
     * sql executeUpdate更新
     * @param sql sql语句,参娄表达式[arg1=:arg1,arg2=:arg2...]
     * @param alias  参数 格式[Map<String, Object>]
     * @return Object
     */
    Object executeByAliasSql(String sql, Map<String, Object> alias);

}
