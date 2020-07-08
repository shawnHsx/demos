# demos
personal code repository


    /**
     * 反转整个链表--非递归
     * @param start
     * @return
     */
    private static LinkNode reverseAllList(LinkNode start) {
        if(start==null || start.next == null){
            return start;
        }
        LinkNode pre = null;
        LinkNode curr = start;
        while (curr!=null ){
            //临时保存下一个节点
            LinkNode temp = start.next;

            curr.next = pre;
            // pre 和 curr 向前移动一步
            pre = curr;
            curr = temp;
        }
        return pre;
    }
    
    /**
     * 反转整个链表--递归方式 1-->2-->3-->4
     * @param start
     * @return
     */
    private static LinkNode reverseAllListRecur(LinkNode start) {
        if(start==null || start.next == null){
            return start;
        }
        // 返回已反转的首节点 lastNode：4；   2<--3<--4
        LinkNode lastNode = reverseAllListRecur(start.next);

        //将节点2的next 设置为start
        start.next.next = start;
        // 将start节点（1节点）的next 设置为null
        start.next = null;
        return lastNode;
    } 
    
    /**
     * 反转第M到N个节点
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static LinkNode reverseFromMtoN2(LinkNode head, int m, int n){

        if(head==null || m>n ){
            return head;
        }
        LinkNode curr = head;

        LinkNode fpre = null;
        LinkNode tpost = null;
        int len =0;
        // 获取m-1 和 n+1 个节点
        while (curr!=null){
            len ++;
            fpre = (len == m-1 )? curr : fpre;
            tpost = (len == n+1 )? curr : tpost;
            curr = curr.next;
        }

        // m 到n 开始反转
        curr = fpre.next;
      
       // 反转前的开始节点，反转后为最后一个节点
        LinkNode mStart = curr;
        
        LinkNode pre = null;
        while (curr!=tpost){
            LinkNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        fpre.next = pre;
        // 将反转后的节点连接上第n+1个节点
        mStart.next = tpost;
        return head;
    }
    
   
    /**
     * 反转前m个节点
     * @param head
     * @param m
     * @return
     */ 
     public static LinkNode reverseMth(LinkNode head, int m) {

        if(head == null || m <=1){
            return head;
        }

        LinkNode curr =  head;
        LinkNode mPost =  null;
        int len = 0;
        while (curr !=null){
            len ++;
            if(len == m+1){
                mPost = curr;
            }
            curr = curr.next;
        }

        //反转前的起始节点
        LinkNode start =  head;
        curr = head;
        LinkNode pre =  null;
        while (curr!=mPost){
            LinkNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        start.next = mPost;
        return pre;
    }
    
    
    /**
     * 反转后m个节点 非递归
     * @param head
     * @param m
     * @return
     */
    public static LinkNode reversePostMth(LinkNode head, int m) {

        if(head == null || m <=1){
            return head;
        }

        LinkNode curr =  null;

        LinkNode fast =  head;
        LinkNode slow =  head;

        int i =0;
        while (i < m){
            fast = fast.next;
            i++;
        }
        while (fast.next!=null){
            slow = slow.next;
            fast = fast.next;
        }
        //反转前的其实节点
        //LinkNode start =  mPre.next;
        curr = slow;
        LinkNode pre =  null;
        while (curr!= null){
            LinkNode temp = curr.next;

            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        slow.next = pre;
        return head;
    }
  
     /**
     * k个节点一组 反转
     * @param head
     * @param k
     * @return
     */
    public static LinkNode reverseKGroup(LinkNode head,int k){
        // 构造虚节点
        LinkNode dummy = new LinkNode(0);
        dummy.next = head;

        LinkNode pre  = dummy;

        LinkNode end  = dummy;

        //开始分组

        while (end.next!=null){

            int i =0;
            while (i<k){
                end = end.next;
                i++;
            }
            if(end ==null){
                break;
            }

            // 开始反转

            LinkNode start = pre.next;
            LinkNode endNext = end.next;
            // 截断链表k个节点
            end.next =null;
            // 反转k组链表 注意 end.next 需要设置未null
           pre.next = reverseAllList(start);

           //重新续上反转后的子链表
           start.next = endNext;

           pre = start;
           end = start;
        }
        return  dummy.next;
    } 
    
     /**
     * 反转相邻节点
     * @param start
     * @return
     */
    private static LinkNode reverseInPairs(LinkNode start){
        if(start==null || start.next == null){
            return start;
        }
        // 构造虚拟节点 0 --1 --2 --3 --4
        LinkNode dummy = new LinkNode(0);
        dummy.next = start;

        LinkNode curr = dummy;

        while(curr.next!=null && curr.next.next!=null ){
            LinkNode left = curr.next;
            LinkNode right = curr.next.next;

            curr.next = right;
            left.next = right.next;
            right.next = left;
            
            // 移动到反转后的节点
            curr = left;
        }
        return dummy.next;
    }
    
     /**
     * 反转相邻节点---递归
     * @param start
     * @return
     */
    private static LinkNode reverseInPairsRecur(LinkNode start){
        if(start==null || start.next == null){
            return start;
        }
        // 反转前右侧节点
        LinkNode right = start.next;
        // right 节点之后的节点反转
        LinkNode temp = reverseInPairsRecur(start.next.next);

        start.next = temp;
        right.next= start;

        return right;
    }
    
    
     /**
     * 判断链表是否回文结构
     * @param start
     * @return
     */
    private static boolean isPalindrome(LinkNode start){
        if(start==null || start.next == null){
            return true;
        }
        // 快慢指针找到中间节点
        LinkNode slow = start;
        LinkNode fast = start;

        while (fast.next !=null &&  fast.next.next!=null  ){
            slow = slow.next;
            fast = fast.next.next;
        }

        // 反转中间节点右测节点
        LinkNode rhead = reverseAllList(slow.next);

        boolean result = true;
        // 开始比较
        LinkNode curr = rhead;
        while (curr!=null){
            if(start.val != curr.val){
                result = false;
                break;
            }
            curr = curr.next;
            start = start.next;
        }
        return result;
    }


    
    
    
    /**
     * 链表定义
     */
    class LinkNode{
        int val;
        LinkNode next;
        public LinkNode(int val){
            this.val = val;
        }
    }

    
    
    
    
