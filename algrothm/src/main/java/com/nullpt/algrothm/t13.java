package com.nullpt.algrothm;

/**
 * 二叉树最大深度
 */
public class t13 {

    public static void main(String[] args) {

        System.out.println(postNum(new Node()));
    }

    static int postNum(Node root) {
        if (root == null) {
            return 0;
        }
        int left = postNum(root.left) + 1;
        int right = postNum(root.right) + 1;
        return Math.max(left, right);
    }

    static class Node {
        Node left;
        Node right;
    }
}
