package com.shags.lodge.servlet;

import com.shags.lodge.auth.AuthUtil;
import com.shags.lodge.exception.exception.PageException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Set;

@WebServlet(loadOnStartup=1,urlPatterns={"/initServlet"})
public class InitServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    try {
      super.init(config);
      Map<String, Set<String>> auths = AuthUtil.initAuth("com.shags.lodge.controller.lodge,com.shags.lodge.controller.business");
      this.getServletContext().setAttribute("allAuths", auths);
    } catch (ServletException e) {
      throw new PageException(500,"系统初始化错误,请联系管理员");
    }

  }

}

