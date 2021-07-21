package com.shags.lodge.business.dao.basic;


import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SystemContext;
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
 * BusinessBaseDao
 * @param <T> 实体类
 * @param <ID> ID类型
 */
@SuppressWarnings({"unused", "ConstantConditions", "EqualsBetweenInconvertibleTypes", "unchecked", "RedundantCast", "rawtypes", "UnusedReturnValue", "DuplicatedCode"})
public class BusinessBaseDAO<T, ID> implements IBusinessBaseDAO<T> {

    private static final int BATCH_SIZE = 500;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> entityClass = null;

    private Class<ID> idClass = null;

    @PersistenceContext(unitName = "businessEntityManagerFactory")
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
            Type[] genericTypifies = parameterizedType.getActualTypeArguments();
            entityClass = (Class<T>) genericTypifies[0];
            idClass = (Class<ID>) genericTypifies[1];
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

    @Override
    public T add(T entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(T entity) {
        try {
            entityManager.merge(entity);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(T entity) {
        try {
            entityManager.remove(entityManager.merge(entity));
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Serializable id) {
        try {
            Query query = entityManager.createQuery("delete from " + entityClass.getName() + " b where b." + getIdField() + " =:id ");
            query.setParameter("id", id);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<T> list(String jpql) {
        return this.list(jpql, null, null);
    }

    @Override
    public List<T> list(String jpql, Object arg) {
        return this.list(jpql, new Object[]{arg}, null);
    }

    @Override
    public List<T> list(String jpql, Object[] args) {
        return this.list(jpql, args, null);
    }

    @Override
    public List<T> listByAlias(String jpql, Map<String, Object> alias) {
        return this.list(jpql, null, alias);
    }

    @Override
    public Object queryObject(String jpql) {
        return this.queryObject(jpql, null, null);
    }

    @Override
    public Object queryObject(String jpql, Object arg) {
        return this.queryObject(jpql, new Object[]{arg}, null);
    }

    @Override
    public Object queryObject(String jpql, Object[] args) {
        return this.queryObject(jpql, args, null);
    }

    @Override
    public Object queryObjectByAlias(String jpql, Map<String, Object> alias) {
        return this.queryObject(jpql, null, alias);
    }

    @Override
    public Pager<T> find(String jpql) {
        return this.find(jpql, null, null);
    }

    @Override
    public Pager<T> find(String jpql, Object arg) {
        return this.find(jpql, new Object[]{arg}, null);
    }

    @Override
    public Pager<T> find(String jpql, Object[] args) {
        return this.find(jpql, args, null);
    }

    @Override
    public Pager<T> find(String jpql, Map<String, Object> alias) {
        return this.find(jpql, null, alias);
    }

    /**
     * sql分页查询
     * @param sql sql语句
     * @param clz 转换类class
     * @param hasEntity 是否转换 [true|false]
     * @param <N> 转换类
     * @return pager
     */
    public <N> Pager<N> findToBeanBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * sql分页查询
     * @param sql sql语句[arg1=?0]
     * @param arg 参数[Object]
     * @param clz  转换类class
     * @param hasEntity 是否转换 [true|false]
     * @param <N> 转换类
     * @return pager
     */
    public <N> Pager<N> findToBeanBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }


    /**
     * sql分页查询
     * @param sql  sql语句[arg1=?0,arg2=?1...]
     * @param args 参数[new Object[]{arg1,arg2...}]
     * @param clz 转换类class
     * @param hasEntity 是否转换 [true|false]
     * @param <N> 转换类
     * @return pager
     */
    public <N> Pager<N> findToBeanBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, args, null, clz, hasEntity);
    }


    /**
     * sql分页查询
     * @param sql  sql语句[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数[Map<String, Object> alias]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @param <N> 转换类
     * @return pager
     */
    public <N> Pager<N> findToBeanByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        return this.findToBeanBySql(sql, null, alias, clz, hasEntity);
    }

    @Override
    public boolean batchSave(List<T> t) {
        Iterator<T> iterator = t.listIterator();
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

    @Override
    public boolean batchUpdate(List<T> t) {
        Iterator<T> iterator = t.listIterator();
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
     * sql列表查询
     * @param sql  sql语句
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @param <N> 转换类
     * @return list
     */
    public <N> List<N> listToBeanBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * sql列表查询
     * @param sql  sql语句[arg1=?0]
     * @param arg 参数[Object]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @param <N> 转换类
     * @return list
     */
    public <N> List<N> listToBeanBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    /**
     * sql列表查询
     * @param sql  sql语句[arg1=?0,arg2=?1...]
     * @param args 参数[new Object[]{arg1,arg2...}]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @param <N> 转换类
     * @return list
     */
    public <N> List<N> listToBeanBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.listToBeanBySql(sql, args, null, clz, hasEntity);
    }

    /**
     * sql列表查询
     * @param sql  sql语句[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数[Map<String, Object> alias]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @param <N> 转换类
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            return null;
        }
    }

    private Pager<T> find(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            String jpql1 = initSort(jpql);
            String cq = getCountJpql(jpql, true);
            Query jquery = entityManager.createQuery(cq);
            Query query = entityManager.createQuery(jpql1);
            // 设置别名参数
            setAliasParameter(query, alias);
            setAliasParameter(jquery, alias);
            // 设置参数
            setParameter(query, args);
            setParameter(jquery, args);
            Pager<T> pages = new Pager<>();
            setPagers(query, pages);
            List<T> dates = query.getResultList();
            pages.setRows(dates);
            long total = (Long) jquery.getSingleResult();
            pages.setTotal(total);
            entityManager.close();
            return pages;
        } catch (Exception e) {
            logger.error(e.getMessage());
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

    @SuppressWarnings("rawtypes")
    private void setPagers(Query query, Pager pages) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageNumber = SystemContext.getPageNumber();
        int pageOffset;
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

    private <N> List<N> listToBeanBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
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
            logger.error(e.getMessage());
            return null;
        }
    }

    private <N> Pager<N> findToBeanBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
        try {
            String sql1 = initSort(sql);
            String cq = getCountJpql(sql, false);
            NativeQueryImpl sq = entityManager.createNativeQuery(sql1).unwrap(NativeQueryImpl.class);
            NativeQueryImpl jquery = entityManager.createNativeQuery(cq).unwrap(NativeQueryImpl.class);
            setAliasParameter(sq, alias);
            setAliasParameter(jquery, alias);
            setParameter(sq, args);
            setParameter(jquery, args);
            Pager<N> pages = new Pager<>();
            setPagers(sq, pages);
            if (hasEntity) {
                sq.addEntity(clz);
            } else {
                setScalar(sq, clz);
                sq.setResultTransformer(Transformers.aliasToBean(clz));
            }
            List<N> dates = (List<N>) sq.getResultList();
            if (dates != null && dates.size() > 0)
                pages.setRows(dates);
            BigDecimal totals = (BigDecimal) jquery.getSingleResult();
            pages.setTotal(totals.longValue());
            entityManager.close();
            return pages;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    @Override
    public Object executeBySql(String sql) {
        return this.executeBySql(sql, null, null);
    }

    @Override
    public Object executeBySql(String sql, Object arg) {
        return this.executeBySql(sql, new Object[]{arg}, null);
    }


    @Override
    public Object executeBySql(String sql, Object[] args) {
        return this.executeBySql(sql, args, null);
    }

    @Override
    public Object executeByAliasSql(String sql, Map<String, Object> alias) {
        {
            return this.executeBySql(sql, null, alias);
        }
    }

    private Object executeBySql(String sql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            setParameter(query, args);
            setAliasParameter(query, alias);
            Object o = query.executeUpdate();
            entityManager.close();
            return o;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }


    }


    @Override
    public Object executeByJpql(String jpql) {
        return this.executeByJpql(jpql, null, null);
    }

    @Override
    public Object executeByJpql(String jpql, Object arg) {
        return this.executeByJpql(jpql, new Object[]{arg}, null);
    }

    @Override
    public Object executeByJpql(String jpql, Object[] args) {
        return this.executeByJpql(jpql, args, null);
    }

    @Override
    public Object executeByAliasJpql(String jpql, Map<String, Object> alias) {
        return this.executeByJpql(jpql, null, alias);
    }


    private Object executeByJpql(String jpql, Object[] args, Map<String, Object> alias) {
        try {
            Query query = entityManager.createQuery(jpql);
            setParameter(query, args);
            setAliasParameter(query, alias);
            int o = query.executeUpdate();
            entityManager.close();
            return o;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    /**
     * sql查询对昂
     * @param sql  sql语句
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
      * @return Object
     */
    public Object queryToObjectBySql(String sql, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, null, null, clz, hasEntity);
    }

    /**
     * sql查询对昂
     * @param sql  sql语句[arg1=?0]
     * @param arg 参数[Object]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @return Object
     */
    public Object queryToObjectBySql(String sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, new Object[]{arg}, null, clz, hasEntity);
    }

    /**
     * sql查询对昂
     * @param sql  sql语句[arg1=?0,arg2=?1...]
     * @param args 参数[new Object[]{arg1,arg2...}]
     * @param clz 转换类class
     * @param hasEntity  是否转换 [true|false]
     * @return Object
     */
    public Object queryToObjectBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.queryToObjectBySql(sql, args, null, clz, hasEntity);
    }

