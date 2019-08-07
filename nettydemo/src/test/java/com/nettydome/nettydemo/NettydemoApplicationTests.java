package com.nettydome.nettydemo;


import com.nettydome.nettydemo.netty.GetDtuIp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NettydemoApplicationTests {

    @Autowired
    private GetDtuIp getDtuIp;

    @Test
    public void contextLoads() {
        HashMap<String,String> dtuIp = getDtuIp.getDtuIpAddress();

        System.out.println(dtuIp);

    }


    @Test
    public void fun1() {
        ArrayList<Integer> strings = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            strings.add(i);
        }
        for (Integer string : strings) {
            System.out.println(string);
        }
    }

}
