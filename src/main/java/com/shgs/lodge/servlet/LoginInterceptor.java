package com.shgs.lodge.servlet;

import com.shgs.lodge.dto.User;
import com.shgs.lodge.exception.exception.JsonException;
import com.shgs.lodge.exception.exception.PageException;
import com.shgs.lodge.service.IUserInfoService;
import groovy.util.logging.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mvc拦截器
 *
 * @author 雷智荣
 * @date 2021-01-29
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private IUserInfoService userInfoService;

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
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        boolean ajax = false;
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            ajax = true;
        }
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
            String aname = hm.getBean().getClass().getName() + "." + hm.getMethod().getName();
            if (!actions.contains(aname)) {
                if (ajax) {
                    throw new JsonException(500, "禁止访问");
                } else {
                    throw new PageException(403, "禁止访问");
                }
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("token", "1111111");
        addHeader(request, map);
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }

    // 在目标方法执行后执行，但在请求返回前，我们仍然可以对 ModelAndView进行修改
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    // 在请求已经返回之后执行
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {


    }

    /**
     * 添加Header
     *
     * @param request
     * @param headerMap
     */
    private void addHeader(HttpServletRequest request, Map<String, String> headerMap) {
        if (headerMap == null || headerMap.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> c = request.getClass();
        try {
            Field requestField = c.getDeclaredField("request");
            requestField.setAccessible(true);

            Object o = requestField.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);

            Object o2 = coyoteRequest.get(o);
            Field headers = o2.getClass().getDeclaredField("headers");
            headers.setAccessible(true);

            MimeHeaders mimeHeaders = (MimeHeaders) headers.get(o2);
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                mimeHeaders.removeHeader(entry.getKey());
                mimeHeaders.addValue(entry.getKey()).setString(entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

