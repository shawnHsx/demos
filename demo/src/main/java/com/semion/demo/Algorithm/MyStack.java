package com.semion.demo.Algorithm;

import java.util.*;

/**
 *  使用java 的队列实现栈
 */
public class MyStack {

    // 队列
    private Queue<Integer> queue;
    // 栈顶元素
    int top;

    public MyStack() {
        queue = new LinkedList<>();
    }

    /**
     * 入栈
     * @param val
     */
    public void push(int val){
        top = val;
        queue.offer(top);
    }

    /**
     *  移除栈顶元素 并返回该元素
     */
    public int pop(){
        int res = top;
        int size = queue.size();
        Queue<Integer> temp = new LinkedList<>();

        // 从队列中移除最后一个元素===>  注意：siz-1 是为了不把最后一个元素加入到temp 队列
        for (int i = 0; i < size-1 ; i++) {
             top = queue.remove();
             temp.offer(top);
        }
        queue = temp;
        return res;
    }


    /**
     * 不实用临时队列 使用单队列进行出栈
     * @return
     */
    public int pop2(){
        int size = queue.size();
        //Queue<Integer> temp = new LinkedList<>();
        // 将队列的前siz-1 个元素追加到队列尾部 然后移除队列头部元素
        for (int i = 0; i < size-1 ; i++) {
            top = queue.remove();
            queue.offer(top);
        }
        // 移除队头元素并返回
        return queue.remove();
    }

    public int size(){
        return queue.size();
    }



    public static void main(String[] args) {
        String s = "123456789";
        List<List<Integer>> generate = generate(5);
        //System.out.println(generate);


        getRow(3);
        //myreverse(s);
        String[] ss = {"h","e","l","0"};


        //reverseString(ss);

        HashSet<Character> set = new HashSet<>(
                Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        char[] chars = s.toCharArray();
        //HashSet<Character> set = new HashSet(Arrays.asList('a','e','i','o','u'));
        int left =0; int right = chars.length-1;

        while (left<right){
            while (!set.contains(chars[right])){
                right--;
            }
            while (!set.contains(chars[left]) ){
               left++;
            }
            if(left<right){
                char temp = chars[left];
                chars[left]= chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }
        String s1 = String.valueOf(chars);


    }



    public static void reverseString(String[] s) {
        showStr(0,s);
    }

    public static void showStr(int index,String[] s){
        if(s==null || index>= s.length){
            return;
        }
        showStr(index+1,s);

        System.out.print(s[index]);
    }

    /**
     * 字符串反转
     * @param str
     * @return
     */
    public static  String myreverse(String str){
        if(null == str) return null;

        char[] value = str.toCharArray();

        int len = value.length-1;
        // len 右移1位 （除2）
        for(int i=len>>1;i>=0;i--){
            int k = len-i;
            char ci = value[i];
            char ck = value[k];
            value[i]= ck;
            value[k] = ci;
        }
        String s = new String(value);
        System.out.println(s);
        return s;

    }


    /**
     * 杨辉三角
     * @param numRows
     *           1
     *           1 1
     *           1 2 1
     *           1 3 3 1
     *           1 4 6 4 1
     *           1 5 10 10 5 1
     *
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        //  f(x,j) = 1 where i=j or i =1;
        //  f(x,j) = f(x-1,j-1) + f(x-1,j)
        List<List<Integer>> list = new ArrayList<>();
        for(int i=0;i<numRows;i++) {
            List<Integer>   rowList = new ArrayList<>();
            for (int j = 0; j <=i; j++) {
                if(j==0 || i==j){
                    rowList.add(1);
                }else {
                    List<Integer>  preRowList = list.get(i-1);
                    int num = preRowList.get(j) + preRowList.get(j - 1);
                    rowList.add(num);
                }
            }
            list.add(rowList);
        }
        return list;
    }

    public static List<Integer> getRow(int rowIndex) {
        if(rowIndex<0) return null;
        List<List<Integer>> list = new ArrayList<>();
        for(int i=0;i<rowIndex;i++) {
            List<Integer>   rowList = new ArrayList<>();
            for (int j = 0; j <=i; j++) {
                if(j==0 || i==j){
                    rowList.add(1);
                }else {
                    List<Integer>  preRowList = list.get(i-1);
                    int num = preRowList.get(j) + preRowList.get(j - 1);
                    rowList.add(num);
                }
            }
            list.add(rowList);
        }
        return list.get(rowIndex-1);
    }

   /* public List<Integer>  getRow(int numRows){
        List<List<Integer>> list = new ArrayList<>();







    }*/

}
