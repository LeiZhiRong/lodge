package com.shags.lodge;

import com.shags.lodge.util.MyJasyptStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LodgeApplicationTests {


    @Test
    void contextLoads() {

    }

    @Test
    void test(){
        System.out.println(new MyJasyptStringEncryptor().encrypt("123456"));
    }

}
