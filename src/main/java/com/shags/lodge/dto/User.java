package com.shags.lodge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shags.lodge.util.RSAUtils;
import com.shags.lodge.exception.exception.JsonException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

import static java.net.URLDecoder.decode;

/**
 * 权限认证-用户组
 */
public class User {

    private String id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 登录密码
     */
    @JsonIgnore
    private String password;

    /**
     * 职务
     */
    private String station;

    /**
     * 登录帐号
     */
     private String account;

    /**
     * 上次登录时间
     */
    private String endTime;

    /**
     * 上次登录地址
     */
    private String endAddress;

    /**
     * 管理部门
     */
    private String manageDept;
    /**
     * 管理处
     */
    private String balanceDept;

    /**
     * 用户对应的角色
     */
    @JsonIgnore
    private List<Role> roles;

    /**
     * 当前登录的帐套
     */
    private String bookSet;

    /**
     * 是否为系统管理员
     */
    @JsonIgnore
    private boolean admin;

    /**
     * 登录时间
     */
    private String loginTime;

    /**
     * 登录地址
     */
    private String loginAddress;

    /**
     * 注册单位名称
     */
    private String corpName;

    /**
     * 菜单IDS
     */
    @JsonIgnore
    private List<String> menuIds;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User() {
        super();
    }

    public User(String id, String userName, String password, List<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }


    public User(String password, HttpSession session) throws Exception {
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("loginKey");
            if (StringUtils.isNotEmpty(password)) {
                this.setPassword(decode(RSAUtils.decryptByPrivateKey(password, privateKey), "UTF-8"));
            }
        } catch (Exception e) {
            throw new JsonException(403,"登录密码解密失败，请使用正常通道登录系统");
        } finally {
            session.removeAttribute("loginKey");
        }
    }
}
