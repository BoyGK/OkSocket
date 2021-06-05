package com.nullpt.algrothm;

import java.util.ArrayList;

/**
 * 二叉树
 * 判断存不存在所有根节点到叶子节点的值的和等于num
 * <p>
 * 思路：先序遍历 同t8
 */
public class t9 {

    public static void main(String[] args) {

        Node root = new Node();

        System.out.println(preSum(root, 8/*num*/, 0/*当前值*/));
    }

    private static boolean preSum(Node root, int num, int cur) {
        if (root == null) {
            return false;
        }
        cur += root.val;
        if (root.left == null && root.right == null && cur == num) {
            return true;
        }
        return preSum(root.left, num, cur) || preSum(root.right, num, cur);
    }

    static class Node {
        int val;
        Node left;
        Node right;
    }
}
