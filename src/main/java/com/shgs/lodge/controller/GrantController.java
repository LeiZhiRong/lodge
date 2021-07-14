package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.service.IRoleInfoService;
import com.shgs.lodge.service.IUserInfoService;
import com.shgs.lodge.service.IUserRoleService;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 角色授权视图层-View接口
 */
@RestController
@RequestMapping("/grant/")
@Scope("prototype")
@AuthClass("login")
public class GrantController {


    private IUserRoleService userRoleService;


    private IUserInfoService userInfoService;


    private IRoleInfoService roleInfoService;

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setRoleInfoService(IRoleInfoService roleInfoService) {
        this.roleInfoService = roleInfoService;
    }


    /**
     * 授权管理首页
     *
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_GRANT")
    @GetMapping("index")
    public ModelAndView index() throws JsonProcessingException {
        return new ModelAndView("grant/index");
    }

    /**
     * 获取角色列表
     *
     * @param keyword
     * @return
     */
    @AuthMethod(role = "ROLE_GRANT")
    @RequestMapping("list")
    public List<SelectJson> list(String keyword) {
         return userInfoService.listToSelectJson(keyword);
    }

    /**
     * 按用户和关键字获取未授权角色列表
     *
     * @param keyword 关键字
     * @return arrayJson
     */
    @AuthMethod(role = "ROLE_GRANT")
    @RequestMapping("getRoleInfo")
    public List<SelectJson> getRoleInfo(String user_id, String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String bookSet = user.isAdmin() ? null : user.getBookSet();
        //获取已授权角色IDS
        List<String> role_ids = userRoleService.getUserRoleID(user_id, bookSet);
        //获取未授权角色列表
        return roleInfoService.listRoleInfo(role_ids, bookSet, keyword);
    }

    /**
     * 按用户获取已授权角色列表
     *
     * @param user_id 用户ID
     * @param keyword 关键字
     * @return
     */
    @AuthMethod(role = "ROLE_GRANT")
    @RequestMapping("getUserGrant")
    public List<SelectJson> getUserGrant(String user_id, String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String bookSet = user.isAdmin() ? null : user.getBookSet();
        return userRoleService.listRoleInfo(user_id, bookSet, keyword);
    }

    /**
     * 保存用户角色
     *
     * @param role_ids 角色ID，多个ID以","隔开
     * @param user_id  用户ID
     * @return
     */
    @AuthMethod(role = "ROLE_GRANT")
    @PostMapping("savaUserGrant")
    public Message savaUserGrant(String role_ids, String user_id) {
        return userRoleService.saveUserRole(user_id, role_ids);
    }

    /**
     * 移除用户角色
     *
     * @param role_ids 角色ID，多个ID以","隔开
     * @param user_id  用户ID
     * @return
     */
    @AuthMethod(role = "ROLE_GRANT")
    @PostMapping("delUserGrant")
    public Message delUserGrant(String role_ids, String user_id) {
        return userRoleService.batchDeleteUserRole(role_ids, user_id);
    }

}
