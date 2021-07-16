package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.TableHeader;
import com.shags.lodge.util.CmsUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("tableHeaderDao")
public class TableHeaderDao extends LodgeBaseDAO<TableHeader,String> implements ITableHeaderDao {

    @Override
    public int getMaxOrderByParent(String user_id, String table_name) {
        String hql = "select max(c.orders) from TableHeader c where c.user_id='" + user_id + "' and c.table_name ='" + table_name + "'";
        Object obj = this.queryObject(hql);
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public void updateSort(List<String> ids) {
        int index = 1;
        String hql = "update  TableHeader c set c.orders =?0 where c.id =?1";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public List<TableHeader> listTableHeader(String user_id, String table_name) {
        Map<String, Object> alias = new HashMap<>();
        alias.put("user_id", user_id);
        alias.put("table_name", table_name);
        return super.listByAlias(" from TableHeader t where  t.user_id =:user_id  and t.table_name =:table_name  order by t.orders asc ", alias);
    }

    @Override
    public boolean deleteTableHeader(String user_id, String table_name) {
        Map<String, Object> alias = new HashMap<>();
        alias.put("user_id",user_id);
        alias.put("table_name",table_name);
        Object o=super.executeByAliasJpql("delete from TableHeader t where t.table_name =:table_name and t.user_id =:user_id ", alias);
        return o != null;
    }

    @Override
    public boolean batchSaveTableHeader(List<TableHeader> tableHeaders) {
        return super.batchSave(tableHeaders);
    }

    @Override
    public boolean batchUpdateTableHeader(List<TableHeader> tableHeaders) {
        return super.batchUpdate(tableHeaders);
    }

    @Override
    public int batchDeleteTableHeader(String[] ids) {
        if (ids != null && ids.length>0) {
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids",  Arrays.asList(ids));
            Object o=super.executeByAliasJpql("delete from TableHeader t where t.id in( :ids) ", alias);
            if(o!=null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public TableHeader queryTableHeader(String id) {
        return (TableHeader) super.queryObject("from TableHeader t where t.id =?0", id);
    }

    @Override
    public boolean addHeaderColumns(String user_id, String table_name, String clas) {
        List<TableHeader> temp = new ArrayList<>();
        List<HeaderColumns> columns = CmsUtils.getHeaderColumns(clas);
        if (columns != null && columns.size() > 0) {
            for (HeaderColumns mast : columns) {
                int orders = this.getMaxOrderByParent(user_id, table_name);
                TableHeader tableHeader = new TableHeader(mast);
                tableHeader.setUser_id(user_id);
                tableHeader.setTable_name(table_name);
                tableHeader.setOrders(orders + 1);
                tableHeader.setStatus(mast.getStatus());
                temp.add(tableHeader);
            }
        }
        if (temp.size() > 0) {
            return super.batchSave(temp);
        } else {
            return false;
        }

    }

}


