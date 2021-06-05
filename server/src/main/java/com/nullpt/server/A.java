package com.nullpt.server;

public class A {


    public static void main(String[] args) {
        C c = new C();
    }

    static class B {

        private B() {
            System.out.println(123);
        }
    }

    static class C extends B {

        protected C() {
        }
    }

}
