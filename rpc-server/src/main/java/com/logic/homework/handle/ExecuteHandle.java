package com.logic.homework.handle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  ExecuteHandlr <p>
 * Describe  rpc请求相应序列化类 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:51 <p>
 */
public class ExecuteHandle{

    private Socket socket;

    public ExecuteHandle(Socket socket) {
        this.socket = socket;
    }

    private void handleRead(Selector selector, SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel=(ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel=serverSocketChannel.accept() ;//一定会有一个连接
            socketChannel.configureBlocking(false);
//            socketChannel.write(ByteBuffer.wrap("Hello Client,I'm NIO Server".getBytes()));
            socketChannel.register(selector,SelectionKey.OP_READ); //注册读事件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) ois.readObject();

            RpcServerRoute rpcRoute = RpcServerRoute.getRpcRoute();
            Object route = rpcRoute.route(request);

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(route);
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
