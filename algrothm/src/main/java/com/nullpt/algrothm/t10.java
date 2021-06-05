package com.nullpt.algrothm;

import java.math.BigInteger;

/**
 * 大叔乘法
 */
public class t10 {

    public static void main(String[] args) {

        String a = "123456789999999";
        String b = "999999987654321";
        System.out.println(new BigInteger(a).multiply(new BigInteger(b)));
    }

    static String multiply(String a, String b) {
        return "";
        /*
        类似于大数加法，按数组位计算乘积，得到两个大数，在进行大叔相加
        注意第二个大数末尾补零（从10位开始）
         */
    }


    static String add(String a, String b) {
        try {
            //错误数返回
            new BigInteger(a);
            new BigInteger(b);
        } catch (Exception ignore) {
            return "0";
        }

        int temp = 0;
        String num1 = a.length() >= b.length() ? a : b;
        String num2 = a.length() < b.length() ? a : b;
        StringBuilder res = new StringBuilder();
        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = Integer.parseInt(num1.charAt(i) + "");
            int n2 = 0;
            if (i >= num1.length() - num2.length()) {
                n2 = Integer.parseInt(num2.charAt(i - (num1.length() - num2.length())) + "");
            }
            int t = temp + n1 + n2;
            temp = t > 9 ? 1 : 0;
            int r = t > 9 ? t - 10 : t;
            res.insert(0, r);
        }

        if (temp > 0) {
            res.insert(0, temp);
        }

        return res.toString();

    }
}
