package com.nullpt.algrothm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 重复数字全排列
 */
public class t42 {

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 1, 2};
        List<String> set = new ArrayList<>();
        dfs(set, arr, 0);

        for (String s : set) {
            System.out.println(s);
        }

    }

    private static void dfs(List<String> set, Integer[] arr, int idx) {
        if (idx == arr.length - 1) {
//            System.out.println(Arrays.toString(arr));
            set.add(Arrays.toString(arr));
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            if (i != idx && arr[i].equals(arr[idx])) continue;
            swap(arr, i, idx);
            dfs(set, arr, idx + 1);
            swap(arr, i, idx);
        }
    }

    private static void swap(Integer[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
}
