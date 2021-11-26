package com.shags.lodge.service.primary;

import com.shags.lodge.primary.entity.Attachment;
import com.shags.lodge.util.Message;

import java.util.List;
import java.util.Map;

/**
 * @author yglei
 * @classname IAttachmentService
 * @description 附件管理业务处理接口层
 * @date 2021-11-22 15:33
 */
public interface IAttachmentService {

    /**
     * 保存对象
     * @param attachment 附件对象
     * @return message
     */
    Message saveAttachment(Attachment attachment);

    /**
     * 批量保存对象
     * @param list 附件集合
     * @return list
     */
    Message batchSaveAttachment(List<Attachment> list);

    /**
     * 按ID删除附件
     * @param id
     */
    Message deleteAttachmentById(String id);

    /**
     * 删除表单所有附件
     * @param tableId 表单ID
     * @param tableName 表单名称
     */
    Message deleteAttachmentByTableIdAndTableName(String  tableId, String tableName);

    /**
     * 批量删除附件
     * @param ids 附件id,多外附件以“,”隔开
     */
    Message batchDeleteByIds(String ids);

    /**
     * 按ID获取对象
     * @param id
     * @return Object
     */
    Attachment load(String id);

    /**
     * 获取表单附件
     * @param tableId 表单ID
     * @param tableName 表单名称
     * @return list
     */
    List<Attachment> listByTopic(String  tableId, String tableName);

    /**
     * 自定义查询
     * @param jpql jpql语句
     * @param args 查询条件
     * @return list
     */
    List<Attachment> listAttachment(String jpql, Object[] args);

    /**
     * 自定义查询
     * @param jpql jpql语句
     * @param alias 查询条件
     * @return list
     */
    List<Attachment> listAttachmentByAlias(String jpql, Map<String, Object> alias);

}
