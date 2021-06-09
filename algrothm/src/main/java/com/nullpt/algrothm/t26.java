package com.nullpt.algrothm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 括号生成
 * n=3
 * ()()(),(())(),((())),()(()),((),())
 */
public class t26 {

    public static void main(String[] args) {
        ArrayList<String> res = new ArrayList<>();
        backStr("", 0, 0, 3, res);
        System.out.println(Arrays.toString(res.toArray()));
    }

    private static void backStr(String s, int l, int r, int n, ArrayList<String> res) {
        if (s.length() == n * 2) {
            res.add(s);
        }
        if (l < n) {
            backStr(s + "(", l + 1, r, n, res);
        }
        if (r < l) {
            backStr(s + ")", l, r + 1, n, res);
        }
    }


}
