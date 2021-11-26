package com.shags.lodge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class BaiduEditController {
  private static final Logger logger = LoggerFactory.getLogger(BaiduEditController.class);

  @RequestMapping(value = "/ueditor1")
  public void ueditor1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Type", "text/html");
    // jar包里的json文件只能通过流读取
    ClassPathResource classPathResource = new ClassPathResource("config.json");
    StringBuilder builer= new StringBuilder();
    InputStream inputStream = classPathResource.getInputStream();
    try {
      InputStreamReader reader = new  InputStreamReader(classPathResource.getInputStream(),"UTF-8");
      BufferedReader bfReader = new BufferedReader(reader);
      String temContent = null;
      while ((temContent = bfReader.readLine()) != null){
        builer.append(temContent);
      }
      logger.info("读取的config.json文件===="+builer.toString());
      String exec =  builer.toString().trim();
      logger.info("读取的exec===="+builer.toString());
      PrintWriter writer = response.getWriter();
      writer.write(exec);
      writer.flush();
      writer.close();
      bfReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

