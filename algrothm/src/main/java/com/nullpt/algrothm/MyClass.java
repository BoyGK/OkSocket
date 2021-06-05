package com.nullpt.algrothm;

import java.util.ArrayList;

public class MyClass {

    public static void main(String[] args) {
        fun(new C<String>(new ArrayList<String>()) {});
    }

    static void fun(C<?> c) {
        System.out.println(c.t.getClass().getTypeName());
    }

}