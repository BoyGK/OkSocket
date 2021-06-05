package com.nullpt.algrothm;

/**
 * 二叉树求和
 * 1
 * 2   3
 * 4   5   6   7
 * result = 124+125+136+137
 */
public class t5 {

    public static void main(String[] args) {
        Node treeRoot = new Node();
        System.out.println(preSum(treeRoot, 0));
    }

    static int preSum(Node root, int sum) {
        if (root == null) {
            return 0;
        }
        sum = sum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return 0;
        }
        return preSum(root.left, sum) + preSum(root.right, sum);
    }

    static class Node {
        int val;
        Node left;
        Node right;
    }

}
