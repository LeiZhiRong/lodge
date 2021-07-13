package com.shgs.lodge.jwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shgs.lodge.dto.MenuShortcut;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * SpringSecurity用户的实体
 * 注意:这里必须要实现UserDetails接口
 * @Author Sans
 * @CreateTime 2019/10/1 20:23
 */

public class SelfUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 登录账号
	 */
	private String account;

	/**
	 * 部门编号
	 */
	private String deptID;

	/**
	 * 账套编号
	 */
	private String bookSet;

	/**
	 * 管理部门
	 */
	private String manageDept;

	/**
	 * 结算部门
	 */
	private String balanceDept;

	/**
	 * 密码
	 */
	@JsonIgnore
	private String password;

	/**
	 * 状态:NORMAL正常  PROHIBIT禁用
	 */
	private String status;

	/**
	 * 是否为管理用户
	 */
	private boolean isAdmin = false;

	/**
	 * 账户是否有效
	 */
	private boolean isEnabled = true;

	/**
	 * 菜单IDS
	 */
	private List<String> menuIds;

	/**
	 * 角色列表
	 */
	private List<String> roles;

	/**
	 * 导航菜单
	 */
	private List<TreeJson> menuInfoList;

	/**
	 * 快捷菜单
	 */
	private List<MenuShortcut> menuShortcutList;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getBookSet() {
		return bookSet;
	}

	public void setBookSet(String bookSet) {
		this.bookSet = bookSet;
	}

	public String getManageDept() {
		return manageDept;
	}

	public void setManageDept(String manageDept) {
		this.manageDept = manageDept;
	}

	public String getBalanceDept() {
		return balanceDept;
	}

	public void setBalanceDept(String balanceDept) {
		this.balanceDept = balanceDept;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public List<String> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<TreeJson> getMenuInfoList() {
		return menuInfoList;
	}

	public void setMenuInfoList(List<TreeJson> menuInfoList) {
		this.menuInfoList = menuInfoList;
	}

	public List<MenuShortcut> getMenuShortcutList() {
		return menuShortcutList;
	}

	public void setMenuShortcutList(List<MenuShortcut> menuShortcutList) {
		this.menuShortcutList = menuShortcutList;
	}

	public SelfUserEntity() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public SelfUserEntity(UserInfo userInfo){
		this.setUserId(userInfo.getId());
		this.setUsername(userInfo.getUserName());
		this.setPassword(userInfo.getLoginPassword());
		this.setAccount(userInfo.getLoginAccount());
		this.setManageDept(userInfo.getDeptID());
		this.setBalanceDept(userInfo.getSettID());
		this.setStatus(userInfo.getStatus()==1?"T":"F");
		this.setAdmin("T".equals(userInfo.getManager()));
	}


}