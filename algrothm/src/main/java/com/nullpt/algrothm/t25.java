package com.nullpt.algrothm;

/**
 * 删除重读元素，留一个
 * 3,4,4,5
 * 3,4,5
 */
public class t25 {

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
                    head.right = temp;
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
