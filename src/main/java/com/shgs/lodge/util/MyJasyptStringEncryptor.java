package com.shgs.lodge.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;

/**
 * 加密解密类
 *
 * @author 雷智荣
 * @since 2020-06-30
 */
public class MyJasyptStringEncryptor implements StringEncryptor {

  private static final Logger LOG = LogManager.getLogger(MyJasyptStringEncryptor.class);
  private final StandardPBEByteEncryptor byteEncryptor;
  private final Base64 base64;

  public MyJasyptStringEncryptor() {
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ0");
    config.setAlgorithm("PBEWithMD5AndDES");
    this.byteEncryptor = new StandardPBEByteEncryptor();
    this.byteEncryptor.setConfig(config);
    this.base64 = new Base64();
  }

  public MyJasyptStringEncryptor(Environment environment) {
    byteEncryptor = new StandardPBEByteEncryptor();
    byteEncryptor.setConfig(getConfig(environment));
    this.base64 = new Base64();
  }

  public MyJasyptStringEncryptor(String password) {
    SimplePBEConfig config = new SimplePBEConfig();
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setPassword(password);
    byteEncryptor = new StandardPBEByteEncryptor();
    byteEncryptor.setConfig(config);
    this.base64 = new Base64();
  }

  public MyJasyptStringEncryptor(SimpleStringPBEConfig config) {
    byteEncryptor = new StandardPBEByteEncryptor();
    byteEncryptor.setConfig(config);
    this.base64 = new Base64();
  }

  @Override
  public String encrypt(String se) {
    byte[] encrypt = byteEncryptor.encrypt((se).getBytes());
    byte[] encode = base64.encode(encrypt);
    return new String(encode, StandardCharsets.UTF_8);
  }

  @Override
  public String decrypt(String se) {
    byte[] decode = base64.decode(se.getBytes());
    byte[] decrypt = byteEncryptor.decrypt(decode);
    return new String(decrypt, StandardCharsets.UTF_8);
  }

  private PBEConfig getConfig(Environment se) {
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(getRequiredProperty(se, "jasypt.encryptor.password"));
    config.setAlgorithm(getProperty(se, "jasypt.encryptor.algorithm", "PBEWithMD5AndDES"));
    config.setKeyObtentionIterations(getProperty(se, "jasypt.encryptor.keyObtentionIterations", "1000"));
    config.setPoolSize(getProperty(se, "jasypt.encryptor.poolSize", "1"));
    config.setProviderName(getProperty(se, "jasypt.encryptor.providerName", null));
    config.setSaltGeneratorClassName(getProperty(se, "jasypt.encryptor.saltGeneratorClassname", "org.jasypt.salt.RandomSaltGenerator"));
    config.setStringOutputType(getProperty(se, "jasypt.encryptor.stringOutputType", "base64"));
    return config;
  }

  private static String getProperty(Environment environment, String key, String defaultValue) {
    if (!propertyExists(environment, key)) {
      LOG.info("Encryptor config not found for property {}, using default value: {}", key, defaultValue);
    }
    return environment.getProperty(key, defaultValue);
  }

  private static boolean propertyExists(Environment environment, String key) {
    return environment.getProperty(key) != null;
  }

  private static String getRequiredProperty(Environment environment, String key) {
    if (!propertyExists(environment, key)) {
      throw new IllegalStateException(String.format("Required Encryption configuration property missing: %s", key));
    }
    return environment.getProperty(key);
  }
}

