package com.shgs.lodge.primary.dao.basic;


import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SystemContext;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * JPA数据库操作基础类封装
 *
 * @param <T>
 * @author 雷智荣
 */
public class BaseDAO<T,ID> implements IBaseDAO<T,ID> {

    private static final int BATCH_SIZE = 500;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> entityClass = null;

    private Class<ID> idClass = null;

    @PersistenceContext(unitName = "primaryEntityManagerFactory")
    private EntityManager entityManager;

    {

        Type type = getClass().getGenericSuperclass();
        while (!(type instanceof ParameterizedType)) {
            type = ((Class<?>) type).getGenericSuperclass();
            if (type == null || "java.lang.Object".equals(type.getClass())) {
                break;
            }
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] genericTypies = parameterizedType.getActualTypeArguments();
            entityClass = (Class<T>) genericTypies[0];
            idClass  = (Class<ID>)genericTypies[1];
        }
    }

    @Override
    public T load(Class<?> clz, String id) {
        return (T) entityManager.find(clz, id);
    }


    @Override
    public T load(Serializable id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * 添加对象
     *
     * @param entity
     * @return T
     */
    @Override
    public T add(T entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 更新对象
     *
     * @param entity
     * @return boolean
     */
    @Override
    public boolean update(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    /**
     * 删除对象
     *
     * @param entity
     * @return boolean
     */
    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag = true;
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    @Override
    public boolean delete(Serializable id) {
        boolean flag = false;
        try {
            Query query = entityManager.createQuery("delete from " + entityClass.getName() + " b where b."+getIdField() +" =:id ");
            query.setParameter("id", id);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            return false;
        }
        return flag;
    }


    /**
     * 按条件查询对象
     *
     * @param jpql jpql语句
     * @return list
     */
    @Override
    public List<T> list(String jpql) {
        return this.list(jpql, null, null);
    }

    /**
     * 按条件查询对象
     *
     * @param jpql
     * @param arg
     * @return list
     */
    @Override
    public List<T> list(String jpql, Object arg) {
        return this.list(jpql, new Object[]{arg}, null);
    }

    /**
     * 按条件查询对象
     *
     * @param jpql
     * @param args
     * @return list
     */
    @Override
    public List<T> list(String jpql, Object[] args) {
        return this.list(jpql, args, null);
    }

    /**
     * 按条件查询对象
     *
     * @param jpql
     * @param alias
     * @return list
     */
    @Override
    public List<T> listByAlias(String jpql, Map<String, Object> alias) {
        return this.list(jpql, null, alias);
    }


    /**
     * 按条件查询对象
     *
     * @param jpql
     * @return Object
     */
    @Override
    public Object queryObject(String jpql) {
        return this.queryObject(jpql, null, null);
    }

    /**
     * 按条件查询对昂
     *
     * @param jpql
     * @param arg
     * @return Object
     */
    @Override
    public Object queryObject(String jpql, Object arg) {
        return this.queryObject(jpql, new Object[]{arg}, null);
    }

    /**
     * 按条件查询对象
     *
     * @param jpql
     * @param args
     * @return Object
     */
    @Override
    public Object queryObject(String jpql, Object[] args) {
        return this.queryObject(jpql, args, null);
    }

    /**
     * 按条件查询对象
     *
     * @param jpql
     * @param alias
     * @return Object
     */
    @Override
    public Object queryObjectByAlias(String jpql, Map<String, Object> alias) {
        return this.queryObject(jpql, null, alias);
    }

    /**
     * 按条件查询对象分页数据
     *
     * @param jpql
     * @return Pager
     */
    @Override
    public Pager<T> find(String jpql) {
        return this.find(jpql, null, null);
    }

    /**
     * 按条件查询对象分页数据
     *
     * @param jpql
     * @param arg
     * @return Pager
     */
    @Override
    public Pager<T> find(String jpql, Object arg) {
        return this.find(jpql, new Object[]{arg}, null);
    }

    /**
     * 按条件查询对象分页数据
     *
     * @param jpql
     * @param args
     * @return Pager
     */
    @Override
    public Pager<T> find(String jpql, Object[] args) {
        return this.find(jpql, args, null);
    }


    /**
     * 按条件查询对象分页数据
     *
     * @param jpql
     * @param alias
     * @return
     */
    @Override
    public Pager<T> find(String jpql, Map<String, Object> alias) {
        return this.find(jpql, null, alias);
    }


    /**
     * 按原生SQL查询对象分页数据
     *
     * @param sql
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return Pager
     */
    public <N> Pager<N> findToBeanBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象分页数据
     *
     * @param sql
     * @param arg
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return
     */
    public <N> Pager<N> findToBeanBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象分页数据
     *
     * @param sql
     * @param args
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return
     */
    public <N> Pager<N> findToBeanBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, args, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象分页数据
     *
     * @param sql
     * @param alias
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return
     */
    public <N> Pager<N> findToBeanByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, null, alias, clz, hasEntity);
    }

    /**
     * 批量增加对象
     *
     * @param t
     * @return boolean
     */
    @Override
    public boolean batchSave(List<T> t) {
        Iterator iterator = t.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
        return true;
    }

    /**
     * 批量更新对象
     *
     * @param t
     * @return boolean
     */
    @Override
    public boolean batchUpdate(List<T> t) {
        Iterator iterator = t.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.merge(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
        return true;
    }

    /**
     * 按原生SQL语句查询
     *
     * @param sql       sql语句
     * @param clz       Entity/pojo
     * @param hasEntity 为true时返回Entity，为false时返回pojo(多用于组合查询)
     * @param <N>       实例化类 Entity/pojo
     * @return List
     */
    public <N> List<N> listToBeanBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象
     *
     * @param sql
     * @param arg
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return list
     */
    public <N> List<N> listToBeanBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象
     *
     * @param sql
     * @param args
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return list
     */
    public <N> List<N> listToBeanBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, args, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询对象
     *
     * @param sql
     * @param alias
     * @param clz
     * @param hasEntity
     * @param <N>
     * @return list
     */
    public <N> List<N> listToBeanByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, null, alias, clz, hasEntity);
    }

    private List<T> list(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createQuery(jpql);
            setAliasParameter(query, alias);
            setParameter(query, args);
            List<T> list = query.getResultList();
            entityManager.close();
            return list;
        } catch (Exception e) {
            return null;
        }

    }

    private String initSort(String jpql) {
        String order = SystemContext.getOrder();
        String sort = SystemContext.getSort();
        if (sort != null && !"".equals(sort.trim())) {
            jpql += " order by " + sort;
            if (!"desc".equals(order))
                jpql += " asc";
            else
                jpql += " desc";
        }
        return jpql;
    }

    private void setAliasParameter(Query query, Map<String, Object> alias) {
        if (alias != null) {
            Set<String> keys = alias.keySet();
            for (String key : keys) {
                Object val = alias.get(key);
                if (val instanceof Collection<?>) {
                    // 查询条件是列表
                    query.setParameter(key, (Collection<?>) val);
                } else if (val instanceof Object[]) {
                    query.setParameter(key, (Object[]) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        }
    }

    private void setParameter(Query query, Object[] args) {
        if (args != null && args.length > 0) {
            int index = 0;
            for (Object arg : args) {
                query.setParameter(index++, arg);
            }
        }
    }

    private Object queryObject(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createQuery(jpql);
            setAliasParameter(query, alias);
            setParameter(query, args);
            query.setFirstResult(0);
            query.setMaxResults(1);
            Object o = query.getSingleResult();
            entityManager.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    private Pager<T> find(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            String jpql1 = initSort(jpql);
            String cq = getCountJpql(jpql, true);
            Query cquery = entityManager.createQuery(cq);
            Query query = entityManager.createQuery(jpql1);
            // 设置别名参数
            setAliasParameter(query, alias);
            setAliasParameter(cquery, alias);
            // 设置参数
            setParameter(query, args);
            setParameter(cquery, args);
            Pager<T> pages = new Pager<T>();
            setPagers(query, pages);
            List<T> datas = query.getResultList();
            pages.setRows(datas);
            long total = (Long) cquery.getSingleResult();
            pages.setTotal(total);
            entityManager.close();
            return pages;
        } catch (Exception e) {
            return null;
        }

    }

    private String getCountJpql(String jpql, boolean isJpql) {
        String e = jpql.substring(jpql.indexOf("from"));
        String c = "select count(*) " + e;
        if (isJpql)
            c = c.replaceAll("fetch", "");
        return c;
    }

    private void setPagers(Query query, Pager pages) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageNumber = SystemContext.getPageNumber();
        Integer pageOffset = 0;
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0)
            pageSize = 20;
        if (pageNumber == 1) {
            pageOffset = 0;
        } else {
            pageOffset = pageNumber * pageSize - pageSize;
        }
        pages.setPageSize(pageSize);
        pages.setPageNumber(pageNumber);
        pages.setPageOffset(pageOffset);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
    }

    private boolean setScalar(NativeQueryImpl query, Class<?> c) {
        if (c == null)
            return false;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(String.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.STRING);
                } else if (field.getType().equals(long.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.LONG);
                } else if (field.getType().equals(Timestamp.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
                } else if (field.getType().equals(Double.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
                } else if (field.getType().equals(Integer.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.INTEGER);
                } else if (field.getType().equals(int.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.INTEGER);
                } else if (field.getType().equals(Date.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.DATE);
                } else if (field.getType().equals(Float.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.FLOAT);
                } else if (field.getType().equals(Boolean.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
                } else if (field.getType().equals(BigInteger.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.BIG_INTEGER);
                } else if (field.getType().equals(BigDecimal.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.BIG_DECIMAL);
                } else if (field.getType().equals(Time.class)) {
                    query.addScalar(field.getName(), StandardBasicTypes.TIME);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    private <N extends Object> List<N> listToBeanBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        try {
            NativeQueryImpl query = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class);
            setAliasParameter(query, alias);
            setParameter(query, args);
            if (hasEntity) {
                query.addEntity(clz);
            } else {
                setScalar(query, clz);
                query.setResultTransformer(Transformers.aliasToBean(clz));
            }
            List<N> list = query.getResultList();
            entityManager.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    private <N extends Object> Pager<N> findToBeanBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        try {
            String sql1 = initSort(sql);
            String cq = getCountJpql(sql, false);
            NativeQueryImpl sq = entityManager.createNativeQuery(sql1).unwrap(NativeQueryImpl.class);
            NativeQueryImpl cquery = entityManager.createNativeQuery(cq).unwrap(NativeQueryImpl.class);
            setAliasParameter(sq, alias);
            setAliasParameter(cquery, alias);
            setParameter(sq, args);
            setParameter(cquery, args);
            Pager<N> pages = new Pager<N>();
            setPagers(sq, pages);
            if (hasEntity) {
                sq.addEntity(clz);
            } else {
                setScalar(sq, clz);
                sq.setResultTransformer(Transformers.aliasToBean(clz));
            }
            List<N> datas = (List<N>) sq.getResultList();
            if (datas != null && datas.size() > 0)
                pages.setRows(datas);
            BigDecimal totals = (BigDecimal) cquery.getSingleResult();
            pages.setTotal(totals.longValue());
            entityManager.close();
            return pages;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 按原生SQL执行executeUpdate
     *
     * @param sql
     * @return Object
     */
    @Override
    public Object executeBySql(String sql) {
        return this.executeBySql(sql, null, null);
    }

    /**
     * 按原生SQL执行executeUpdate
     *
     * @param sql
     * @param arg
     * @return Object
     */
    @Override
    public Object executeBySql(String sql, Object arg) {
        return this.executeBySql(sql, new Object[]{arg}, null);
    }

    /**
     * 按原生SQL执行executeUpdate
     *
     * @param sql
     * @param args
     * @return
     */
    @Override
    public Object executeBySql(String sql, Object[] args) {
        return this.executeBySql(sql, args, null);
    }


    /**
     * 按原生SQL执行executeUpdate
     *
     * @param sql
     * @param alias
     * @return
     */
    @Override
    public Object executeByAliasSql(String sql, Map<String, Object> alias) {
        {
            return this.executeBySql(sql, null, alias);
        }
    }

    /**
     * 按原生SQL执行executeUpdate
     *
     * @param sql
     * @param args
     * @return Object
     */
    private Object executeBySql(String sql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            setParameter(query, args);
            setAliasParameter(query, alias);
            Object o = query.executeUpdate();
            entityManager.close();
            return o;
        } catch (Exception e) {
            return null;
        }


    }


    /**
     * 执行executeUpdate
     *
     * @param jpql
     * @return Object
     */
    @Override
    public Object executeByJpql(String jpql) {
        return this.executeByJpql(jpql, null, null);
    }

    /**
     * 执行executeUpdate
     *
     * @param jpql
     * @param arg
     * @return Object
     */
    @Override
    public Object executeByJpql(String jpql, Object arg) {
        return this.executeByJpql(jpql, new Object[]{arg}, null);
    }

    /**
     * 执行executeUpdate
     *
     * @param jpql
     * @param args
     * @return
     */
    @Override
    public Object executeByJpql(String jpql, Object[] args) {
        return this.executeByJpql(jpql, args, null);
    }

    /**
     * 执行executeUpdate
     *
     * @param jpql
     * @param alias
     * @return
     */
    @Override
    public Object executeByAliasJpql(String jpql, Map<String, Object> alias) {
        return this.executeByJpql(jpql, null, alias);
    }


    /**
     * 执行executeUpdate
     *
     * @param jpql
     * @param args
     * @return Object
     */
    private Object executeByJpql(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createQuery(jpql);
            setParameter(query, args);
            setAliasParameter(query, alias);
            int o = query.executeUpdate();
            entityManager.close();
            return o;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param clz
     * @param hasEntity
     * @return Object
     */
    public Object queryToObjectBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param arg
     * @param clz
     * @param hasEntity
     * @return Object
     */
    public Object queryToObjectBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param args
     * @param clz
     * @param hasEntity
     * @return Object
     */
    public Object queryToObjectBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, args, null, clz, hasEntity);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param alias
     * @param clz
     * @param hasEntity boolean
     * @return Object
     */
    public Object queryToObjectByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, null, alias, clz, hasEntity);
    }

    private Object queryToObjectBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz,
                                      boolean hasEntity) {
        try {
            NativeQueryImpl query = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class);
            setAliasParameter(query, alias);
            setParameter(query, args);
            if (hasEntity) {
                query.addEntity(clz);
            } else {
                setScalar(query, clz);
                query.setResultTransformer(Transformers.aliasToBean(clz));
            }
            Object o = query.getSingleResult();
            entityManager.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @return map
     */
    @Override
    public List<Map> listToMapBySql(String sql) {
        return this.listToMapBySql(sql, null, null);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param arg
     * @return map
     */
    @Override
    public List<Map> listToMapBySql(String sql, Object arg) {
        return this.listToMapBySql(sql, new Object[]{arg}, null);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param args
     * @return map
     */
    @Override
    public List<Map> listToMapBySql(String sql, Object[] args) {
        return this.listToMapBySql(sql, args, null);
    }

    /**
     * 按原生SQL查询
     *
     * @param sql
     * @param alias
     * @return map
     */
    @Override
    public List<Map> listToMapByAliasSql(String sql, Map<String, Object> alias) {
        return this.listToMapBySql(sql, null, alias);
    }

    private List<Map> listToMapBySql(String sql, Object[] args, Map<String, Object> alias) {
        try {
            NativeQueryImpl query = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class);
            setAliasParameter(query, alias);
            setParameter(query, args);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map> list = query.getResultList();
            entityManager.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按原生SQL查询分页
     *
     * @param sql
     * @return
     */
    public Pager<Map> findToMapBySql(String sql) {
        return this.findToMapBySql(sql, null, null);
    }

    /**
     * 按原生SQL查询分页
     *
     * @param sql
     * @param arg
     * @return
     */
    public Pager<Map> findToMapBySql(String sql, Object arg) {
        return this.findToMapBySql(sql, new Object[]{arg}, null);
    }

    /**
     * 按原生SQL查询分页
     *
     * @param sql
     * @param args
     * @return
     */
    public Pager<Map> findToMapBySql(String sql, Object[] args) {
        return this.findToMapBySql(sql, args, null);
    }

    /**
     * 按原生SQL查询分页
     *
     * @param sql
     * @param alias
     * @return
     */
    public Pager<Map> findToMapByAliasSql(String sql, Map<String, Object> alias) {
        return this.findToMapBySql(sql, null, alias);
    }

    private Pager<Map> findToMapBySql(String sql, Object[] args, Map<String, Object> alias) {
        try {
            String sql1 = initSort(sql);
            String cq = getCountJpql(sql, false);
            NativeQueryImpl sq = entityManager.createNativeQuery(sql1).unwrap(NativeQueryImpl.class);
            NativeQueryImpl cquery = entityManager.createNativeQuery(cq).unwrap(NativeQueryImpl.class);
            setAliasParameter(sq, alias);
            setAliasParameter(cquery, alias);
            setParameter(sq, args);
            setParameter(cquery, args);
            Pager<Map> pages = new Pager<Map>();
            setPagers(sq, pages);
            sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map> datas = (List<Map>) sq.getResultList();
            if (datas != null && datas.size() > 0)
                pages.setRows(datas);
            BigDecimal totals = (BigDecimal) cquery.getSingleResult();
            pages.setTotal(totals.longValue());
            entityManager.close();
            return pages;
        } catch (Exception e) {
            return null;
        }

    }

    private String getIdField(){
        String idField = entityManager.getMetamodel().entity(entityClass).getId(idClass).getName();
        return idField;
    }

}
