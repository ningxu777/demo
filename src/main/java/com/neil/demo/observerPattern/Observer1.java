package com.neil.demo.observerPattern;

/**
 * Created by Neil on 16/7/8.
 */
public class Observer1 extends Observer {

    @Override
    public void update() {
        String subjectState = subject.getState();
        System.out.println("Observer1 is updating...becuase of " + subjectState + " `s change");
    }
}
