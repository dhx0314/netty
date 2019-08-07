package com.nettydome.nettydemo.netty;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class GetMacAddress {

    @Test
    public void fun1() {

        String IOSMAC="98-d8-63-11-a1-3d";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> mac = new HashMap<>();
        BufferedReader br = null;
        String command="arp -a";//获得局域网IP跟MAC地址
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
              //  sb.append(line + "\n");
                String s=line;
                if (line.length()>40) {
                    String IP = line.substring(0, 15).trim();
                    String MAC = line.substring(22, 42).trim();
                    map.put(MAC, IP);
                    System.out.println(s);
                }
            }
           // System.out.println(sb.toString());

            for (int i = 0; i <3 ; i++) {
                System.out.println();
            }

            for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                if (IOSMAC.equals(stringStringEntry.getKey())) {
                    System.out.println(stringStringEntry.getKey()+"  "+stringStringEntry
                    .getValue());
                    mac.put(stringStringEntry.getKey(),stringStringEntry
                            .getValue());
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

}


