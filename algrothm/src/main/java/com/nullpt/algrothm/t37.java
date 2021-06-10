package com.nullpt.algrothm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 合并区间
 * [10,20],[15,30]
 * [10,30]
 */
public class t37 {

    public static void main(String[] args) {
        ArrayList<Integer[]> list = new ArrayList<Integer[]>() {{
            add(new Integer[]{10, 15});
            add(new Integer[]{15, 40});
            add(new Integer[]{30, 60});
            add(new Integer[]{70, 80});
        }};

        list.sort(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] t1, Integer[] t2) {
                return t1[0].compareTo(t2[0]);
            }
        });

        for (int i = 1; i < list.size(); i++) {
            int last = list.get(i - 1)[1];
            int curr = list.get(i)[0];
            if (curr <= last) {
                list.get(i - 1)[1] = list.get(i)[1];
                list.remove(i);
                i--;
            }
        }

        for (Integer[] integers : list) {
            System.out.println(Arrays.toString(integers));
        }
    }
}
