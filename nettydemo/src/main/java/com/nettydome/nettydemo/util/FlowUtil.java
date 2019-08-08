package com.nettydome.nettydemo.util;

/**
 * @ClassName: FlowUtil
 * @Description: TODO
 * @Author: dhx
 * @CreateDate: 2019/8/8 13:44
 * @Version: 1.0
 */
public class FlowUtil {

    //    净重
    public static double netWeight(byte[] b) {
        String sum = "";
        for (int i = 4; i <= 9; i++) {
            sum += (char) b[i];
        }
        double abc = Double.valueOf(sum);
        return abc;
    }

    //    皮重
    public static String tareWeigh(byte[] b) {
        String sum = "";
        for (int i = 12; i <= 17; i++) {
            sum += (char) b[i];
        }
        return sum;

    }
}
