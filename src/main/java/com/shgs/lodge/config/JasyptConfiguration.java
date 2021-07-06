package com.shgs.lodge.config;

import com.shgs.lodge.util.MyJasyptStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JasyptConfiguration {
  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor(Environment environment) {
    return new MyJasyptStringEncryptor(environment);
  }

}

