package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.User;
import com.shags.lodge.primary.entity.UserInfo;
import com.shags.lodge.service.primary.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@RestController
@Scope("prototype")
@RequestMapping("/main/")
@AuthClass("login")
public class MainController {

    private IUserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @AuthMethod
    @GetMapping("index")
    public ModelAndView list() throws JsonProcessingException {
        return new ModelAndView("index");
    }

    @AuthMethod
    @PostMapping("updateSidemenu")
    public void updateSidemenu(String collapsed, HttpSession session) {
        try {
            if (StringUtils.isNotEmpty(collapsed)) {
                User user = (User) session.getAttribute("user");
                UserInfo info=userInfoService.queryByAccount(user.getAccount());
                if(info!=null){
                    info.setCollapsed(collapsed);
                    userInfoService.updateUserinfo(info);
                    user.setCollapsed(collapsed);
                    session.setAttribute("user", user);
                }
            }
        } catch (Exception e) {

        }

    }


}
