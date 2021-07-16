package com.shags.lodge.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;

/**
 * 配置文件管理内
 *
 * @author lei
 * @since 2020-06-27
 */
public class FileManagerUtil extends Thread {

  private static final Logger LOG = LoggerFactory.getLogger(FileManagerUtil.class);
  public static String PROPERTIES_URL = null;
  private static Properties properties;

  static {
    try {
      PROPERTIES_URL = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent() + "/config/application-client.properties";
      if (PROPERTIES_URL.startsWith("file:")) {
        PROPERTIES_URL = PROPERTIES_URL.replaceFirst("file:", "");
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  //新增
  public static Boolean addProperty(String key, String value) {
    if (getProperty(key) != null) {
      LOG.info("application-client.properties already has this key");
      return false;
    }
    return optionProperty(key, value);
  }

  //获取属性
  public static String getProperty(String key) {
    try (FileInputStream fis = new FileInputStream(PROPERTIES_URL)) {
      if (properties == null) {
        load();
      }
      properties.load(fis);
    } catch (IOException ex) {
      LOG.error("application-client.properties load failed", ex);
      return null;
    }
    return properties.getProperty(key);
  }

  public static Boolean optionProperty(String key, String value) {
    try (FileOutputStream fos = new FileOutputStream(PROPERTIES_URL)) {
      if (properties == null) {
        load();
      }
      properties.setProperty(key, value);
      // 将Properties集合保存到流中   
      properties.store(fos, "Copyright (c) 攀钢集团生活服务公司");
    } catch (IOException ea) {
      LOG.error("application-client.properties load failed", ea);
      return false;
    }
    return true;
  }

  public static void load() throws IOException {
    properties = new Properties();
    try (FileInputStream fis = new FileInputStream(PROPERTIES_URL)) {
      properties.load(fis);
    }
  }

  //修改
  public static Boolean updateProperty(String key, String value) {
    return optionProperty(key, value);
  }

}

