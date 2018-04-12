package com.semion.demo.netty.messagepack;

import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshuanxu on 2018/4/12.
 */
public class TestMsgPack {

    private static final Logger logger = LoggerFactory.getLogger(TestMsgPack.class);


    public static void main(String[] args) throws IOException {
        test();
    }


    public static void test() throws IOException {

        List<String> src = new ArrayList<String>();
        src.add("msgpack");
        src.add("msgpack1");
        src.add("msgpack2");
        src.add("msgpack3");

        MessagePack messagePack = new MessagePack();
        // 序列化为字节数组
        byte[] res = messagePack.write(src);
        // 反序列
        List<String> dts = messagePack.read(res, Templates.tList(Templates.TString));

        for (int i = 0; i < dts.size(); i++) {
            String s = dts.get(i);
            logger.info(s);
        }
    }


}
