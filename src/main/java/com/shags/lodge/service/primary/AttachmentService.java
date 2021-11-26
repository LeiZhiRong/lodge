package com.shags.lodge.service.primary;

import com.shags.lodge.primary.dao.IAttachmentDao;
import com.shags.lodge.primary.entity.Attachment;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yglei
 * @classname AttachmentService
 * @description 附件管理业务接口实现类
 * @date 2021-11-22 15:50
 */
@Service("attachmentService")
public class AttachmentService implements IAttachmentService {

    private IAttachmentDao attachmentDao;

    @Autowired
    public void setAttachmentDao(IAttachmentDao attachmentDao) {
        this.attachmentDao = attachmentDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message saveAttachment(Attachment attachment) {
        Message msg=new Message(0,"上传失败");
        if(attachmentDao.add(attachment)!=null){
            msg.setCode(1);
            msg.setMessage("上传成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchSaveAttachment(List<Attachment> list) {
        Message msg=new Message(0,"上传失败");
        if(attachmentDao.batchSave(list)){
            msg.setCode(1);
            msg.setMessage("上传成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteAttachmentById(String id) {
        Message msg = new Message(0, "删除失败");
        if (attachmentDao.delete(id)) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteAttachmentByTableIdAndTableName(String tableId, String tableName) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        Message msg = new Message(0, "删除失败");
        if (StringUtils.isNotEmpty(tableId) && StringUtils.isNotEmpty(tableName)) {
            jpql.append(" delete from  Attachment a where a.tableId =:tableId and a.tableName =:tableName ");
            alias.put("tableId", tableId);
            alias.put("tableName", tableName);
            Object o = attachmentDao.executeByAliasJpql(jpql.toString(), alias);
            if (o != null) {
                msg.setCode(1);
                msg.setMessage("删除成功");
            }
        } else {
            msg.setMessage("参数错误");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteByIds(String ids) {
        Map<String, Object> alias = new HashMap<>();
        Message msg = new Message(0, "删除失败");
        if (StringUtils.isNotEmpty(ids)) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            String jpql = " delete from Attachment a where a.id in(:ids) ";
            alias.put("ids", _ids);
            Object o = attachmentDao.executeByAliasJpql(jpql, alias);
            if (o != null) {
                msg.setCode(1);
                msg.setMessage("删除成功");
            }
        } else {
            msg.setMessage("参数错误");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Attachment load(String id) {
        return attachmentDao.load(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Attachment> listByTopic(String tableId, String tableName) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        if (StringUtils.isNotEmpty(tableId) && StringUtils.isNotEmpty(tableName)) {
            jpql.append(" from  Attachment a where a.tableId =:tableId and a.tableName =:tableName ");
            alias.put("tableId", tableId);
            alias.put("tableName", tableName);
            return attachmentDao.listByAlias(jpql.toString(), alias);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Attachment> listAttachment(String jpql, Object[] args) {
        return attachmentDao.list(jpql, args);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Attachment> listAttachmentByAlias(String jpql, Map<String, Object> alias) {
        return attachmentDao.listByAlias(jpql, alias);
    }



}
