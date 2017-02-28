package com.semion.demo.netty.bio;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by heshuanxu on 2017/2/22.
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            InputStream inputStream = this.socket.getInputStream();
            OutputStream outputStream = this.socket.getOutputStream();

            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(outputStream,true);

            String currentTime = null;
            String body = null;
            while (true){
                body = in.readLine();
                if(body==null){
                    break;
                }
                System.out.println("body = " + body);
                // 获取当前时间
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                System.out.println("time:"+currentTime);
                // 输出时间到客户端
                out.println(currentTime);
            }
        } catch (IOException e) {
            // close流
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            this.socket = null;
        }
    }
}
