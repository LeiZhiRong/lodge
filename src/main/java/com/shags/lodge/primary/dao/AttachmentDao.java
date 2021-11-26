package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.Attachment;
import org.springframework.stereotype.Repository;

/**
 * @author yglei
 * @classname AttachmentDao
 * @description 附件管理持久层实现类
 * @date 2021-11-22 15:29
 */
@Repository("attachmentDao")
public class AttachmentDao extends LodgeBaseDAO<Attachment, String>  implements IAttachmentDao {

}
