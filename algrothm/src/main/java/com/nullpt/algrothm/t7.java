package com.nullpt.algrothm;

/**
 * 股票买入的最佳时机
 * 贪心
 * 每天都能计算截至目前的最佳状态
 */
public class t7 {

    public static void main(String[] args) {

        //int[] array = new int[]{1, 4, 2};
        int[] array = new int[]{2, 1, 3, 4, 3};
        int min = array[0];
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) min = array[i];
            max = Math.max(max, array[i] - min);
        }

        System.out.println(max);

    }
}
