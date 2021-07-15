package com.shgs.lodge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class LodgeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LodgeApplication.class, args);
    }

    

}
