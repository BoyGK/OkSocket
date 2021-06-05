package com.nullpt.algrothm;

/**
 * 顺时针旋转矩阵
 * res = i.j颠倒
 * 纵坐标反着跑
 */
public class t18 {

    public static void main(String[] args) {
        int[][] mex = new int[][]{
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5}
        };

        int[][] res = new int[mex[0].length][mex.length];

        for (int i = 0; i < mex.length; i++) {
            for (int j = 0; j < mex[0].length; j++) {
                res[j][mex.length - i - 1] = mex[i][j];
            }
        }

        for (int[] re : res) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(re[j]);
                System.out.print(",");
            }
            System.out.println();
        }

    }
}
