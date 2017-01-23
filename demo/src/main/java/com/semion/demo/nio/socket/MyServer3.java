package com.semion.demo.nio.socket;
import com.semion.demo.utils.SerializableUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyServer3 {
    /*
    SelectionKey.OP_ACCEPT —— 接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
    SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
    SelectionKey.OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
    SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
     */




    private final static Logger logger = Logger.getLogger(MyServer3.class.getName());

    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;

        try {
            // 创建select，它用来监听各种感兴趣的IO事件
            selector = Selector.open();

            // 创建一个可监听TCP连接的Channel通道
            serverSocketChannel = ServerSocketChannel.open();
            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 绑定端口
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.socket().bind(new InetSocketAddress(10000));

            // 将这个通道注册到select上，监听OP_ACCEPT（新建立连接）事件，SelectionKey有四种事件类型
            SelectionKey registerKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("register done ....key:{"+registerKey.interestOps()+"} SelectionKey.OP_ACCEPT:"+SelectionKey.OP_ACCEPT);

            // 发生在select上的事件的数量，该方法会阻塞，直到有一个时间发生
            while (selector.select() > 0) {

                //返回发生了事件的 SelectionKey 对象的一个 集合并迭代
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                //通过迭代SelectionKeys并依次处理每个SelectionKey时间
                while (it.hasNext()) {
                    SelectionKey readyKey = it.next();
                    //对SelectionKey调用readyOps()方法，并检查发生了什么类型的事件

                        //接收了一个新连接。因为我们知道这个服务器套接字上有一个传入连接在等待 也就是说不用担心 accept() 操作会阻塞
                    if((readyKey.readyOps() & SelectionKey.OP_ACCEPT)== SelectionKey.OP_ACCEPT ){
                        ServerSocketChannel ssc = (ServerSocketChannel) readyKey.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        //将新连接注册到selector
                        //由于接受这个连接的目的是为了读取来自套接字的数据，所以我们还必须将SocketChannel注册到Selector上
                        sc.register(selector, SelectionKey.OP_READ);
                        it.remove();
                        System.out.println("get connectioned from " +sc);

                    }else if((readyKey.readyOps()& SelectionKey.OP_READ) == SelectionKey.OP_READ){
                        System.out.println("response msg to client ");
                        // 写数据到客服端
                        //execute((ServerSocketChannel) readyKey.channel());
                        it.remove();// 手动移除已处理的通道
                    }
                }
            }
        } catch (ClosedChannelException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                selector.close();
            } catch (Exception ex) {
            }
            try {
                serverSocketChannel.close();
            } catch (Exception ex) {
            }
        }
    }

    private static void execute(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            MyRequestObject myRequestObject = receiveData(socketChannel);
            logger.log(Level.INFO, myRequestObject.toString());

            MyResponseObject myResponseObject = new MyResponseObject(
                    "response for " + myRequestObject.getName(),
                    "response for " + myRequestObject.getValue());
            sendData(socketChannel, myResponseObject);
            logger.log(Level.INFO, myResponseObject.toString());
        } finally {
            try {
                socketChannel.close();
            } catch (Exception ex) {
            }
        }
    }

    private static MyRequestObject receiveData(SocketChannel socketChannel) throws IOException {
        MyRequestObject myRequestObject = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            byte[] bytes;
            int size = 0;
            while ((size = socketChannel.read(buffer)) >= 0) {
                buffer.flip();
                bytes = new byte[size];
                buffer.get(bytes);
                baos.write(bytes);
                buffer.clear();
            }
            bytes = baos.toByteArray();
            Object obj = SerializableUtil.toObject(bytes);
            myRequestObject = (MyRequestObject) obj;
        } finally {
            try {
                baos.close();
            } catch (Exception ex) {
            }
        }
        return myRequestObject;
    }

    private static void sendData(SocketChannel socketChannel, MyResponseObject myResponseObject) throws IOException {
        byte[] bytes = SerializableUtil.toBytes(myResponseObject);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketChannel.write(buffer);
    }
}