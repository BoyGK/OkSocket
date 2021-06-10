package com.nullpt.algrothm;

/**
 * 求路径
 * 从左上到右下
 */
public class t34 {

    public static void main(String[] args) {
        int n = 3;
        int m = 3;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][1] = 1;
        }
        for (int i = 1; i <= m; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= m; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        System.out.println(dp[n][m]);
    }
}
