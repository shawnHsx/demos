package com.semion.demo.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Matrix {


    public static void main(String[] args) {

        //printMatrix();

        int target = 2;
        int[][] matrix={
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}
        };
        int[][] matrix2={
                {1,4},
                {2,5},
        };

        my3(matrix);
       /* System.out.println( searchMatrix2(matrix2,target));
        int[][] c ={{-5}};
        searchMatrix4(c,-5);

        //reversePrint2();
        int i = numWays(7);
        System.out.println(i);
        int[] ar = {4,5,6,7,0,2,3};
        search2(ar,0);*/

    }

    private static void printMatrix() {
        int target = 6;
        int[][] matrix={
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}
               };
        // 行
        int m = matrix.length;
        // 列
        int n = matrix[0].length;
        System.out.println("行："+m +"列："+n);

        System.out.println(matrix[0][1]);
        System.out.println(matrix[0][0]);

        for (int i = 0; i < m; i++) {
            int[] numsRow = matrix[i];
            for (int j = 0; j < n; j++) {
                int val = numsRow[j];
                if(val==target){
                    System.out.println("found target element");
                }
            }
        }
    }

    /*
        编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
        每行中的整数从左到右按升序排列。
        每行的第一个整数大于前一行的最后一个整数。
        提示：使用二分查找
        注意到输入的 m x n 矩阵可以视为长度为 m x n的有序数组。
        left = 0 和 right = m x n - 1
        选取虚数组最中间的序号作为中间序号: pivot_idx = (left + right) / 2。
        由于该虚数组的序号可以由下式方便地转化为原矩阵中的行和列 (我们当然不会真的创建一个新数组) ，该有序数组非常适合二分查找。
        row = idx / n ， col = idx % n
        比较 target 与 matrix[row][col] 确定在哪一半查找
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length ==0) return false;
        int rows = matrix.length;
        int cols = matrix[0].length;

        int left = 0;
        int right = rows*cols -1;

        while (left<=right){
            // 虚拟数组中间序号
            int  idx = (left+right)/2;
            // 对应原矩阵中的位置
            int idx_element = matrix[idx/cols][idx%cols];
            if(idx_element == target){
                return true;
            }
            if(target<idx_element){// 在左半部分继续查找
                right = idx-1;
            }else{
                //在右边查找
                left = idx+1;
            }
        }
        return false;
    }
    public static boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return false;
        int begin, mid, end;
        begin = mid = 0;
        int len1 = matrix.length, len2 = matrix[0].length;
        end = len1 * len2 - 1;
        while (begin < end) {
            mid = (begin + end) / 2;
            if (matrix[mid / len2][mid % len2] < target)
                begin = mid + 1;
            else
                end = mid;
        }
        return matrix[begin / len2][begin % len2] == target;
    }


    /***
     *  从矩阵右上角开始查找
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix3(int[][] matrix, int target) {
            if (matrix.length == 0 || matrix[0].length == 0)
                return false;

            int cols = matrix[0].length-1;
            int rows = matrix.length;
            int row =0;
            int col = cols;

        while (row<rows  && col>=0){
            int element = matrix[row][col];

            if(element== target){
                return true;
            }
            if(element>target){
                col--;
            }else {
                row++;
            }
        }
        return  false;
    }

    /**
     *  左下角开始查找
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix4(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return false;

        int cols = matrix[0].length-1;
        int rows = matrix.length;
        int row = rows-1;
        int col =0;

        while(col<=cols && row>=0){
            int data = matrix[row][col];
            if(data == target) return true;
            if(data>target){
                row--;
            }else{
                col++;
            }
        }
        return  false;
    }


    public int[] spiralOrder(int[][] matrix) {
        if(matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        int[] res = new int[(r + 1) * (b + 1)];
        while(true) {
            for(int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if(++t > b) break;
            for(int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if(l > --r) break;
            for(int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if(t > --b) break;
            for(int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if(++l > r) break;
        }
        return res;
    }



    public static String replaceSpace(String s) {

        int[] nums;
        if (s == null || "".equals(s))
            return s;
        char[] chars = s.toCharArray();
        char[] arr = new char[chars.length * 3];
        int size = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                arr[size++] = '%';
                arr[size++] = '2';
                arr[size++] = '0';
            } else {
                arr[size++] = c;
            }
        }
        return  new String(arr,0,size);
    }

    /**
     * 从尾到头打印链表
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode curr = head;

        while (curr!=null){
            stack.push(curr);
            curr = curr.next;
        }
        int size = stack.size();
        int [] arr = new int[size];
        for (int i = 0; i < size; i++) {
           arr[i] =  stack.pop().val;
        }
        return arr;
    }


    public static int[] reversePrint2(ListNode head) {
        ListNode curr = head;
        ListNode pre = null;
        int i =0;
        int[] arr = new int[10000];
        //List<Integer> arr = new ArrayList<Integer>();
        while (curr!=null && curr.next !=null){
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
            arr[i++] = temp.val;
            //arr.add(temp.val);
        }
        //int[] objects = arr.toArray();

        return arr;
    }

    public static int numWays(int n) {
        if(n<0) return 0;
        if(n==0 || n==1) return 1;
        if(n==2) return 2;

        int[] arr = new int[n+1];
        arr[0]=1;
        arr[1]=1;
        arr[2]=2;


        for(int i=2;i<=n;i++){
            arr[i]= (arr[i-1]+arr[i-2])%1000000007;
        }
        return arr[n];
    }

    // 二分查找算法
    public int search(int[] nums, int target) {

        if(nums == null || nums.length == 0)
            return -1;

        int left = 0, right = nums.length - 1;
        while(left <= right){
            // Prevent (left + right) overflow
            int mid = left + (right - left) / 2;
            if(nums[mid] == target){ return mid; }
            else if(nums[mid] < target) { left = mid + 1; }
            else { right = mid - 1; }
        }

        // End Condition: left > right
        return -1;

    }

    public static int search2(int[] nums, int target) {
        if(nums ==null || nums.length==0) return -1;

        int left =0,right = nums.length-1;

        while (left<=right){
            int mid = (left+right)/2;
            if (nums[mid] == target){
                return mid;
            }else if(nums[mid]<nums[right]){// 右测
                if(nums[mid]<target && target<=nums[right]){
                    right = mid-1;
                }else {
                    left = mid+1;
                }
            }else{// 左侧
                if(nums[left]<=target && target<nums[mid]){
                    left= mid +1;
                }else {
                    right = mid-1;
                }

            }
        }
        return -1;
    }


    public static List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> list = new ArrayList<Integer>();
        if(matrix == null || matrix.length == 0)
            return list;
        // 行
        int m = matrix.length;
        // 列
        int n = matrix[0].length;
        // 控制层数
        int i = 0;

        //统计矩阵从外向内的层数，如果矩阵非空，那么它的层数至少为1层
        int count = (Math.min(m, n)+1)/2;
        //从外部向内部遍历，逐层打印数据
        while(i < count) {
            // 左到右
            for (int j = i; j < n-i; j++) {
                list.add(matrix[i][j]);
            }
            //
            for (int j = i+1; j < m-i; j++) {
                list.add(matrix[j][(n-1)-i]);
            }

            for (int j = (n-1)-(i+1); j >= i && (m-1-i != i); j--) {
                list.add(matrix[(m-1)-i][j]);
            }
            for (int j = (m-1)-(i+1); j >= i+1 && (n-1-i) != i; j--) {
                list.add(matrix[j][i]);
            }
            // 层数变量 向里一层
            i++;
        }
        return list;
    }


    public static List<Integer> my3(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if(matrix==null) return list;
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 控制层数
        int index =0;
        // 总层数
        int count = (Math.min(rows,cols)+1)>>1;

        while (index<count){
            // 左到右  列变量： 0<k<cols-1; 行:index
            for (int k = index; k < cols - index; k++) {
                list.add(matrix[index][k]);
            }
            // 上到下 行变量  1<k<rows-1; 列：clos-1-index
            for (int k = index+1; k < rows - index; k++) {
                list.add(matrix[k][cols-1-index]);
            }
            // 右到左 列变量:0<k<(cols-1-index) 行：rows-1-index  如果这一层只有1行，那么第一个循环已经将该行打印了，这里就不需要打印了:rows-1-index !=index
            for (int k =(rows-1)-(index+1); k>=index && rows-1-index !=index; k--) {
                list.add(matrix[rows-1-index][k]);
            }
            // 下到上 行变量 1<k<rows-1-index; 列:
            for (int k =(rows-1)-(index+1) ; k>=index+1 && cols-1-index!=index; k--) {
                list.add(matrix[k][index]);
            }
            index++;
        }

        return list;
    }


}
