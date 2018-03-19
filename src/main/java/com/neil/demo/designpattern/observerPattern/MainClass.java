package com.neil.demo.designpattern.observerPattern;

/**
 * Created by Neil on 16/7/8.
 * 观察者模式demo
 */
public class MainClass {
    public static void main(String[] args) {
        Subject subject1 = new Subject1();
        subject1.register(new Observer1());
        subject1.register(new Observer2());
        subject1.setState("subject1");

        Subject subject2 = new Subject2();
        subject2.register(new Observer1());
        subject2.register(new Observer2());
        subject2.setState("subject2");
    }
}
