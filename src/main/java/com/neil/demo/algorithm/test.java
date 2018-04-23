package com.neil.demo.algorithm;

/**
 * Created by Neil on 2018/4/11.
 */
public class test {

    public static void  main(String[] args){
        System.out.println(solution(9,1,3,5,5,7));
        System.out.println(solution(9,1,3,5,5,7));
    }

    public static int solution(int n) {
        int[] d = new int[30];
        int l = 0;
        int p;
        while (n > 0) {
            d[l] = n % 2;
            n /= 2;
            l++;
        }
        for (p = 1; p < 1 + l; ++p) {
            int i;
            boolean ok = true;
            for (i = 0; i < l - p; ++i) {
                if (d[l-1-i] != d[l-1-(i + p)]) {
                    ok = false;
                    break;
                }
            }
            if (ok && p <= l/2) {
                return p;
            }
        }
        return -1;
    }

    /**
     * 给定6个数字求这6个数字所能组成的 最小 有效 时间，24小时制。如2、5、3、1、8、9可以组成的最小有效时间是12：38：59
     * @param A
     * @param B
     * @param C
     * @param D
     * @param E
     * @param F
     * @return
     */
    public static String solution(int A, int B, int C, int D, int E, int F) {
        // write your code in Java SE 8






        return null;
    }
}
