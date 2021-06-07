package com.nullpt.algrothm;

/**
 * 指定区间反转链表
 */
public class t21 {

    public static void main(String[] args) {

        Node[] nodes = new Node[10];
        for (int i = 0; i < 10; i++) {
            nodes[i] = new Node(i);
            if (i > 0) {
                nodes[i - 1].right = nodes[i];
            }
        }

        Node head = nodes[0];
        head = reverse(head, 3, 8);

        while (head != null) {
            System.out.println(head.val);
            head = head.right;
        }
    }

    private static Node reverse(Node head, int s, int e) {
        Node per = head;
        Node last = head;
        Node perPer = null;
        Node lastLast = head.right;
        while (s-- > 0) {
            per = per.right;
            if (perPer == null) {
                perPer = head;
            } else {
                perPer = perPer.right;
            }
        }
        while (e-- > 0) {
            last = last.right;
            lastLast = lastLast.right;
        }
        last.right = null;
        Node mid = reverse(per);
        perPer.right = mid;
        while (mid.right != null) {
            mid = mid.right;
        }
        mid.right = lastLast;
        return head;
    }

    private static Node reverse(Node root) {
        Node per = root;
        Node last = root.right;
        while (last != null) {
            Node t = last.right;
            last.right = per;
            per = last;
            last = t;
        }
        root.right = null;
        return per;
    }

}
