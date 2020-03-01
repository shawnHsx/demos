package com.semion.demo.Algorithm;

import java.util.Arrays;

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
        System.out.println( searchMatrix2(matrix2,target));



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
}
