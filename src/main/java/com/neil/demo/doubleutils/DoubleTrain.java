package com.neil.demo.doubleutils;

import java.text.DecimalFormat;

/**
 * Created by Neil on 16/7/6.
 */
public class DoubleTrain {

    public static Double setDoubleScale(double value, int scale, Integer beishu) {
        //BigDecimal decimal = new BigDecimal(value);
        //return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        StringBuffer a = new StringBuffer("0.");
        for (int i = 0; i < scale; i++) {
            a.append("0");
        }
        DecimalFormat df = new DecimalFormat(a.toString());
        Double rel = value;
        if (beishu != null && beishu > 1) {
            rel = value * beishu;
        }
        String relsult = df.format(rel);
        return Double.valueOf(relsult);
    }

    public static void main(String[] args) {
//        double a = 70;
//        double b = 106;
//        Double c = setDoubleScale(a/b,2,null);
//        System.out.println(c);
        String a = "[\"40.015071\",\"116.356704\"]";
        String[] aArr = a.split("\"");
        System.out.println(aArr[1]);
        System.out.println(aArr[3]);
    }


}
