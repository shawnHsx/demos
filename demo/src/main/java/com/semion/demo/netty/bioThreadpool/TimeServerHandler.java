package com.semion.demo.netty.bioThreadpool;

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
            out = new PrintWriter(outputStream);
            String body = null;
            String currentTime = null;

            while (true) {
                body = in.readLine();
                System.out.println("====body :" + body);
                if (body == null) {
                    break;
                }
                String now = new Date(System.currentTimeMillis()).toString();
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? now : "===BAD ORDER===";
                System.out.println("time is :" + currentTime);
                out.println("now is：" + currentTime);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        this.socket = null;
    }
}
