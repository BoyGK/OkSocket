package com.nullpt.algrothm;

import java.util.Arrays;

/**
 * 前中序列，重建二叉树
 * 前序0=中序分割
 * 分割后递归
 */
public class t12 {

    public static void main(String[] args) {

        int[] pre = new int[]{1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = new int[]{4, 7, 2, 1, 5, 3, 8, 6};

        Node root = rebuild(pre, in);
        System.out.println(root);
    }

    static Node rebuild(int[] pre, int[] in) {
        if (pre.length == 0 && in.length == 0) {
            return null;
        }
        Node root = new Node(pre[0]);
        for (int i = 1; i < in.length; i++) {
            if (pre[0] == in[i]) {
                root.left = rebuild(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                root.right = rebuild(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
                break;
            }
        }
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
