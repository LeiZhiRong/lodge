package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.RSAPublicDto;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.primary.entity.UserInfo;
import com.shgs.lodge.service.IUserInfoService;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.MyJasyptStringEncryptor;
import com.shgs.lodge.util.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import static java.net.URLDecoder.decode;

@RestController
@Scope("prototype")
@RequestMapping("/home/")
@AuthClass("login")
public class HomeController {


    private IUserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @AuthMethod
    @GetMapping("index")
    public ModelAndView index(@RequestHeader("token") String token, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        return new ModelAndView("home/index");
    }

    /**
     * 密码修改
     *
     * @param model
     * @param session
     * @return
     */
    @AuthMethod
    @GetMapping("password")
    public ModelAndView password(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String userId = user.getId();
        model.addAttribute("userId", userId);
        return new ModelAndView("home/password");
    }

    /**
     * 用户信息
     *
     * @param model
     * @param session
     * @return
     */
    @AuthMethod
    @GetMapping("getUserInfo")
    public ModelAndView getUserInfo(Model model, HttpSession session) {
        return new ModelAndView("home/userInfo");
    }

    /**
     * 获取加密钥匙
     *
     * @param session
     * @return
     */
    @AuthMethod
    @PostMapping(value = "getKey")
    @ResponseBody
    public RSAPublicDto getKey(HttpServletRequest request, HttpSession session) {
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
     * 修改密码
     *
     * @param id
     * @param password
     * @param confirmPwd
     * @param newPwd
     * @param session
     * @return
     */
    @AuthMethod
    @PostMapping(value = "saveUserPwd")
    public Message saveUserPwd(String id, String password, String confirmPwd, String newPwd, HttpSession session) {
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("loginKey");
            if (password == null || password.isEmpty())
                return new Message(0, "原始密码不能为空!", 0);
            if (newPwd == null || newPwd.isEmpty())
                return new Message(0, "重设密码不能为空!", 0);
            if (confirmPwd == null || confirmPwd.isEmpty())
                return new Message(0, "确认密码不能为空!", 0);
            if (id == null || id.isEmpty())
                return new Message(0, "获取用户参数失败!", 0);
            String _pwd = decode(RSAUtils.decryptByPrivateKey(password, privateKey), "UTF-8");
            String _id = (RSAUtils.decryptByPrivateKey(id, privateKey));
            String _newPwd = decode(RSAUtils.decryptByPrivateKey(newPwd, privateKey), "UTF-8");
            String _confirmPwd = decode(RSAUtils.decryptByPrivateKey(confirmPwd, privateKey), "UTF-8");
            if (!_newPwd.equals(_confirmPwd))
                return new Message(0, "两次密码验证失败！");
            UserInfo userInfo = userInfoService.queryUserInfoById(_id);
            if (userInfo != null) {
                String decrypt = new MyJasyptStringEncryptor().decrypt(userInfo.getLoginPassword());
                if (_pwd.equals(decrypt)) {
                    userInfo.setLoginPassword(new MyJasyptStringEncryptor().encrypt(_newPwd));
                    return userInfoService.updateUserinfo(userInfo);
                } else {
                    return new Message(0, "原始密码不正确");
                }
            } else {
                return new Message(0, "用户信息获取错误，请联系管理员");
            }

        } catch (Exception e) {
            return new Message(0, "参数错误");
        } finally {
            session.removeAttribute("loginKey");
        }
    }


}
