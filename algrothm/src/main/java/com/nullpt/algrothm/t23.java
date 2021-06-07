package com.nullpt.algrothm;

import java.util.Random;

/**
 * 链表划分
 * 1，4，3，2，5，2   x=3
 * res = 1，2，2，4，3，5
 */
public class t23 {

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new Node(new Random().nextInt() % 10);
            if (i > 0) {
                nodes[i - 1].right = nodes[i];
            }
        }

        int x = 6;

        //输出原数据，用于对比
        Node temp = nodes[0];
        while (temp != null) {
            System.out.print(temp.val);
            System.out.print(",");
            temp = temp.right;
        }
        System.out.println();

        //算法
        Node head = nodes[0];
        Node first = null;
        Node firstH = null;
        Node last = null;
        Node lastH = null;
        while (head != null) {
            if (head.val < x) {
                if (first == null) {
                    first = head;
                    firstH = first;
                } else {
                    first.right = head;
                    first = head;
                }
            } else {
                if (last == null) {
                    last = head;
                    lastH = last;
                } else {
                    last.right = head;
                    last = head;
                }
            }
            head = head.right;
        }

        Node res;
        if (first != null) {
            first.right = lastH;
            last.right = null;
            res = firstH;
        } else {
            res = lastH;
            last.right = null;
        }

        //输出结果
        while (res != null) {
            System.out.print(res.val);
            System.out.print(",");
            res = res.right;
        }
    }
}
