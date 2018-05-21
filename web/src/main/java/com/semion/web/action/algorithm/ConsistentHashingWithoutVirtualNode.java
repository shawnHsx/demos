package com.semion.web.action.algorithm;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by heshuanxu on 2018/5/18.
 */
public class ConsistentHashingWithoutVirtualNode {

    /**
     * 待添加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111", "192.168.0.4:111"};

    /**
     * key表示服务器的hash值，value表示服务器的名称
     */
    private static SortedMap sortedMap = new TreeMap();

    /**
     * 程序初始化，将所有的服务器放入sortedMap中
     */
    static {
        for (int i = 0; i < servers.length; i++) {
            int hash = getHash(servers[i]);
            System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, servers[i]);
        }
        System.out.println();
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash;
        hash ^= hash >> 7;
        hash += hash;
        hash ^= hash >> 17;
        hash += hash;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    /**
     * 得到应当路由到的结点
     */
    private static String getServer(String node) {
        // 得到带路由的结点的Hash值
        int hash = getHash(node);
        // 得到大于该Hash值的所有Map
        SortedMap subMap =
                sortedMap.tailMap(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        Integer i = (Integer) subMap.firstKey();
        // 返回对应的服务器名称
        return (String) subMap.get(i);
    }

    // 巧用异或运算符的规则，得出一个数和0异或还是自己，一个数和自己异或是0

    /**
     * A XOR B XOR B = A xor  0 = A (自反性)
     */
    public static void testXOR() {
        int[] array = {2, 3, 4, 4, 3, 5, 6, 6, 5};
        int v = 0;
        for (int i = 0; i < array.length; i++) {
            //v ^= array[i];
            v = v^array[i];
            System.out.println(v);
        }
        System.out.println("只出现一次的数是:" + v);

    }


    public static void main(String[] args) {
        /*String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");*/
        //testXOR();

        int a =10;
        int b = 5;
        a = a^b;
        b = a^b;
        a = a^b;
        System.out.println(a);
        System.out.println(b);
    }


    /**
     * 1-1000放在含有1001个元素的数组中，只有唯一的一个元素值重复，其它均只出现一次
     * 1^2^...^1000（序列中不包含n）的结果为T
     * 则1^2^...^1000（序列中包含n）的结果就是T^n
     * T^(T^n)=n
     */

}
