package com.neil.demo.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Neil on 2017/6/12.
 */
public class MyProperties {

    public static Properties properties = new Properties();

    //静态代码块，只在类装载的时候执行
    static{
        // InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
        InputStream in = null;
        try {

            in = MyProperties.class.getClassLoader().getResource("config.properties").openStream();
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //非静态代码块，每new一次都会执行, 且先于构造方法执行
    {
        //do something
    }

    //private使外部对象无法通过new 的方式创建MyProperties对象。此为单例类的必要写法
    private MyProperties(){}

    public static String getByKey (String key) {
        String value = "";
        value = properties.getProperty(key);
        return value;
    }
}
