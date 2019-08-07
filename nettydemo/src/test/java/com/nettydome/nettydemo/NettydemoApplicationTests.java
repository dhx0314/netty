package com.nettydome.nettydemo;


import com.nettydome.nettydemo.netty.GetDtuIp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NettydemoApplicationTests {

    @Autowired
    private GetDtuIp getDtuIp;

    @Test
    public void contextLoads() {
        HashMap dtuIp = getDtuIp.getDtuIpAddress();

        System.out.println(dtuIp);


        System.out.println("aaaaa");
    }

}
