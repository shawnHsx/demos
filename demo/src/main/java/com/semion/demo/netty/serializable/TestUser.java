package com.semion.demo.netty.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by heshuanxu on 2018/4/12.
 */
public class TestUser {


    public static void main(String[] args) throws IOException {
        //testSize();
        testTime();
    }

    private static void testSize() throws IOException {
        User user = new User();
        user.setId(100);
        user.setName("JDK");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        oos.flush();
        oos.close();

        byte[] bytes = bos.toByteArray();


        System.out.println("jdk serializable length is :"+bytes.length);
        bos.close();

        System.out.println("the byte array serializable length :"+ user.codeC().length);
    }


    public static void testTime() throws IOException {

        User user = new User();
        user.setId(100);
        user.setName("JDK");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            oos.writeObject(user);
            oos.flush();
            oos.close();
            byte[] bytes = bos.toByteArray();
            bos.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("jdk serializable time is :"+(end-start));



        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            byte[] bytes = user.codeC();
        }
        long end2 = System.currentTimeMillis();

        System.out.println("the byte array serializable time :"+ (end2-start2));


    }


}
