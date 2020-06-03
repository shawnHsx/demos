package com.semion.demo.Algorithm;

public class MyArray {

    public static void main(String[] args) {

    }


    /**
     * 合并两个有序数组
     * @param nums1
     * @param m 长度
     * @param nums2
     * @param n 长度
     */
    public static  void merge(int[] nums1, int m, int[] nums2, int n) {
       /* System.arraycopy(nums2,0,nums1,m,n);
        Arrays.sort(nums1);*/
        // 使用双指针
        int[] nums1_temp = new int[m];
        // 将num1 数组拷贝到 nums1_temp
        System.arraycopy(nums1,0,nums1_temp,0,m);
        int p1 =0;
        int p2= 0;
        int p=0;
        while (p1<m && p2<n){
            if(nums1_temp[p1]<nums2[p2]){
                nums1[p] =  nums1_temp[p1];
                p1++;
            } else {
                nums1[p] =  nums2[p2];
                p2++;
            }
            p++;
        }
        // 还有剩余元素
        if(p1<m){
            // 将剩余的nums1_temp  中的元素拷贝num1
            System.arraycopy(nums1_temp,p1,nums1,p1+p2,m+n-p1-p2);
        }
        if(p2<n){
            System.arraycopy(nums2,p2,nums1,p1+p2,m+n-p1-p2);
        }
    }


    /***
     * 合并两个有序数组，从后往前
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        // two get pointers for nums1 and nums2
        int p1 = m - 1;
        int p2 = n - 1;
        // set pointer for nums1
        int p = m + n - 1;
        // while there are still elements to compare
        while ((p1 >= 0) && (p2 >= 0))
            // compare two elements from nums1 and nums2
            // and add the largest one in nums1
            //nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
            nums1[p--] = (nums1[p1--]<=nums2[p2]) ? nums2[p2--]:nums1[p1--];
           // add missing elements from nums2
         System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    // 合并有序数组
    public void merge3(int[] nums1, int m, int[] nums2, int n) {
        int p1=0;
        int p2=0;
        int p =0;
        int[] nums1_copy = new int[m];
        System.arraycopy(nums1,0,nums1,0,m);
        while(p1<m && p2<n){
            if(nums1_copy[p1] <= nums2[p2]){
                nums1[p] = nums1_copy[p1];
                p1++;
            }else{
                nums1[p] = nums2[p2];
                p2++;
            }
            p++;
        }
        if(p1<m){
            System.arraycopy(nums1_copy,p1,nums1,p1+p2,m+n-p1-p2);
        }
        if(p2<n){
            System.arraycopy(nums2,p2,nums1,p1+p2,m+n-p1-p2);
        }
    }


}
