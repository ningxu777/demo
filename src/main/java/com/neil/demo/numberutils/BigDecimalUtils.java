package com.neil.demo.numberutils;

import java.text.DecimalFormat;

/**
 * Created by Neil on 2018/2/6.
 */
public class BigDecimalUtils {

    public static Double setDoubleScale(double value, int scale,Integer multiple) {
        //BigDecimal decimal = new BigDecimal(value);
        //return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        StringBuffer a = new StringBuffer("0.");
        for(int i = 0; i < scale; i++){
            a.append("0");
        }
        DecimalFormat df = new DecimalFormat(a.toString());
        Double rel = value;
        if(multiple != null && multiple > 1){
            rel = value * multiple;
        }
        return Double.valueOf(df.format(rel));
    }
}
