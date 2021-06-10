package com.nullpt.algrothm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 最长无重复子数组
 * [1,2,3,3,4,5,6,7,7]
 * [3,4,5,6,7]
 */
public class t41 {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 2, 4, 3, 6, 7, 7};
        int l = 0, r = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < arr.length; j++) {
            if (map.containsKey(arr[j])) {
                i = map.get(arr[j]) + 1;
            }
            map.put(arr[j], j);

            if (j - i > r - l) {
                l = i;
                r = j;
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, l, r + 1)));
    }
}
