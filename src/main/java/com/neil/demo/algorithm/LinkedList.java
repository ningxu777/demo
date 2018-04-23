package com.neil.demo.algorithm;

/**
 * Created by Neil on 2018/3/29.
 * 链表
 */
public class LinkedList {

    public static class Node{

        Object data;

        Node next;

        public Node(Object data){
            this.data = data;
//            this.next = next;
        }

    }

    /**
     * 构造链表
     */
    public static Node create(){
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        return node1;
    }

    /**
     * 迭代法反转链表
     */
    public static Node reverse (Node node){

        Node prev = null;
        Node now = node;
        while(now != null){
            Node next = now.next;
            now.next = prev;
            prev = now;
            now = next;
        }
        return prev;

    }

    /**
     * 递归法反转
     */

    public static Node reverse(Node node,Node prev){
        if(node !=null){
            Node rel = reverse(node.next,node);
            node.next = prev;
            return rel;
        }else {
            return prev;
        }
    }

    public static void main(String[] args){
        Node node = create();
        Node rel = reverse(node,null);
        while(rel != null){
            System.out.println(rel.data);
            rel = rel.next;
        }
    }
}
