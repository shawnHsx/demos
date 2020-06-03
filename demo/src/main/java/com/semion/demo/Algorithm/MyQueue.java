package com.semion.demo.Algorithm;

import java.util.Stack;

/**
 * 双栈实现队列
 */
public class MyQueue {

    Stack<Integer> stack1 = null;
    Stack<Integer> stack2 = null;

    int size = 0;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void appendTail(int value) {
        size++;
        stack1.push(value);
    }

    public int deleteHead() {
        if(stack2.empty() && stack1.empty()){
            return  -1;
        }
        if(stack2.empty()){
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        size--;
        return stack2.pop();
    }
}
