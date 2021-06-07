package com.nullpt.algrothm;

import java.util.Arrays;

/**
 * 合并有序数组
 */
public class t22 {

    public static void main(String[] args) {
        int m = 5;
        int[] arr1 = new int[]{1, 3, 5, 6, 8, 0, 0, 0, 0, 0};//m=5
        int n = 5;
        int[] arr2 = new int[]{2, 4, 4, 5, 7};//n=5

        int index = m + n - 1;
        m--;
        n--;
        while (m >= 0 || n >= 0) {
            if (m >= 0 && n >= 0) {
                arr1[index--] = arr1[m] > arr2[n] ? arr1[m--] : arr2[n--];
            } else if (m >= 0) {
                arr1[index--] = arr1[m--];
            } else {
                arr1[index--] = arr2[n--];
            }
        }

        System.out.println(Arrays.toString(arr1));
    }
}
