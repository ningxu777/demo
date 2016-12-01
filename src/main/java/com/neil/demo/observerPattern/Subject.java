package com.neil.demo.observerPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 16/7/8.
 * 抽象主题,提供通知方法
 */
public abstract class Subject {

    private List<Observer> observerList = new ArrayList<Observer>();

    public String subjectState; //对象状态

    public abstract String getState();

    public abstract void setState(String subjectState);

    /**
     * 注册观察者
     * @param observer
     */
    public void register(Observer observer){
        observerList.add(observer);
        this.setSubjectToObserver(observer);
    }

    public abstract void setSubjectToObserver(Observer observer);

    /**
     * 注销观察者
     * @param observer
     */
    public void unregister(Observer observer){
        observerList.remove(observer);
    }

    /**
     * 通知观察者更新
     */
    public void post() {
        for (Observer item : observerList) {
            item.update();
        }
    }


}
