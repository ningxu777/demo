package com.neil.demo.observerPattern;

/**
 * Created by Neil on 16/7/8.
 */
public class Subject2 extends Subject {

    public String getState(){
        return subjectState;
    }

    public void setState(String subjectState){
        this.subjectState = subjectState;
        System.out.println("Subject2 is changed.");
        post();
    }

    public void setSubjectToObserver(Observer observer){
        observer.setSubject(this);
    }

}
