package com.logic.homework.handle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcInvocationHandler <p>
 * Describe  rpc请求处理包装 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 15:22 <p>
 */
@Component
public class RpcInvocationHandler implements InvocationHandler {

    @Value("${rpc.host}")
    private String host;
    @Value("${rpc.port}")
    private Integer port;

    public RpcInvocationHandler() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcClientRequest clientRequest = new RpcClientRequest(host, port);
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), args, method.getParameterTypes());

        return clientRequest.send(request);
    }
}
