package com.neil.demo.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Neil on 16/5/5.
 */
public class ListTrain {
    public static void main(String[] args) {
        //getBJ();
        //orderList();
        //setByIndex();
        copyList();
    }

    //指定index设置值
    public static void setByIndex() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        for (int i = 0; i < 4; i++) {
            System.out.println(l.get(0));
            l.remove(0);
        }
    }

    //交集&并集
    public static void getBJ() {
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(4);
        List<Integer> l = new ArrayList<Integer>();
        for (Integer item : l1) {
            if (item == 2 || item == 7) {
                l.add(item);
            }
        }
        l1.removeAll(l);
        l.addAll(l1);
        System.out.println(l);
    }

    //排序
    public static void orderList() {
        //普通排序
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(9);
        l1.add(10);
        l1.add(5);
        l1.add(7);
        Collections.sort(l1);
        System.out.println(l1);
        //重载Compares方法排序
        List<Person> l2 = new ArrayList<Person>();
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            int reandom = (int) (1 + Math.random() * 10);
            p.setId(reandom);
            p.setName("ren" + reandom);
            l2.add(p);
            System.out.println(p.getName());
        }
        Collections.sort(l2, new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        for (Person item : l2) {
            System.out.println(item.getName());
        }
    }

    //复制list
    public static void copyList() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        List<Integer> lCopy = new ArrayList<Integer>(l);
        l.remove(0);
        System.out.println(l);
        System.out.println(lCopy);
    }

    static class Person {
        Integer id;
        String name;
        int age;

        public Integer getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

}
