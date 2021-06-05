package com.nullpt.algrothm;

/**
 * 判断链表是否有环
 */
public class t4 {

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new Node();
            nodes[i].val = i;
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }
        //nodes[9].next = nodes[0];
        Node head = nodes[0];

        System.out.println(jusband(head));


    }

    private static boolean jusband(Node head) {
        Node f = head;
        Node s = f.next;
        while (f != null && s.next != null) {
            f = f.next;
            s = s.next.next;
            if (f == s) {
                return true;
            }
        }
        return false;
    }

    static class Node {
        int val;
        Node next;
    }

}
