package com.nettydome.nettydemo;

import com.nettydome.nettydemo.Dao.DtuDao;
import com.nettydome.nettydemo.entity.Dtu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: DtuTest
 * @Description: TODO
 * @Author: dhx
 * @CreateDate: 2019/8/7 14:25
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DtuTest {

    @Autowired
    private DtuDao dtuDao;

    @Test
    public void selectAll() {
        List<Dtu> dtus = dtuDao.selectAll();
        for (Dtu dtu : dtus) {
            System.out.println(dtu);
        }
    }

    @Test
    public void insertIp() {
        Dtu dtu = new Dtu();
        dtu.setDtuId(7);
        dtu.setDtuMac("aabb");
        dtu.setDtuIp("192.168.01.11");
        dtuDao.insertIp(dtu);
    }

    @Test
    public void updateIp() {
        Dtu dtu = new Dtu();
        dtu.setDtuId(3);
        dtu.setDtuIp("192.168.011.1");
        dtuDao.undateIp(dtu);
    }

    @Test
    public void selectOne() {
        String ip="127.0.0.1";
        Dtu dtu = dtuDao.selectOneByIp(ip);
        System.out.println(dtu);
    }

    @Test
    public void selectOneBymac() {
        String mac="98-d8-63-11-a1-3d";
        Dtu dtu = dtuDao.selectOneByMac(mac);
        System.out.println(dtu);

    }
}
