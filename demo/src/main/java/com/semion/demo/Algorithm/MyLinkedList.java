package com.semion.demo.Algorithm;

import java.util.*;

public class MyLinkedList {


    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        //ListNode listNode = reverseList(l1);
        //System.out.println(listNode.val);


        ListNode swapPairsNode = swapPairs(l1);
        //System.out.println(swapPairsNode.val);

        int[] a = {2,2,2,4,4,4,4};
        int i = majorityElement(a);
        System.out.println(i);

    }

    /**
     * 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode merge(ListNode l1,ListNode l2){
        if(l1 ==null ) return l2;
        if(l2 ==null ) return l1;
        // res 指向头节点
        ListNode res = new ListNode(-1);
        // pre 每次前进一步
        ListNode pre = res;
        while (l1!=null && l2!=null){
            if(l1.val<l2.val){
                pre.next = l1;
                l1 = l1.next;
            }else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
       /* if(l1!=null){
            pre.next = l1;
        }
        if(l2!=null){
            pre.next = l2;
        }*/
        pre.next = (l1==null?l2:l1);

        return res.next;
    }



    /**
     * 反转链表 双指针方法
     * @param head
     * @return
     *  1>2>3>4>null
     *  过程：
     *  null<1;
     *  2>3>4>null
     *  .....
     *  null<1<2<3<4
     */
    public static ListNode reverseList(ListNode head){
        ListNode pre = null;
        ListNode curr = head;
        while (curr!=null){
            //  临时存储当前节点的下一个节点
            ListNode temp = curr.next;
            // 当前节点下一个节点设置位前一个（最开始前一个为null）
            curr.next = pre;
            // pre ，cull 前进一步
            pre = curr;
            curr = temp;
        }
        return pre;
    }


    /***
     * 反转链表 使用递归
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head){
        if(head==null || head.next == null)
            return head;
        ListNode  p = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    /**
     * 相邻两个链表交换位置
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        return swap(head);
    }

    public  static  ListNode swap(ListNode head){
        if(head==null || head.next == null)
            return head;

        ListNode f = head;
        ListNode s = head.next;
        // 设置f节点的next
        f.next = swap(head.next);
        // 设置s节点的next
        s.next = f;
        return s;
    }

    /****
     * 查找出现一次的数  set集合
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        HashSet set = new HashSet();
        for(int i=0;i<nums.length;i++){
            boolean bool= set.add(nums[i]);
            if(!bool){
                set.remove(nums[i]);
            }
        }
        return (int) set.iterator().next();
    }


    /**
     *  查找出现次数最多的数--map统计方法 ---思考其他方式
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        int count =  nums.length/2;

        for (int i = 0; i <nums.length; i++) {
            if(map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else {
                map.put(nums[i],1);
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Integer> next = iterator.next();
            if( next.getValue()>count){
                return next.getKey();
            }
        }
        return 0;
    }


}


class ListNode{

    int val;

    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

}

