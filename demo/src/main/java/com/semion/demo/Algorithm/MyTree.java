package com.semion.demo.Algorithm;


import java.util.*;

public class MyTree {


    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(0);

    }


    // 使用链表 前序遍历 根——>左->右
    public List<Integer> preorderTraversal(TreeNode root) {
        // 记录元素遍历的顺序
        List<Integer> res = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();

        stack.add(root);
        while (!stack.isEmpty()) {
            // 移除链表的最后一个元素
            TreeNode node = stack.pollLast();
            res.add(node.val);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        return res;

    }

    /**
     * 前序遍历：根——>左->右
     * 使用Stack，使用栈，先入栈右节点，后入栈左节点
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        // 记录元素遍历的顺序
        List<Integer> res = new LinkedList<>();
        if (root == null)
            return res;
        // 栈记录
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            // 出栈
            TreeNode node = stack.pop();
            res.add(node.val);
            //1. 右先入栈
            if (node.right != null) {
                stack.add(node.right);
            }
            // 2.左入栈
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        return res;
    }


    /**
     * 后序遍历：左--右--跟
     * 使用栈模拟
     * @param root
     * @return 后序遍历的顺序是 左 -> 右 -> 根
     * 前序遍历的顺序是 根 -> 左 -> 右，所以我们也可以轻松的写出 根 -> 右 -> 左 的代码。
     * 然后把 根 -> 右 -> 左 逆序，就是 左 -> 右 -> 根，也就是后序遍历了。
     */
    public List<Integer> postOrderIteration(TreeNode root) {
        // 记录元素遍历的顺序
        List<Integer> res = new LinkedList<>();
        if (root == null) return res;
        // 栈记录
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            // 出栈
            TreeNode node = stack.pop();
            res.add(node.val);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        // 对结果进行反转
        Collections.reverse(res);
        return res;
    }


    /***
     * 中序遍历：左--根--右
     * @param root
     * 开始的话，也是不停的往左子树走，然后直到为 null。
     * 我们应该把节点 peek 出来，然后判断一下当前根节点的右子树是否为空或者是否是从右子树回到的根节点。
     */
    public List<Integer> inOrderIteration(TreeNode root) {
        List < Integer > res = new ArrayList < > ();
        Stack < TreeNode > stack = new Stack < > ();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    /**
     * 递归实现中序列遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root,res);
        return res;

    }

    public void helper(TreeNode root, List < Integer > res) {
        if(root==null) return;
        if(root.left!=null){
            helper(root.left,res);
        }
        res.add(root.val);
        if(root.right!=null) {
            helper(root.right, res);
        }
    }

    /**
     *  中序列遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal3(TreeNode root) {

        List < Integer > res = new ArrayList < > ();
        Stack < TreeNode > stack = new Stack < > ();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }


}


class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}
