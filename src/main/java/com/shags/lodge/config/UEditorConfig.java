package com.shags.lodge.config;

import com.shags.lodge.baidu.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhuzhe
 * @date 2018/5/22 10:19
 * @email 1529949535@qq.com
 */
@Controller
public class UEditorConfig {

    /**
     * ueditor配置文件名称
     */
    @Value("${UEditorConfig.fileName}")
    private String configFileName;

    /**
     * 读取配置
     * @param request
     * @param response
     */
    @RequestMapping(value="/ueditor")
    public void config(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            String exec = new ActionEnter(request, rootPath,configFileName,session).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
