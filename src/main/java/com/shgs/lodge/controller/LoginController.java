package com.shgs.lodge.controller;

import com.shgs.lodge.config.JWTConfig;
import com.shgs.lodge.dto.RSAPublicDto;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.exception.exception.JsonException;
import com.shgs.lodge.jwt.util.CookieUtil;
import com.shgs.lodge.jwt.util.JWTTokenUtil;
import com.shgs.lodge.service.*;
import com.shgs.lodge.servlet.CmsSessionContext;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.RSAUtils;
import com.shgs.lodge.util.SelectJson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/login/")
public class LoginController {

    private IAccounInfoService accounInfoService;

    private IUserService userService;

    @Autowired
    public void setAccounInfoService(IAccounInfoService accounInfoService) {
        this.accounInfoService = accounInfoService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 普通登录页面
     *
     * @param model
     * @return
     */

    @GetMapping(value = "index")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<SelectJson> select = accounInfoService.listAccounInfoToSelectJson("", "T");
        String userName = "";
        String accoun = "";
        String check = "F";
        boolean exp = JWTTokenUtil.isTokenExpiration(request, JWTConfig.tokenHeader, JWTConfig.secret);
        if (!exp) {
            Claims claims = JWTTokenUtil.getClaims(request, JWTConfig.tokenHeader, JWTConfig.secret);
            if (claims != null && claims.size() > 0) {
                userName = (String) claims.get("sub");
                accoun = (String) claims.get("bookSet");
                check = "T";
            }
        } else {
            CookieUtil.deleteCookie(response, JWTConfig.tokenHeader);
        }
        select.add(0, new SelectJson("", "请选择..."));
        model.addAttribute("bookSet", select);
        model.addAttribute("userName", userName);
        model.addAttribute("accoun", accoun);
        model.addAttribute("check", check);
        return "login";
    }


    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout")
    @ResponseBody
    public Message logout(HttpSession session) {
        try {
            CmsSessionContext.removeSession(session);
            session.invalidate();
        } catch (Exception e) {
            throw new JsonException(1, "成功退出");
        }
        return new Message(1, "成功退出");
    }

    /**
     * 获取加密钥匙
     *
     * @param session
     * @return
     */
    @PostMapping(value = "getKey")
    @ResponseBody
    public RSAPublicDto getKey(HttpSession session) {
        RSAPublicDto client = new RSAPublicDto();
        HashMap<String, Object> keys;
        try {
            keys = RSAUtils.getKeys();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) keys.get("private");
        session.setAttribute("loginKey", privateKey);
        String modulus = publicKey.getModulus().toString(16);
        String exponent = publicKey.getPublicExponent().toString(16);
        client.setModulus(modulus);
        client.setExponent(exponent);
        return client;
    }

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     * @param bookSet
     * @param session
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "login")
    public Message login(String userName, String password, String bookSet, String check, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Message msg = userService.loginUser(userName, password, bookSet, request, response);
        if (msg.getCode() == 1) {
            if ("T".equals(check)) {
                String token = JWTTokenUtil.createAccessToken((User) msg.getData());
                CookieUtil.setCookie(response, JWTConfig.tokenHeader, token, 7 * 24 * 60 * 60);
            } else {
                CookieUtil.deleteCookie(response, JWTConfig.tokenHeader);
            }
            String basePath = request.getContextPath();
            String url = basePath + "/main/index";
            session.setAttribute("basePath", basePath);
            msg.setData(url);
        } else {
            msg.setCode(0);
            msg.setMessage("系统初始化错误");
        }
        return msg;
    }

}

