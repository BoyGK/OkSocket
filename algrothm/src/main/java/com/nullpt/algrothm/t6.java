package com.nullpt.algrothm;


/**
 * 二叉树最大路径和
 * 1
 * 2   3
 * 4   5   6   7
 * result = 5+2+1+3+7
 * <p>
 * 核心思想，先算左 、右
 * 记录左中右能不能成为最大 , 计算左中或右中作为返回的最大路径值
 */
public class t6 {

    static int maxSum = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Node treeRoot7 = new Node(7, null, null);
        Node treeRoot6 = new Node(6, null, null);
        Node treeRoot5 = new Node(5, null, null);
        Node treeRoot4 = new Node(4, null, null);
        Node treeRoot3 = new Node(3, treeRoot6, treeRoot7);
        Node treeRoot2 = new Node(2, treeRoot4, treeRoot5);
        Node treeRoot1 = new Node(1, treeRoot2, treeRoot3);

        postSum(treeRoot1);
        System.out.println(maxSum);
    }

    private static int postSum(Node root) {
        if (root == null) {
            return 0;
        }
        int left = postSum(root.left);
        int right = postSum(root.right);
        int curVal = root.val;
        if (left > 0) {
            curVal += left;
        }
        if (right > 0) {
            curVal += right;
        }
        maxSum = Math.max(maxSum, curVal);
        return Math.max(root.val, Math.max(left, right) + root.val);
    }

    static class Node {

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        int val;
        Node left;
        Node right;
    }
}
