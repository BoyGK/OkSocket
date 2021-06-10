package com.nullpt.algrothm;

/**
 * 合并有序链表
 * <p>
 * <p>
 * weiwan
 */
public class t33 {

    public static void main(String[] args) {
        Node[] a = new Node[5];
        for (int i = 0; i < 5; i++) {
            a[i] = new Node(i * 2);
            if (i > 0) {
                a[i - 1].right = a[i];
            }
        }
        Node[] b = new Node[5];
        for (int i = 0; i < 5; i++) {
            b[i] = new Node(i * 2 + 1);
            if (i > 0) {
                b[i - 1].right = b[i];
            }
        }

        merge(a[0], b[0]);

        while (a[0] != null) {
            System.out.println(a[0].val);
            a[0] = a[0].right;
        }
    }

    private static void merge(Node root, Node node) {
        Node head = root;
        while (head != null && node != null) {
            if (head.val < node.val) {
                Node t1 = head.right;
                Node t2 = node.right;
                head.right = node;
                node.right = t1;
                head = t1.right;
                t1.right = t2;
                node = t2.right;
            } else {
                Node t1 = head.right;
                Node t2 = node.right;
                node.right = head;
                head.right = t2;
                node = t2.right;
                t2.right = t1;
                head = t1.right;
            }

            if (head.right == null) {
                head.right = node;
                break;
            }
            if (node.right == null) {
                break;
            }
        }
    }
}
