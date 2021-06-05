package com.nullpt.algrothm;

/**
 * 重排链表
 * 0,1,2,3,4,5,6,7,8
 * 0,8,1,7,2,6,3,5,4
 */
public class t2 {

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new Node();
            nodes[i].val = i;
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }

        split(nodes[0]);

        Node head = nodes[0];
        while (head != null) {
            System.out.print(head.val);
            head = head.next;
        }
    }

    static Node split(Node head) {
        Node first = head;
        Node second = head.next;
        //快慢指针找中点
        while (second != null && second.next != null) {
            first = first.next;
            second = second.next.next;
        }
        Node rev = reverse(first.next);
        //中点之后断
        first.next = null;
        return merge(head, rev);
    }

    //反转
    private static Node reverse(Node next) {
        Node f = next;
        Node s = next.next;
        while (s != null) {
            Node t = s.next;
            s.next = f;
            f = s;
            s = t;
        }
        //注意头节点next置空
        next.next = null;
        return f;
    }

    //合并
    private static Node merge(Node head, Node rev) {
        Node f = head;
        while (rev != null) {
            Node t = f.next;
            f.next = rev;
            Node t2 = rev.next;
            rev.next = t;
            //分别后移
            f = t;
            rev = t2;
        }
        return f;
    }

    static class Node {
        int val;
        Node next;
    }

}
