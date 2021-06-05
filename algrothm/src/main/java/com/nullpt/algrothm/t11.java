package com.nullpt.algrothm;

/**
 * 有序数组转平衡二叉树
 */
public class t11 {

    public static void main(String[] args) {
        int[] array = new int[]{0, 1, 2, 3, 8, 9};
        Node root = preNode(array, 0, array.length - 1);
        System.out.println(root);
    }

    static Node preNode(int[] num, int l, int r) {
        if (l > r) {
            return null;
        }
        int mid = l + (r - l + 1) / 2;
        Node root = new Node(num[mid]);
        root.left = preNode(num, l, mid - 1);
        root.right = preNode(num, mid + 1, r);
        return root;
    }

    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }
}
