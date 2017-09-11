package com.semion.web.action;

import java.util.Scanner;

/**
 * Created by heshuanxu on 2017/5/9.
 */
public class Timer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int input = scanner.nextInt();
            if (input > 2359 || input < 0) {
                System.out.print("请输入0~2359之间BJT整数：");
            } else {
                System.out.println("BJT:" + input);
                String result = "";
                int hour = input / 100;
                int minutes = input % 100;
                if (hour > 23 || minutes < 0 || minutes > 59) {
                    System.out.println("BJT时间格式有误");
                } else {
                    if (hour - 8 < 0) {
                        // 跨时区
                        hour = hour + 24 - 8;
                    } else {
                        hour = hour - 8;
                    }
                    if (minutes < 10) {
                        String str = "0" + minutes;
                        result = hour * 100 + str;
                    } else {
                        result = hour * 100 + minutes + "";
                    }
                    System.out.println("UTC :" + result);
                }
            }
        }
    }
}
