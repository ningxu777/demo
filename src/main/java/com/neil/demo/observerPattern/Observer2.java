package com.neil.demo.observerPattern;

/**
 * Created by Neil on 16/7/8.
 */
public class Observer2 extends Observer {

    @Override
    public void update() {
        String subjectState = subject.getState();
            System.out.println("Observer2 is updating... becuase of "+subjectState+" `s change");
    }
}
