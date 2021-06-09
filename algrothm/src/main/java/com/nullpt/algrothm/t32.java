package com.nullpt.algrothm;

/**
 * 求平方根
 * 向下取整
 */
public class t32 {

    public static void main(String[] args) {

        System.out.println(sqrt(120));
    }

    static int sqrt(int x) {
        if (x <= 0) {
            return 0;
        }
        int l = 1;
        int r = x;
        while (l < r) {
            int mid = (l + r) / 2;
            if (mid * mid <= x && (mid + 1) * (mid + 1) > x) {
                return mid;
            } else if (mid * mid > x) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return l;
    }
}
