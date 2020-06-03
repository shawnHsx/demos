package com.semion.demo.Algorithm;


public class Sort {

    /***
     * 冒泡排序
     * @param nums
     */
    public void bubbleSort(int[] nums){
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for(int j=0;j<len;j++){
                if (nums[j] > nums[j+1]) {
                    // 交换
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j=1] = temp;
                }
            }
        }
    }


}
