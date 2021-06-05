package com.nullpt.algrothm;

/**
 * 最长回文串
 * dp
 */
public class t17 {

    public static void main(String[] args) {

        String str = "sdf135ff531sdf";
        int n = str.length();

        int maxLen = 1;
        boolean[][] dp = new boolean[n][n];
        for (int right = 1; right < n; right++) {
            for (int left = 0; left < right; left++) {
                if (str.charAt(left) != str.charAt(right)) {
                    continue;
                }

                if (left == right) {
                    dp[left][right] = true;
                } else if (right - left <= 2) {
                    dp[left][right] = true;
                } else {
                    dp[left][right] = dp[left + 1][right - 1];
                }

                if (dp[left][right]) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            }
        }

        System.out.println(maxLen);

    }
}
