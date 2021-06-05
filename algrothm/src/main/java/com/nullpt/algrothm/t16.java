package com.nullpt.algrothm;


import java.util.Stack;

/**
 * 判断二叉树对称
 */
public class t16 {

    public static void main(String[] args) {

        System.out.println(isDC(new Node()));
    }

    static boolean isDC(Node root) {
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left != root.right) return false;
        stack1.push(root.left);
        stack2.push(root.right);
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            Node top1 = stack1.pop();
            Node top2 = stack2.pop();
            if (top1 != top2) return false;
            if (top1.left != null) stack1.push(top1.left);
            if (top2.right != null) stack2.push(top1.right);
            if (top1.right != null) stack1.push(top1.right);
            if (top2.left != null) stack2.push(top1.left);
        }
        if (stack1.size() != stack2.size()) return false;
        return true;
    }
}
