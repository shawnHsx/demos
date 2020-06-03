package com.semion.demo.Algorithm;

import java.util.*;

/**
 * 使用java 的队列实现栈
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
     *
     * @param val
     */
    public void push(int val) {
        top = val;
        queue.offer(top);
    }

    /**
     * 移除栈顶元素 并返回该元素
     */
    public int pop() {
        int res = top;
        int size = queue.size();
        Queue<Integer> temp = new LinkedList<>();

        // 从队列中移除最后一个元素===>  注意：siz-1 是为了不把最后一个元素加入到temp 队列
        for (int i = 0; i < size - 1; i++) {
            top = queue.remove();
            temp.offer(top);
        }
        queue = temp;
        return res;
    }


    /**
     * 不实用临时队列 使用单队列进行出栈
     *
     * @return
     */
    public int pop2() {
        int size = queue.size();
        //Queue<Integer> temp = new LinkedList<>();
        // 将队列的前siz-1 个元素追加到队列尾部 然后移除队列头部元素
        for (int i = 0; i < size - 1; i++) {
            top = queue.remove();
            queue.offer(top);
        }
        // 移除队头元素并返回
        return queue.remove();
    }

    public int size() {
        return queue.size();
    }


    public static void main(String[] args) {
        String s = "123456789";
        List<List<Integer>> generate = generate(5);
        //System.out.println(generate);


        //getRow(3);
        //myreverse(s);
        String[] ss = {"h", "e", "l", "0"};


        //reverseString(ss);

        HashSet<Character> set = new HashSet<>(
                Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        char[] chars = s.toCharArray();
        //HashSet<Character> set = new HashSet(Arrays.asList('a','e','i','o','u'));
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            while (!set.contains(chars[right])) {
                right--;
            }
            while (!set.contains(chars[left])) {
                left++;
            }
            if (left < right) {
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }
        String s1 = String.valueOf(chars);


    }


    public static void reverseString(String[] s) {
        showStr(0, s);
    }

    public static void showStr(int index, String[] s) {
        if (s == null || index >= s.length) {
            return;
        }
        showStr(index + 1, s);

        System.out.print(s[index]);
    }

    /**
     * 字符串反转--通过数组前后交换位置
     *
     * @param str
     * @return
     */
    public static String myreverse(String str) {
        if (null == str) return null;

        char[] value = str.toCharArray();

        int len = value.length - 1;
        // len 右移1位 （除2）
        for (int i = len >> 1; i >= 0; i--) {
            int k = len - i;
            char ci = value[i];
            char ck = value[k];
            value[i] = ck;
            value[k] = ci;
        }
        String s = new String(value);
        System.out.println(s);
        return s;

    }


    /**
     * 杨辉三角
     *
     * @param numRows 1
     *                1 1
     *                1 2 1
     *                1 3 3 1
     *                1 4 6 4 1
     *                1 5 10 10 5 1
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        //  f(x,j) = 1 where i=j or i =1;
        //  f(x,j) = f(x-1,j-1) + f(x-1,j)
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> rowList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || i == j) {
                    rowList.add(1);
                } else {
                    List<Integer> preRowList = list.get(i - 1);
                    int num = preRowList.get(j) + preRowList.get(j - 1);
                    rowList.add(num);
                }
            }
            list.add(rowList);
        }
        return list;
    }


    public int findRepeatNumber(int[] nums) {
        HashSet set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return nums[i];
            }
        }
        return -1;
    }

    /**
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }
        return -1;
    }

    /***
     * 使用索引与具体值对应关系
     * @param nums
     * @return
     */
    public int findRepeatNumber3(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[i] != nums[nums[i]]) {
                    return nums[i];
                }
                int temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }
        }
        return -1;
    }


}


class MinStack {

    // 数据栈
    private Stack<Integer> data;
    // 辅助栈
    private Stack<Integer> helper;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        data = new Stack<>();
        helper = new Stack<>();
    }

    public void push(int x) {
        data.push(x);
        if (helper.isEmpty() || helper.peek().intValue() > x) {
            helper.push(x);
        }
    }


    public void pop() {
        if (data.pop().intValue() == helper.peek().intValue()) {
            helper.pop();
        }
    }

    public int top() {
        Integer top = data.pop();
        if (top.intValue() == helper.peek().intValue()) {
            helper.pop();
        }
        return top;
    }

    public int getMin() {
        if (!helper.isEmpty()) {
            return helper.peek();
        }
        throw new RuntimeException("栈元素为空");
    }


    public boolean canThreePartsEqualSum(int[] A) {
        int len = A.length;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += A[i];
        }
        //数组所有元素和不能被3整除
        if (sum % 3 != 0) {
            return false;
        }
        int subSum = sum / 3;

        int cnt = 0;
        int currSum = 0;
        for (int i = 0; i < len - 1; i++) {
            currSum += A[i];
            if (currSum == subSum) {
                cnt++;
            }
            if (cnt == 3) {
                return true;
            }
        }
        return false;

    }

    // 解码字符串  s = "3[a]2[bc]" ==> "aaabcbc"
    public String decodeString(String s) {

        LinkedList<Integer> s1 = new LinkedList<>();
        LinkedList<String> s2 = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        int nums = 0;

        for (Character c : s.toCharArray()) {
            if (c == '[') {
                s1.add(nums);
                s2.add(sb.toString());
                nums = 0;
                sb = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = s1.removeLast();
                for (int i = 0; i < cur_multi; i++) tmp.append(sb);
                sb = new StringBuilder(s2.removeLast() + tmp);
            } else if (c > 0 && c < 9) {
                //数字
                nums = nums * 10 + Integer.parseInt(c + "");
            } else {
                // 字母
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public String gcdOfStrings(String str1, String str2) {
        // 假设str1是N个x，str2是M个x，那么str1+str2肯定是等于str2+str1的。
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        // 辗转相除法求gcd。
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }

    // 求最大公约数
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    public static void main(String[] args) {
        int[] arr = {1, 0};
        // int[] arr = {1,7,3,6,5,6};
        //System.out.println(pivotIndex(arr));
        System.out.println(dominantIndex(arr));
    }

    public String gcdOfStrings2(String str1, String str2) {
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }

        return str2.substring(0, mygcd(str1.length(), str2.length()));
    }

    public int mygcd(int a, int b) {
        return b == 0 ? a : mygcd(b, a % b);
    }

    public static int pivotIndex(int[] nums) {
        int leftSum = 0;
        int sum = 0;
        int left = 0;
        for (int num : nums) {
            sum += num;
        }

        while (left < nums.length - 1) {
            if (2 * leftSum == sum - nums[left]) {
                return left;
            }
            leftSum += nums[left];
            left++;
        }
        return -1;
    }

    public static int dominantIndex(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int max = 0, maxsec = 0, maxi = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                maxsec = max;
                maxi = i;
                max = nums[i];
            } else if (nums[i] > maxsec) {
                maxsec = nums[i];
            }
        }
        if (max < 2 * maxsec)
            return -1;
        return maxi;
    }


}
