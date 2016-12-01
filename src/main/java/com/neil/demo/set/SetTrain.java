package com.neil.demo.set;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neil on 16/4/21.
 */
public class SetTrain {
    public static void main(String[] args){
        Set<Integer> s1 = new HashSet<Integer>();
        s1.add(1);
        s1.add(2);
        s1.add(3);
        Set<Integer> s2 = new HashSet<Integer>();
        s2.add(3);
        s2.add(4);
        s2.add(5);
        System.out.println(s1);
        s1.removeAll(s2);
        System.out.println(s1);
        Set<Integer> s3 = new HashSet<Integer>();
        System.out.println(s3);
    }
}
