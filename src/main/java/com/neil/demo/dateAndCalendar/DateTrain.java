package com.neil.demo.dateAndCalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Neil on 16/4/14.
 */
public class DateTrain {

    public static void main(String[] args){
//        int a = 3;
//        int b = 9;
//        int c = a*b%12;
//        //System.out.println(c);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//            date = df.parse("2016-4-15 16:15:14");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date.getDay());
//
//        boolean x = (5 == 5 && 6 == 7);
//        //System.out.println(x);
//        Date  date1 = new Date(1462870800000L);
//
//        System.out.println(date1.getYear()+1900);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse("2016-4-15 12:15:14");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
    }
}
