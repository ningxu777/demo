package com.neil.demo.singletonPattern;

/**
 * Created by Neil on 16/8/17.
 * 饿汉式,缺点是第一次引用该类的时候就创建对象实例,而不管是否需要创建
 */
public class HungrySingleton {

    //私有化构造方法,防止调用构造方法来创建实例
    private HungrySingleton() {
    }

    private static HungrySingleton instance = new HungrySingleton();

    public static HungrySingleton getInstance() {
        return instance;
    }

}
