package com.nullpt.algrothm;

/**
 * 完全删除重复链表
 * 3,4,4,5
 * 3,5
 */
public class t24 {

    public static void main(String[] args) {
        Node a = new Node(3);
        Node b = new Node(4);
        Node c = new Node(4);
        Node d = new Node(5);
        a.right = b;
        b.right = c;
        c.right = d;
        d.right = null;


        //因为有序，所以相同的挨着，直接删除重复元素
        Node head = a;
        Node root = null;
        Node rootH = null;
        while (head != null) {
            int val = head.val;
            Node temp = head.right;
            if (temp != null) {
                while (temp.val == val) {
                    temp = temp.right;
                }
                if (head.right != temp) {
                    head = temp;
                }
            }

            if (root == null) {
                root = head;
                rootH = root;
            } else {
                root.right = head;
                root = head;
            }

            head = head.right;
        }

        while (rootH != null) {
            System.out.println(rootH.val);
            rootH = rootH.right;
        }
    }
}
