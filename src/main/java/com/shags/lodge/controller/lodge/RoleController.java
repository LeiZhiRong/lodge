package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.ListGroup;
import com.shags.lodge.dto.Role;
import com.shags.lodge.dto.User;
import com.shags.lodge.service.primary.IAccounInfoService;
import com.shags.lodge.service.primary.IPermInfoService;
import com.shags.lodge.service.primary.IRoleInfoService;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.RoleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/role/")
@Scope("prototype")
@AuthClass("login")
public class RoleController {


    private IRoleInfoService roleInfoService;


    private IPermInfoService permInfoService;


    private IAccounInfoService accounInfoService;

    @Autowired
    public void setRoleInfoService(IRoleInfoService roleInfoService) {
        this.roleInfoService = roleInfoService;
    }

    @Autowired
    public void setPermInfoService(IPermInfoService permInfoService) {
        this.permInfoService = permInfoService;
    }

    @Autowired
    public void setAccounInfoService(IAccounInfoService accounInfoService) {
        this.accounInfoService = accounInfoService;
    }

    /**
     * 角色管理首页
     *
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_ROLE")
    @GetMapping("index")
    public ModelAndView index() throws JsonProcessingException {
        return new ModelAndView("role/index");
    }

    /**
     * 角色编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_ROLE")
    @GetMapping("roleDialog")
    public ModelAndView roleDialog(String id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean hidden = true;
        boolean disabled = false;
        if (user.isAdmin())
            hidden = false;
        RoleInfo roleInfo = new RoleInfo();
        List<SelectJson> select = accounInfoService.listAccounInfoToSelectJson(null, null);
        select.add(0, new SelectJson("", "请选择..."));
        if (StringUtils.isNotEmpty(id)) {
            roleInfo = roleInfoService.getRoleInfo(id);
            List<Role> roleList = user.getRoles();
            for (Role role : roleList) {
                if (role.getId().equals(roleInfo.getId())) {
                    disabled = true;
                    break;
                }
            }
        } else {
            roleInfo.setZtbz("T");
        }
        model.addAttribute("roleInfo", roleInfo);
        model.addAttribute("bookset", select);
        model.addAttribute("hidden", hidden);
        model.addAttribute("disabled", disabled);
        return new ModelAndView("role/roleDialog");
    }

    /**
     * 添加（编辑）角色
     *
     * @param roleInfo
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_ROLE")
    @PostMapping("saveRole")
    public Message saveRole(@Validated RoleInfo roleInfo, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            RoleInfo mast = new RoleInfo();
            String id = roleInfo.getId();
            if (id == null || roleInfo.getId().isEmpty()) {
                //添加
                mast.setRoleName(roleInfo.getRoleName());
                if (user.isAdmin()) {
                    mast.setBookSet(roleInfo.getBookSet());
                } else {
                    mast.setBookSet(user.getBookSet());
                }
                mast.setRemarks(roleInfo.getRemarks());
                return roleInfoService.addRoleInfo(mast);
            } else {
                //更新
                mast = roleInfoService.getRoleInfo(id);
                mast.setZtbz(roleInfo.getZtbz());
                mast.setRoleName(roleInfo.getRoleName());
                if (user.isAdmin())
                    mast.setBookSet(roleInfo.getBookSet());
                mast.setRemarks(roleInfo.getRemarks());
                return roleInfoService.updateRoleInfo(mast);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_ROLE")
    @PostMapping(value = "deleRole")
    public Message deleRole(String id, HttpSession session) {
        if (StringUtils.isEmpty(id))
            return new Message(0, "参数错误");
        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            List<Role> roleList = user.getRoles();
            for (Role role : roleList) {
                if (role.getId().equals(id)) {
                    return new Message(0, "当前角色正在使用中，禁止删除");
                }
            }
        }
        Integer o = permInfoService.delePermInfoBYRoleID(id);
        if (o != null) {
            return roleInfoService.delRoleINfo(id);
        } else {
            return new Message(0, "删除失败");
        }

    }

    /**
     * 获取角色列表
     *
     * @param keyword
     * @return
     */
    @AuthMethod(role = "ROLE_ROLE")
    @RequestMapping("list")
    public List<RoleInfo> list(String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) {
            return roleInfoService.listRoleInfo(keyword);
        } else {
            return roleInfoService.listRoleInfo(keyword, user.getBookSet());
        }
    }

    /**
     * 按角色获取未授权模块列表
     *
     * @param role_id 角色ID
     * @param keyword 关键字
     * @return arrayJson
     */
    @SuppressWarnings("unchecked")
    @AuthMethod(role = "ROLE_ROLE")
    @RequestMapping("getMenuInfo")
    public List<ListGroup> getMenuInfo(String role_id, String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<String> menuIds = (List<String>) session.getAttribute("menuIds");
        boolean isAdmin = user.isAdmin();
        return permInfoService.ListMeunInfo2ListGroup(menuIds, role_id, keyword, isAdmin);
    }

    /**
     * 按角色获已授权模块列表
     *
     * @param role_id 角色ID
     * @param keyword 关键字
     * @return
     */
    @AuthMethod(role = "ROLE_ROLE")
    @RequestMapping("getPermInfo")
    public List<ListGroup> getPermInfo(String role_id, String keyword) {
        return permInfoService.listPermInfo2ListGroup(role_id, keyword);
    }

    @AuthMethod(role = "ROLE_ROLE")
    @PostMapping("savaPermInfo")
    public Message savaPermInfo(String menu_ids, String role_id) {
        return permInfoService.batchSavePermInfo(role_id, menu_ids);
    }

    @AuthMethod(role = "ROLE_ROLE")
    @PostMapping("delPermInfo")
    public Message delPermInfo(String menu_ids, String role_id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Role> list = user.getRoles();
        //非系统管理员禁止移除默认权限
        for (Role role : list) {
            if (role.getId().equals(role_id) && !user.isAdmin())
                return new Message(0, "当前角色正在使用中，禁止移除所属权限");
        }
        return permInfoService.batchDelePermInfo(role_id, menu_ids);
    }

}
