# LRU 算法实现 
## 方式一： 可借助LinkedHashMap实现
## Code
``` java []
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * 判断size超过容量时返回true，告知LinkedHashMap移除最老的缓存项(即链表的第一个元素)
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```


## 方式二：自定义节点和链表实现

## Code
``` java []
class LRUCache {

    // key-->Node
    private Map<Integer, Node> map;

    //缓存 链表数据Node->Node 维护超过容量元素之后需要动态删除头元素
    private DoubleLinkedList cache;
    // 容量
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new DoubleLinkedList();
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            // 将该key 提升为最近使用(添加至链表尾部，可以先删除再添加)
            Node node = map.get(key);
            cache.deleteNode(node);
            cache.addLast(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {// 修改value值，并提升至最近使用
            Node node = map.get(key);
            node.val = value;
            // 提升至最近使用
            cache.deleteNode(node);
            cache.addLast(node);
            return;
        }
        // 当前容量是否已满--则移除最久为使用元素
        if (capacity == cache.size) {
            Node first = cache.removeFirst();
            map.remove(first.key);
        }
        // 开始添加元素至链表尾部
        Node add = new Node(key,value);
        cache.addLast(add);
        map.put(key,add);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

 /**
 * 节点定义
 */
class Node {
    int key;
    int val;
    Node pre;
    Node next;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

/**
 * 双向节点定义  最近使用的节点添加到尾部
 */
class DoubleLinkedList {
    // 定义虚拟头尾节点
    Node head;
    Node tail;
    int size;

    public DoubleLinkedList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre = head;
        size = 0;
    }

    /**
     * 添加到链表尾部
     *
     * @param x
     */
    public void addLast(Node x) {
        x.pre = tail.pre;
        x.next = tail;
        tail.pre = x;
        tail.pre.next = x;
        size++;
    }

    /**
     * @param x
     */
    public void deleteNode(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
        size--;
    }

    /**
     * 删除头节点 并返回
     *
     * @param
     */
    public Node removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node first = head.next;
        head.next = first.next;
        first.next.pre = head;
        size--;
        return first;
    }
}
``` 
