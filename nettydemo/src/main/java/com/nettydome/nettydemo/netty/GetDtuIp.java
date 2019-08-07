package com.nettydome.nettydemo.netty;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: GetDtuIp
 * @Description: 通过DTU的MAC地址获取当前DTU使用的IP地址
 * @Author: dhx
 * @CreateDate: 2019/8/7 10:05
 * @Version: 1.0
 */
@Component
public class GetDtuIp {

    @Value("${dtu.DTU_DEVICE01MAC}")
    private String DTU_DEVICE01MAC;
    @Value("${dtu.DTU_DEVICE02MAC}")
    private String DTU_DEVICE02MAC;



    public void fun1() {
        System.out.println(DTU_DEVICE02MAC);
        System.out.println(DTU_DEVICE01MAC);
    }

    /**
     * @Author dhx
     * @Description //TODO
     * @Date 2019//7 10:2
     * @Return java.util.Map
     **/
    public  HashMap<String, String> getDtuIp() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> ipMap = new HashMap<>();
        BufferedReader br = null;
        String command = "arp -a";//获得局域网IP跟MAC地址
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                //  sb.append(line + "\n");
                String s = line;
                if (line.length() > 40) {
                    String IP = line.substring(0, 15).trim();
                    String MAC = line.substring(22, 42).trim();
                    map.put(MAC, IP);
                  //  System.out.println(s);
                }
            }
            System.out.println("------------");
            for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                if (DTU_DEVICE01MAC.equals(stringStringEntry.getKey())) {
                    System.out.println(stringStringEntry.getKey()+"  "+stringStringEntry
                            .getValue());
                    ipMap.put(stringStringEntry.getValue(),"DTU设备1");
                }
                if (DTU_DEVICE02MAC.equals(stringStringEntry.getKey())) {
                    System.out.println(stringStringEntry.getKey()+"  "+stringStringEntry
                            .getValue());
                    ipMap.put(stringStringEntry.getValue(),"DTU设备2");
                }
            }

            return ipMap;
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
        return ipMap;
    }

}
