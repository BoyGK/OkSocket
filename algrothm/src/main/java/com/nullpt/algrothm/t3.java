package com.nullpt.algrothm;

/**
 * 找链表环节点
 */
public class t3 {

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new Node();
            nodes[i].val = i;
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }
        nodes[9].next = nodes[2];
        Node head = nodes[0];

        //快慢指针找相等
        Node f = head;
        Node s = head.next;
        while (f != null && s.next != null && f != s) {
            f = f.next;
            s = s.next.next;
        }

        System.out.println(f.val);

        //同时走找环节点
        s = head;
        while (s != f.next) {
            s = s.next;
            f = f.next;
        }

        System.out.println(s.val);
    }

    static class Node {
        int val;
        Node next;
    }

}
