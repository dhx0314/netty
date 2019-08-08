package com.nettydome.nettydemo.util;

import com.nettydome.nettydemo.Dao.DtuDao;
import com.nettydome.nettydemo.entity.Dtu;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GetDtuIp
 * @Description: 通过DTU的MAC地址获取当前DTU使用的IP地址
 * @Author: dhx
 * @CreateDate: 2019/8/7 10:05
 * @Version: 1.0
 */



@Component
public class DtuUtil {


//    private String DTU_DEVICE01MAC="98-d8-63-11-a1-3d";
//    private String DTU_DEVICE02MAC="00-1e-64-df-fc-9b";
//    private String DTU_DEVICE03MAC="ff-1e-64-df-fc-9b";

    @Autowired
    private DtuDao dtuDao;


    /**
     * @Author dhx
     * @Description //TODO 初始化DTU设备的IP
     * @Date 2019//7 10:2
     * @Return java.util.Map
     **/
    public  void setDtu() {


        HashMap<String, String> map = new HashMap<>();
        BufferedReader br = null;
        String command = "arp -a";//获得局域网IP跟MAC地址
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String s = line;
                if (line.length() > 40) {
                    String IP = line.substring(0, 15).trim();
                    String MAC = line.substring(22, 42).trim();
                    map.put(MAC, IP);
                  //  System.out.println(s);
                }
            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String mac=entry.getKey();
                Dtu dtu = dtuDao.selectOneByMac(mac);
                if (dtu != null) {
                    if (mac.equals(dtu.getDtuMac())) {
                        Dtu newDtu = new Dtu();
                        newDtu.setDtuId(dtu.getDtuId());
                        newDtu.setDtuIp(entry.getValue());
                        dtuDao.undateIp(newDtu);
                    }
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static String getDtuIp(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        return clientIP;
    }

}
