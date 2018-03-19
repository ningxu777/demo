package com.neil.demo.designpattern.singletonPattern;

/**
 * Created by Neil on 16/8/17.
 * 懒汉式,需要兼顾多线程所以采用双重检查,仍然有问题:volatile关键字有两层语义,第一层是可见性指的是在一个线程中对该变量的修改会马上有工作内存
 * 写回主内存所以会马上反映在其他线程中;第二层意思是禁止指令重排序优化,大家知道我们编写的代码在编译的时候会被编译器优化,实际执行的时候可能与我们
 * 编写的顺序不一样.这在单线程没问题,但一旦引入多线程这种乱序可能导致严重问题.而volatile关键字知道jdk1.5以后才能正常工作.
 */
public class LazySingleton {

    private LazySingleton() {
    }

    private static volatile LazySingleton instance = null;

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
