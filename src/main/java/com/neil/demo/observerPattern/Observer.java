package com.neil.demo.observerPattern;

/**
 * Created by Neil on 16/7/8.
 */
public abstract class Observer {

    protected Subject subject;

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    public abstract void update();
}
