package com.neil.demo.proxydynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理类
 *
 * @author gooooooo
 */
public class BookFacadeProxy implements InvocationHandler {

    private Object target;

    /**
     * 绑定委托对象并返回一个代理类
     */
    public Object bind(Object target) {
        this.target = target;
        //要绑定接口，这是一个缺陷，cglib弥补了这一缺陷
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        System.out.println("执行添加图书方法之前要执行的代码。。。。");
        result = method.invoke(target, args);
        System.out.println("执行添加图书方法之后要执行的代码。。。。");
        return result;
    }

    //测试JDK动态代理
    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
        bookProxy.addBook();
    }

}
