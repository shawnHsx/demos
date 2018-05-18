package com.semion.web.action.hystrix;

import java.util.List;

/**
 * Created by heshuanxu on 2018/4/13.
 */
public class FlushData implements Runnable {

    private List<Node> nodeList;

    public FlushData(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

   /* public static void main(String[] args) {

        new Thread(new FlushData(nodeList)).start();

    }*/
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Node s1 = new Node("50"+i, (i+1));
            nodeList.add(s1);
            System.out.println("======节点新增完成====count:"+(i+1));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
