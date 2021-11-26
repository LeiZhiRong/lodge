package com.shags.lodge.dto;

import java.sql.Timestamp;

/**
 * @author yglei
 * @classname AttachmentDto
 * @description TODO
 * @date 2021-11-22 17:15
 */
public class AttachmentDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 扩展名
     */
    private String suffix;

    /**
     * 附件大小
     */
    private String filesize;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件所属表
     */
    private String tName;

    /**
     * 附件所属表id
     */
    private String tId;

    /**
     * 上传时间
     */
    private Timestamp createTime;





}
