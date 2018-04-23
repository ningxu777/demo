package com.neil.demo.algorithm;

/**
 * Created by Neil on 2018/3/31.
 */
public class Sort {


    /**
     * 冒泡
     */
    public static void bubbleSort(int[] arr){
        int length = arr.length;
        for(int i = 0; i < length; i++){

            for(int j = 0;j < length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int inum = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = inum;
                }
            }

        }
    }

    /**
     * 快速排序
     */

    public static int[] quickSort(int[] arr,int from,int to){
        if (from < to) {
            int point = from;
            int index = point+1;
            for(int i = index;i<=to;i++){
                if(arr[i]<arr[point]){
                    swap(arr,i,index);
                    index++;
                }
            }
            //交换第一个和index-1个
            index = index-1;
            swap(arr,point,index);


            quickSort(arr,from,index-1);
            quickSort(arr,index+1,to);
        }
        return arr;
    }

    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args){
        int[] arr = {15,3,6,2,4,7,9,8,91,23};
        //冒泡
//        bubbleSort(arr);

        //快速排序
        quickSort(arr,0,arr.length-1);

        for(int i = 0; i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }

}
