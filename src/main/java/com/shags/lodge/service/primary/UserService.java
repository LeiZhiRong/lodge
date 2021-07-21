package com.shags.lodge.service.primary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shags.lodge.dto.*;
import com.shags.lodge.util.BeanUtil;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.primary.entity.AccounInfo;
import com.shags.lodge.primary.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserService implements IUserService {


    private IUserInfoService userInfoService;


    private IAccounInfoService accounInfoService;


    private IUserRoleService userRoleService;


    private IPermInfoService permInfoService;


    private IMenuService menuService;

    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setAccounInfoService(IAccounInfoService accounInfoService) {
        this.accounInfoService = accounInfoService;
    }

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setPermInfoService(IPermInfoService permInfoService) {
        this.permInfoService = permInfoService;
    }

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Message loginUser(String userName, String password, String bookSet, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(userName)) {
            return new Message(0, "登录账号不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return new Message(0, "登录密码不能为空");
        }
        if (StringUtils.isEmpty(bookSet))
            return new Message(0, "请选择登录账套");
        User dto = new User(password, session);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        boolean isAdmin;
        Message msg = userInfoService.loginUser(userName, dto.getPassword());
        if (msg.getCode() == 1) {
            UserInfo userInfo = (UserInfo) msg.getData();
            if ("T".equals(userInfo.getManager())) {
                isAdmin = true;
                dto.setAdmin(true);
            } else {
                isAdmin = false;
                dto.setAdmin(false);
            }
            dto.setBookSet(bookSet);
            dto.setPassword(null);
            dto.setUserName(userInfo.getUserName());
            dto.setId(userInfo.getId());
            dto.setAccount(userInfo.getLoginAccount());
            dto.setEndAddress(userInfo.getLoginIP());
            if (userInfo.getLoginTime() != null)
                dto.setEndTime(BeanUtil.timestampToStr(userInfo.getLoginTime(), "yyyy-MM-dd HH:mm"));
            //获取登录账套
            AccounInfo accounInfo = accounInfoService.queryAccounInfoByBookSet(dto.getBookSet());
            if (accounInfo != null) {
                if ("F".equals(accounInfo.getZtbz()))
                    return new Message(0, "该系统已到期，请联系系统管理员");
                dto.setCorpName(accounInfo.getCorpName());
            }
            //获取角色
            List<Role> roles = userRoleService.getRoleById(userInfo.getId(), isAdmin ? null : dto.getBookSet());
            if (roles == null || roles.size() == 0) {
                msg.setMessage("获取账号角色失败，请联系管理员授权后重新登录");
                msg.setCode(0);
                return msg;
            }
            List<String> menuIds = new ArrayList<>();
            List<Role> roleList = new ArrayList<>();
            List<Permissions> _permissionsList = new ArrayList<>();
            for (Role role : roles) {
                List<Permissions> permissionsList = permInfoService.listRoleType(role.getId());
                if (permissionsList != null && permissionsList.size() > 0) {
                    for (Permissions permissions : permissionsList) {
                        _permissionsList.add(permissions);
                        List<String> m = CmsUtils.string2Array(permissions.getIds(), ",");
                        for (String str : m) {
                            if (!menuIds.contains(str))
                                menuIds.add(str);
                        }
                    }
                }
                role.setPermissions(permissionsList);
                roleList.add(role);
            }
            if (menuIds.size() == 0) {
                msg.setMessage("获取系统操作权限失败，请联系管理员授权后重新登录");
                msg.setCode(0);
                return msg;
            }
            dto.setRoles(roleList);
            List<TreeJson> menuInfoList = menuService.listMenuInfoTreeByIDS(menuIds, isAdmin);
            List<MenuShortcut> menuShortcutList = menuService.listMenuShortcutByIDS(menuIds, isAdmin);
            if (menuInfoList != null && menuInfoList.size() > 0) {
                userInfo.setLoginIP(CmsUtils.getIpAddr(request));
                userInfo.setLoginTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                userInfoService.updateUserinfo(userInfo);
                dto.setLoginAddress(userInfo.getLoginIP());
                dto.setManageDept(userInfo.getDeptID());
                dto.setBalanceDept(userInfo.getSettID());
                dto.setLoginTime(BeanUtil.timestampToStr(userInfo.getLoginTime(), "yyyy-MM-dd HH:mm"));
                dto.setMenuIds(menuIds);
                session.setAttribute("isAdmin", isAdmin);
                session.setAttribute("menuIds", menuIds);
                session.setAttribute("menuList", mapper.writeValueAsString(menuInfoList));
                session.setAttribute("menuShortcut", menuShortcutList);
                session.setAttribute("user", dto);
                session.setAttribute("allActions", getAllActions(_permissionsList, session));
                msg.setData(dto);
            } else {
                msg.setCode(0);
                msg.setMessage("系统初始化错误");
            }
        }
        return msg;
    }


    @SuppressWarnings("unchecked")
    private List<String> getAllActions(List<Permissions> permissions, HttpSession session) {
        List<String> roletype = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        Map<String, List<String>> allAuths = (Map<String, List<String>>) session.getServletContext().getAttribute("allAuths");
        for (Permissions mast : permissions) {
            roletype.add(mast.getRoleType());
        }
        try {
            actions.addAll(allAuths.get("base"));
        } catch (Exception ignored) {

        }
        for (String str : roletype) {
            try {
                actions.addAll(allAuths.get(str));
            } catch (Exception ignored) {

            }
        }
        return actions;
    }

}
