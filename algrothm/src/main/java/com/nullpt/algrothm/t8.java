package com.nullpt.algrothm;

import java.util.ArrayList;

/**
 * 二叉树
 * 找出所有根节点到叶子节点的值的和等于num
 * <p>
 * 思路：先序遍历
 * 遍历每一条链，值相等的记录
 */
public class t8 {

    public static void main(String[] args) {

        Node root = new Node();

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        preSum(root, res, path, 8/*num*/, 0/*当前值*/);
    }

    private static void preSum(Node root, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> path, int num, int cur) {
        if (root == null) {
            return;
        }
        cur += root.val;
        path.add(root.val);
        if (root.left == null && root.right == null && cur == num) {
            res.add(path);
        }
        preSum(root.left, res, path, num, cur);
        preSum(root.right, res, path, num, cur);
    }

    static class Node {
        int val;
        Node left;
        Node right;
    }
}
