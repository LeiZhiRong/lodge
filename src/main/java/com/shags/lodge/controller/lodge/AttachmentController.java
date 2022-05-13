package com.shags.lodge.controller.lodge;

import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.primary.entity.Attachment;
import com.shags.lodge.service.primary.IAttachmentService;
import com.shags.lodge.util.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * @author yglei
 * @classname AttachmentController
 * @description 附件管理示图接口层
 * @date 2021-11-22 17:08
 */
@RestController
@RequestMapping(value = "/attachment/")
@Scope("prototype")
@AuthClass("login")
public class AttachmentController {

    @Autowired
    public void setAttachmentService(IAttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    private IAttachmentService attachmentService;


    @AuthMethod
    @RequestMapping("list")
    public List<Attachment> list(String tableId, String tableName, HttpSession session) {
        return null;
    }


    /**
     * 保存附件数据
     * @param tableId 表单ID
     * @param tableName 表单名称
     * @param session session
     * @return Message
     */
//    @AuthMethod
//    @RequestMapping("save")
//    public Message save(String tableId, String tableName, HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("user");
//            List<Attachment> attachment = (List<Attachment>) session.getAttribute("attachment");
//            List<Attachment> list=new ArrayList<>();
//            if (attachment != null) {
//                if (attachment.size() > 0) {
//                    for (Attachment mast : attachment) {
//                        Attachment att = new Attachment();
//                        att.setFilesize(mast.getFilesize());
//                        att.setTableId(tableId);
//                        att.setTableName(tableName);
//                        att.setBookSet(user.getBookSet());
//                        att.setNewName(mast.getNewName());
//                        att.setOldName(mast.getOldName());
//                        att.setSavePath(mast.getSavePath());
//                        att.setAnnexUrl(mast.getAnnexUrl());
//                        att.setSuffix(mast.getSuffix());
//                        att.setAnnexUrl(user.getAccount());
//                        att.setCreateTime(BeanUtil.dateToTimestamp(new Date()));
//                        list.add(att);
//                    }
//                }
//            }
//            if(list.size()>0){
//                Message msg=attachmentService.batchSaveAttachment(list);
//                if(msg.getCode()==1){
//                    session.setAttribute("attachment", new ArrayList<Attachment>());
//                }
//                return msg;
//            }else{
//               return new Message(0,"无数据可以保存！");
//            }
//
//        } catch (Exception e) {
//            return new Message(0, e.getMessage());
//        }
//
//    }

    /**
     * 删除附件
     * @param id 关键字
     * @return Message
     */
    @AuthMethod
    @RequestMapping(value = "del/{id}")
    public Message delAttachment(@PathVariable String id) {
        try {
            if (StringUtils.isNotEmpty(id)) {
                Attachment attachment = attachmentService.load(id);
                if (attachment != null) {
                    File file = new File(attachment.getSavePath());
                    if (file.exists())
                        file.delete();
                    attachmentService.deleteAttachmentById(id);
                }
                return new Message(1, "删除成功");
            } else {
                return new Message(0, "请选择附件，然后重试！");
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }



}
