package com.shags.lodge.servlet;

import com.shags.lodge.dto.User;
import com.shags.lodge.exception.exception.JsonException;
import com.shags.lodge.exception.exception.PageException;
import groovy.util.logging.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@SuppressWarnings("unchecked")
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        HandlerMethod hm = (HandlerMethod) handler;
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "1800");
        //  设置  受支持请求标头（自定义  可以访问的请求头  例如：Token）
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token,Origin,Content-Type,Accept");
        // 指示的请求的响应是否可以暴露于该页面。当true值返回时它可以被暴露
        response.setHeader("Access-Control-Allow-Credentials", "true");
       //如果是OPTIONS请求，则放行，否则进行拦截判断
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        // 如果不是映射到方法直接通过
        boolean ajax = request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            if (ajax) {
                throw new JsonException(401, "登录超时，请重新登录");
            } else {
                response.sendRedirect(request.getContextPath() + "/login/index");
                return false;
            }

        } else {
            List<String> actions = (List<String>) session.getAttribute("allActions");
            String name = hm.getBean().getClass().getName() + "." + hm.getMethod().getName();
            if (!actions.contains(name)) {
                if (ajax) {
                    throw new JsonException(500, "禁止访问");
                } else {
                    throw new PageException(403, "禁止访问");
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);

    }

    // 在目标方法执行后执行，但在请求返回前，我们仍然可以对 ModelAndView进行修改
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    // 在请求已经返回之后执行
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {


    }

}

