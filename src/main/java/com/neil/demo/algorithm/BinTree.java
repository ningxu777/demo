package com.neil.demo.algorithm;


import java.util.*;
import java.util.LinkedList;

/**
 * Created by Neil on 2018/3/30.
 * 二叉树
 */
public class BinTree {

    public static class TreeNode{

        Object data;

        TreeNode left;

        TreeNode right;

    }

    public static TreeNode create(){
        TreeNode node1 = new TreeNode();
        node1.data = 1;
        TreeNode node2 = new TreeNode();
        node2.data = 2;
        TreeNode node3 = new TreeNode();
        node3.data = 3;
        TreeNode node4 = new TreeNode();
        node4.data = 4;
        TreeNode node5 = new TreeNode();
        node5.data = 5;
        TreeNode node6 = new TreeNode();
        node6.data = 6;
        TreeNode node7 = new TreeNode();
        node7.data = 7;
        TreeNode node8 = new TreeNode();
        node8.data = 8;
        TreeNode node9 = new TreeNode();
        node9.data = 9;
        TreeNode node10 = new TreeNode();
        node10.data = 10;
        TreeNode node11 = new TreeNode();
        node11.data = 11;
        TreeNode node12 = new TreeNode();
        node12.data = 12;

        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        node4.left = node8;
        node4.right = node9;

        node6.left = node11;

        node7.right = node12;

        node9.left = node10;

        return node1;

    }


    /**
     * 层序遍历，输出：1、2、3、4、5、6、7、8、9、11、12、10
     */
    public static void CTraversal(TreeNode root){

        if(root == null){
            return;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode now = queue.poll();
            System.out.println(now.data);
            if(now.left != null){
                queue.add(now.left);
            }
            if(now.right != null){
                queue.add(now.right);
            }
        }

    }


    /**
     * 依次打印二叉树每层的最大值
     */
    public static void levelMax(TreeNode root){
        // TODO: 2018/4/13
    }


    /**
     * 前序遍历，输出
     */
    public static void QTraversal(TreeNode root){
        if(root == null){
            return;
        }

        System.out.println(root.data);
        QTraversal(root.left);
        QTraversal(root.right);
    }

    /**
     * 中序遍历，输出8，4，10，9，2，5，1，11，6，3，7，12
     */
    public static void ZTraversal(TreeNode root){
        if(root == null){
            return;
        }

        ZTraversal(root.left);
        System.out.println(root.data);
        ZTraversal(root.right);
    }

    /**
     * 后序遍历，输出8,10,9,4,5,2,11,6,12,7,3,1
     */
    public static void HTraversal(TreeNode root){
        if(root == null){
            return;
        }

        HTraversal(root.left);
        HTraversal(root.right);
        System.out.println(root.data);
    }


    public static void main(String[] args){
        TreeNode root = create();
//        System.out.println("层序遍历");
//        CTraversal(root);
//        System.out.println("前序遍历");
//        QTraversal(root);
//        System.out.println("中序遍历");
//        ZTraversal(root);
        System.out.println("中序遍历");
        HTraversal(root);
    }

}
