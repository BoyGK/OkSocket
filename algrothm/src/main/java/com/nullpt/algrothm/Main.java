package com.nullpt.algrothm;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        int[] arr = new int[]{1, 2, 3};
//        fun(arr);
//        System.out.println(Arrays.toString(arr));

        A a = new A() {
            @Override
            void a() {
                System.out.println("123");
            }
        };
    }

    static void fun(int[] arr) {
        arr = null;
    }

    static abstract class A {
        public A() {
            a();
        }

        abstract void a();
    }
}
