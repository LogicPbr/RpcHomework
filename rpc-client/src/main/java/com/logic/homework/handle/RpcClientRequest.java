package com.logic.homework.handle;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcClientRequest <p>
 * Describe  rpc请求响应发送序列化反序列化 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 15:24 <p>
 */
@Component
public class RpcClientRequest {

    private String host;
    private Integer port;

    public RpcClientRequest() {}

    public RpcClientRequest(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Socket getSocket() throws IOException {
        return new Socket(host, port);
    }

    public Object send(RpcRequest request){
        Object o = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            Socket socket = getSocket();
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();
            
            ois = new ObjectInputStream(socket.getInputStream());
            o = ois.readObject();
        } catch (Exception e) {
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
        return o;
    }

}
