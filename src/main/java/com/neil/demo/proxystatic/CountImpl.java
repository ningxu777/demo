package com.neil.demo.proxystatic;

/**
 * 委托类(包含业务逻辑)
 * Count接口的实现类
 *
 * @author gooooooo
 */
public class CountImpl implements Count {

    public void queryCount() {
        System.out.println("查询账户！");
    }

    public void updateCount() {
        System.out.println("修改账户！");
    }
}
