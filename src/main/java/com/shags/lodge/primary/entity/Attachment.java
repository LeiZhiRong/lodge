package com.shags.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 附件
 * @author Administrator
 *
 */
@Entity
@Table(name = "atta_chment")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class Attachment {

	/**
	 * 关键字
	 */
	private String id;

	/**
	 * 上传之后的名称
	 */
	private String newName;	
	
	/**
	 * 原始名称
	 */
	private String oldName;

	/**
	 * 后缀名
	 */
	private String suffix;

	/**
	 * 大小
	 */
	private String filesize;

	/**
	 * 物理路径
	 */
	private String savePath;

	/**
	 * 附件访问地址
	 */
	private String annexUrl;

	/**
	 * 附件所属表
	 */
	private String tableName;
	
	/**
	 * 附件所属表id
	 */
	private String tableId;
	
	/**
	 * 上传时间
	 */
	private Timestamp createTime;
	
	/**
	 * 创建者账号
	 */
	private String userAccount;
	
	/**
	 * 所属账套
	 */
	private String bookSet;

	@Id
	@GeneratedValue(generator = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getAnnexUrl() {
		return annexUrl;
	}

	public void setAnnexUrl(String annexUrl) {
		this.annexUrl = annexUrl;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getBookSet() {
		return bookSet;
	}

	public void setBookSet(String bookSet) {
		this.bookSet = bookSet;
	}

	public Attachment() {
		super();
	}


}
