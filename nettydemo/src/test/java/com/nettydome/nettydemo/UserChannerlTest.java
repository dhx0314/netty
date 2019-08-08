package com.nettydome.nettydemo;

import com.nettydome.nettydemo.netty.DeviceConnectionManagement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: UserChannerlTest
 * @Description: TODO
 * @Author: dhx
 * @CreateDate: 2019/8/8 11:07
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserChannerlTest {

    @Autowired
    private DeviceConnectionManagement deviceConnectionManagement;


    @Test
    public void test1() {
       DeviceConnectionManagement.output();
    }


    @Test
    public void test2() {
        DeviceConnectionManagement.onlineCount();
    }
}