    /**
     * sql查询对昂
     * @param sql  sql语句[arg1=:arg1,arg2=:arg2...]
     * @param alias 参娄[Map<String, Object> alias]
     * @param clz 转换类class
     * @param hasEntity 是否转换 [true|false]
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
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map> listToMapBySql(String sql) {
        return this.listToMapBySql(sql, null, null);
    }

    @Override
    public List<Map> listToMapBySql(String sql, Object arg) {
        return this.listToMapBySql(sql, new Object[]{arg}, null);
    }

    @Override
    public List<Map> listToMapBySql(String sql, Object[] args) {
        return this.listToMapBySql(sql, args, null);
    }

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
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * sql分页查询
     * @param sql sql语句
     * @return pager
     */
    public Pager<Map> findToMapBySql(String sql) {
        return this.findToMapBySql(sql, null, null);
    }

    /**
     * sql分页查询
     * @param sql sql语句[arg1=?0]
     * @param arg 参数[Object]
     * @return pager
     */
    public Pager<Map> findToMapBySql(String sql, Object arg) {
        return this.findToMapBySql(sql, new Object[]{arg}, null);
    }

    /**
     * sql分页查询
     * @param sql sql语句[arg1=?0,arg2=?1...]
     * @param args 参数[new Object[]{arg1,arg2...}]
     * @return pager
     */
    public Pager<Map> findToMapBySql(String sql, Object[] args) {
        return this.findToMapBySql(sql, args, null);
    }


