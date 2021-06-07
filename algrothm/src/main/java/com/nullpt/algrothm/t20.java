package com.nullpt.algrothm;

import java.util.ArrayList;

/**
 * 数字字符串转换为IP地址
 * 深搜，按条件拆分字符串
 * 回溯，保证下一次是正确的字符串开始的
 */
public class t20 {

    public static void main(String[] args) {
        String str = "25525523133";
        ArrayList<String> res = new ArrayList<>();
        dfs(str, res, new ArrayList<>());
        for (String re : res) {
            System.out.println(re);
        }
    }

    private static void dfs(String str, ArrayList<String> res, ArrayList<String> temp) {
        if (str.isEmpty() && temp.size() == 4) {
            res.add(convert(temp));
        }
        for (int i = 1; i <= str.length(); i++) {
            if (i > 1 && str.charAt(0) == '0') break;
            int p = Integer.parseInt(str.substring(0, i));
            if (p > 255) break;
            temp.add(str.substring(0, i));
            dfs(str.substring(i), res, temp);
            temp.remove(temp.size() - 1);
        }
    }

    private static String convert(ArrayList<String> temp) {
        StringBuilder res = new StringBuilder();
        for (String s : temp) {
            res.append(s).append(",");
        }
        return res.substring(0, res.length() - 1);
    }
}
