package com.semion.demo.netty.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;


/**
 * Created by heshuanxu on 2018/4/12.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -553620767161724152L;

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = this.name.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        buffer.putInt(this.id);
        buffer.flip();
        bytes = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;

    }
}
