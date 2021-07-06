package com.shgs.lodge.service;

import com.shgs.lodge.util.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param bookSet
     * @return
     */
    Message loginUser(String userName, String password, String bookSet, HttpServletRequest request, HttpServletResponse response) throws Exception;




}
