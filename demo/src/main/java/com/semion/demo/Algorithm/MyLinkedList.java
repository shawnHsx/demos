package com.semion.demo.Algorithm;

public class MyLinkedList {


    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        ListNode listNode = reverseList(l1);
        System.out.println(listNode.val);

    }


    /**
     * 反转链表
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head){
        ListNode pre = null;
        ListNode curr = head;
        while (curr!=null){
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        return pre;
    }
}


class ListNode{

    int val;

    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

}

