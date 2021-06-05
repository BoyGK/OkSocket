package com.nullpt.algrothm;

import java.util.Stack;

/**
 * 之字遍历
 */
public class t14 {

    public static void main(String[] args) {
        itor(new Node());
    }

    static void itor(Node root) {
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.add(root);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                Node top = stack1.pop();
                System.out.println(top.val);
                stack2.push(top.left);
                stack2.push(top.right);
            }
            while (!stack2.isEmpty()) {
                Node top = stack2.pop();
                System.out.println(top.val);
                stack1.push(top.right);
                stack1.push(top.left);
            }
        }
    }
}
