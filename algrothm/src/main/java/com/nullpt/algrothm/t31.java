package com.nullpt.algrothm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第一个只出现一次的字符串
 */
public class t31 {

    public static void main(String[] args) {
        String srt = "google";
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < srt.length(); i++) {
            if (map.containsKey(srt.charAt(i) + "")) {
                map.put(srt.charAt(i) + "", 1);
            } else {
                map.put(srt.charAt(i) + "", -i);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() <= 0) {
                System.out.println(-entry.getValue());
                return;
            }
        }
        System.out.println(-1);
    }
}
