package com.semion.web.action.hystrix;

import java.util.*;

/**
 * Created by heshuanxu on 2018/4/10.
 */
public class WeightRoundRobin {
    /*

           (1) 轮询所有节点，计算当前状态下所有节点的effectiveWeight之和totalWeight;

           (2) currentWeight = currentWeight + effectiveWeight;  选出所有节点中currentWeight中最大的一个节点作为选中节点；

           (3) 选中节点的currentWeight = currentWeight - totalWeight；

       */
    public static List<Node> nodeList = new ArrayList<Node>();//节点集合

    static {
        Node s1 = new Node("100", 2);
        Node s2 = new Node("101", 3);
        //Node s3 = new Node("102", -1);// 权重为负数 当前node不可用
        nodeList.add(s1);
        nodeList.add(s2);
        //nodeList.add(s3);
    }
    /** 
     * @Author: heshuanxu 
     * @Date: 17:31 2018/4/10 
     * @Desc: 加权轮询
     */
    public Node getNode(List<Node> nodeList) {
        int total = 0;// 总权重
        Node resultNode = null;// 结果节点
        Node currentNode = null;
        for (int i = 0, len = nodeList.size(); i < len; i++) {
            //当前所有节点
            currentNode = nodeList.get(i);
            //当前服务不可用
            if (currentNode.down) {
                continue;
            }
            total += currentNode.effectiveWeight;// 计算总权重

            currentNode.currentWeight += currentNode.effectiveWeight;

            if(currentNode.effectiveWeight < currentNode.weight){
                currentNode.effectiveWeight++;
            }
            if (resultNode == null || currentNode.currentWeight > resultNode.currentWeight) {
                resultNode = currentNode;
            }
        }
        resultNode.currentWeight = resultNode.currentWeight-total;
        return resultNode;
    }


    public static String weightRandom()
    {
        //重新建立一個map,避免出現由於服務器上線和下線導致的並發問題
        Map<String,Integer> serverMap  = new HashMap<String,Integer>();
        //serverMap.putAll(serverWeigthMap);
        //獲取ip列表list
        Set<String> keySet = serverMap.keySet();
        Iterator<String> it = keySet.iterator();

        List<String> serverList = new ArrayList<String>();

        while (it.hasNext()) {
            String server = it.next();
            Integer weight = serverMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }
        Random random = new Random();
        int randomPos = random.nextInt(serverList.size());

        String server = serverList.get(randomPos);
        return server;
    }


    public static void main(String[] args) throws InterruptedException {
        WeightRoundRobin obj = new WeightRoundRobin();
        Map<String, Integer> countResult = new HashMap<String, Integer>();
        new Thread(new FlushData(nodeList)).start();// 线程修改集合大小
        for (int i = 0; i < 1000; i++) {
            long l = System.currentTimeMillis();
            Node node = obj.getNode(nodeList);// 加权轮询算法
            long l2 = System.currentTimeMillis();

            Thread.sleep(100);
            String log = "node:" + node.channel + ";weight:" + node.weight;
            if (countResult.containsKey(log)) {
                countResult.put(log, countResult.get(log) + 1);
            } else {
                countResult.put(log, 1);
            }
            System.out.println(log);
        }
        for (Map.Entry<String, Integer> map : countResult.entrySet()) {
            System.out.println("节点 " + map.getKey() + " 请求次数： " + map.getValue());
        }
    }
}
