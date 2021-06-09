package com.nullpt.algrothm;

import java.util.Arrays;

/**
 * 矩阵查找
 */
public class t29 {

    public static void main(String[] args) {

        int[][] metrx = new int[][]{
                {1, 3, 5, 7},
                {10, 12, 15, 16},
                {21, 23, 25, 27},
        };
        int target = 15;
        int l = 0;
        int r = metrx[0].length - 1;
        int t = 0;
        int b = metrx.length - 1;
        int i = 0;
        for (; i <= b; i++) {
            if (metrx[i][r] >= target) {
                break;
            }
        }
        int j = Arrays.binarySearch(metrx[i], target);
        System.out.println(i + "," + j);
    }
}
