package com.neil.demo.properties;

import java.io.InputStream;

/**
 * Created by Neil on 2017/6/12.
 */
public class MyProperties {

    public static String getByKey (String key) {
//        InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
        String value = "";
        try{
            InputStream in = MyProperties.class.getClassLoader().getResource("config.properties").openStream();
            java.util.Properties properties = new java.util.Properties();
            properties.load(in);
            value = properties.getProperty(key);
            in.close();
        }catch (Exception e){
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return value;
    }


}
