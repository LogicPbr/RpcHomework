package com.logic.homework.handle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcServer <p>
 * Describe  rpc服务请求监听类 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:46 <p>
 */
@Component
public class RpcServerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${rpc.host}")
    private String host;
    @Value("${rpc.port}")
    private Integer port;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    static Selector selector;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ServerSocket ss = null;
        try {
            selector = Selector.open();
            //selector 必须是非阻塞
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //设置为非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //把连接事件注册到多路复用器上

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) { //连接事件
                        handleAccept(key);
                    } else if (key.isReadable()) { //读的就绪事件
                        handleRead(key);
                    } else if (key.isWritable()) {

                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleRead(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int readLen = 0;
            while ((readLen = socketChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                byte[] temp = new byte[readLen];
                byteBuffer.get(temp, 0, readLen);
            }
            if (readLen <= 0) {
                selectionKey.cancel();
            }

            RpcRequest request = (RpcRequest) getObject(byteBuffer);
            RpcServerRoute rpcRoute = RpcServerRoute.getRpcRoute();
            Object route = rpcRoute.route(request);

            byteBuffer = getByteBuffer(route);
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();//一定会有一个连接
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ); //注册读事件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObject(ByteBuffer byteBuffer) throws ClassNotFoundException, IOException {
        InputStream input = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream oi = new ObjectInputStream(input);
        Object obj = oi.readObject();
        input.close();
        oi.close();
        byteBuffer.clear();
        return obj;
    }

    public static ByteBuffer getByteBuffer(Object obj) throws IOException {
        byte[] bytes = getBytes(obj);
        ByteBuffer buff = ByteBuffer.wrap(bytes);
        return buff;
    }

    public static byte[] getBytes(Object obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        out.close();
        return bytes;
    }


}
