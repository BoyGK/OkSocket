package com.nullpt.sockettest;

import java.util.Arrays;

public class MM {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        fun(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void fun(int[] arr) {
        arr[0] = 0;
    }

}
