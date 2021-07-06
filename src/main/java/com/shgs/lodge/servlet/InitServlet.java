package com.shgs.lodge.servlet;

import com.shgs.lodge.auth.AuthUtil;
import com.shgs.lodge.exception.exception.PageException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Set;

/**
 * Servlet权限谁让认证
 * @author 雷智荣
 * @date 2021-01-29
 */
@WebServlet(loadOnStartup=1,urlPatterns={"/initServlet"})
public class InitServlet extends HttpServlet {

  private static WebApplicationContext wc;

  @Override
  public void init(ServletConfig config) throws ServletException {
    try {
      super.init(config);
      wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      Map<String, Set<String>> auths = AuthUtil.initAuth("com.shgs.lodge.controller");
      this.getServletContext().setAttribute("allAuths", auths);
    } catch (ServletException e) {
      throw new PageException(500,"系统初始化错误,请联系管理员");
    }

  }
  public static WebApplicationContext getWc() {
    return wc;
  }

}