    /**
     * sql分页查询
     * @param sql sql语句[arg1=:arg1,arg2=:arg2...]
     * @param alias 参数[Map<String, Object> alias]
     * @return pager
     */
    public Pager<Map> findToMapByAliasSql(String sql, Map<String, Object> alias) {
        return this.findToMapBySql(sql, null, alias);
    }

    private Pager<Map> findToMapBySql(String sql, Object[] args, Map<String, Object> alias) {
        try {
            String sql1 = initSort(sql);
            String cq = getCountJpql(sql, false);
            NativeQueryImpl sq = entityManager.createNativeQuery(sql1).unwrap(NativeQueryImpl.class);
            NativeQueryImpl jquery = entityManager.createNativeQuery(cq).unwrap(NativeQueryImpl.class);
            setAliasParameter(sq, alias);
            setAliasParameter(jquery, alias);
            setParameter(sq, args);
            setParameter(jquery, args);
            Pager<Map> pages = new Pager<>();
            setPagers(sq, pages);
            sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map> dates = (List<Map>) sq.getResultList();
            if (dates != null && dates.size() > 0)
                pages.setRows(dates);
            BigDecimal totals = (BigDecimal) jquery.getSingleResult();
            pages.setTotal(totals.longValue());
            entityManager.close();
            return pages;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    private String getIdField() {
        return entityManager.getMetamodel().entity(entityClass).getId(idClass).getName();
    }

}
