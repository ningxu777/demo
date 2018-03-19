package com.neil.demo.designpattern.singletonPattern;

/**
 * Created by Neil on 16/8/17.
 * 静态内部类方法,即实现了延迟加载又保证线程安全.我们可以把类的实例放到一个静态内部类中,这样就避免了静态实例在类加载的时候就创建对象,并且
 * 由于内部类只会被加载一次,所以这种写法也是线程安全的.
 * <p>
 * 也不是最完美的方法,因为可能会有人使用反射强行调用私有构造方法
 */
public class InnerClassSingleton {

    private InnerClassSingleton() {
    }

    //静态内部类
    private static class Holder {
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance() {
        return Holder.instance;
    }
}
