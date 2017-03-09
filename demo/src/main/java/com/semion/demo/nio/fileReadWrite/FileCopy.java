package com.semion.demo.nio.fileReadWrite;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by heshuanxu on 2016/9/18.
 */
public class FileCopy {

    /**
     *   IO                NIO
     面向流            面向缓冲
     阻塞IO            非阻塞IO
     无                选择器
     *
     */

    /**
     * 缓冲区内部细节：状态变量：position，limit，capacity
     * position：跟踪从缓冲区获取了多少数据
     * limit：还有多少数据需要取出（缓冲区写入通道），或者还有多少空间可以放数据（通道写入缓冲区）
     * capacity：缓冲区的最大容量
     * <p>
     * clear()方法：重置缓冲区，1.将limit设置为与capacity相同；2.position设置为0。
     * flip()方法：1.将limit设置为position值，2.设置为position为0；
     */


    public static void main(String args[]) throws Exception {
        //NIO 实现文件copy
        fileCopy();

        writeMsg();

        //fileMapping();

    }


    public static void writeMsg() throws IOException {
        byte message[] = {83, 111, 109, 101, 32, 98, 121, 116, 101, 115, 46};//字节数组内容： Some bytes.

        System.out.print("字节数组内容:" + new String(message));

        FileOutputStream fout = new FileOutputStream("E://myclass/a.txt");

        FileChannel fc = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        for (int i = 0; i < message.length; ++i) {
            buffer.put(message[i]);
        }

        buffer.flip();

        fc.write(buffer);

        fout.close();
    }


    /**
     * 文件读写的过程
     *
     * @throws java.io.IOException
     */
    private static void fileCopy() throws IOException {
        FileInputStream fis = new FileInputStream("E://myclass/Demo.java");
        // 获取读取通道
        FileChannel readChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("E://myclass/demo_cpay.java");
        // 写通道
        FileChannel writeChannel = fos.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true) {
            buffer.clear();//重置缓冲区
            int len = readChannel.read(buffer);// 读取文件
            if (len == -1) {// 读到文件结尾
                break;
            }
            buffer.flip();
            writeChannel.write(buffer);// 缓冲区数据通过channel写入到文件
        }

        readChannel.close();
        writeChannel.close();
        fis.close();
        fos.close();
    }

    /**
     * 文件内存映射
     *
     * @throws java.io.IOException 功能：访问磁盘上的数据文件，这使你可以不必对文件执行I / O操作，并且可以不必对文件内容进行缓存。
     */
    private static void fileMapping() throws IOException {

        RandomAccessFile raf = new RandomAccessFile("E://myclass/Demo.java", "rw");

        FileChannel fc = raf.getChannel();
        // 将文件映射到内存中
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
        while (mbb.hasRemaining()) {
            System.out.print((char) mbb.get());
        }
        mbb.put(0, (byte) 98); // 修改文件  对MappedByteBuffer的修改就相当于修改文件本身，这样操作的速度是很快的。
        raf.close();
    }
}
