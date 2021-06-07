package com.nullpt.algrothm;

/**
 * 子数组最大累加和
 */
public class t19 {

    public static void main(String[] args) {
        int[] array = new int[]{1, -2, 3, 5, -2, 6, -1};

        int m = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] = Math.max(array[i], array[i - 1] + array[i]);
            m = Math.max(m, array[i]);
        }

        System.out.println(m);
    }
}
