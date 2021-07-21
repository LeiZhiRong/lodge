package com.shags.lodge.service.primary;

import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.primary.dao.ITableHeaderDao;
import com.shags.lodge.primary.entity.TableHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("tableHeaderService")
public class TableHeaderService implements ITableHeaderService {

    private ITableHeaderDao tableHeaderDao;

    @Autowired
    public void setTableHeaderDao(ITableHeaderDao tableHeaderDao) {
        this.tableHeaderDao = tableHeaderDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String ids) {
        if(ids!=null && !ids.isEmpty()){
            List<String> _ids= CmsUtils.string2Array(ids,",");
            tableHeaderDao.updateSort(_ids);
        }
    }

    @Override
    public List<TableHeader> listTableHeader(String user_id, String table_name) {
        return tableHeaderDao.listTableHeader(user_id,table_name);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchSaveTableHeader(List<TableHeader> tableHeaders) {
        Message msg=new Message(0,"保存失败");
        if(tableHeaderDao.batchSaveTableHeader(tableHeaders)){
            msg.setCode(1);
            msg.setMessage("保存成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchUpdateTableHeader(List<TableHeader> tableHeaders) {
        Message msg=new Message(0,"保存失败");
        if(tableHeaderDao.batchUpdateTableHeader(tableHeaders)){
            msg.setCode(1);
            msg.setMessage("保存成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteTableHeader(String[] ids) {
        Message msg=new Message(0,"删除失败");
        if(tableHeaderDao.batchDeleteTableHeader(ids)>0){
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public TableHeader queryTableHeader(String id) {
        return tableHeaderDao.queryTableHeader(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public List<HeaderColumns> listHeaderColumns(String user_id, String table_name, String clas) {
        List<TableHeader> dto = tableHeaderDao.listTableHeader(user_id, table_name);
        if (dto.size() > 0) {
            return new HeaderColumns().listHeaderColumns(dto);
        } else {
            List<HeaderColumns> temp = new ArrayList<>();
            List<HeaderColumns> columns = CmsUtils.getHeaderColumns(clas);
            assert columns != null;
            for (HeaderColumns mast : columns) {
                int orders = tableHeaderDao.getMaxOrderByParent(user_id, table_name);
                TableHeader tableHeader = new TableHeader(mast);
                tableHeader.setUser_id(user_id);
                tableHeader.setTable_name(table_name);
                tableHeader.setOrders(orders + 1);
                tableHeader.setStatus(mast.getStatus());
                TableHeader t = tableHeaderDao.add(tableHeader);
                if (t.getStatus() == 1)
                    temp.add(new HeaderColumns(t));
            }
            return temp;
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteTableHeader(String user_id, String table_name) {
        Message msg=new Message(0,"重置失败");
        if(tableHeaderDao.deleteTableHeader(user_id, table_name)){
            msg.setCode(1);
            msg.setMessage("重置成功");
        }
        return msg;
    }

}
