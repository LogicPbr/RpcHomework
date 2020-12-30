package com.logic.homework.handle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcRoute <p>
 * Describe  rpc服务路由类 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:40 <p>
 */
public class RpcServerRoute {
    static Map<String, BeanMethod> routeMap = new ConcurrentHashMap<String, BeanMethod>();

    private static volatile RpcServerRoute rpcRoute;

    private RpcServerRoute() {}

    public static RpcServerRoute getRpcRoute(){
        if(rpcRoute == null) {
            synchronized (RpcServerRoute.class) {
                if(rpcRoute == null) {
                    rpcRoute = new RpcServerRoute();
                }
            }
        }
        return rpcRoute;
    }

    public Object route(RpcRequest request){
        Object invoke = null;
        BeanMethod beanMethod = routeMap.get(request.getClassName() + "." + request.getMethodName());
        if(beanMethod == null) {
            return null;
        }
        try {
            invoke = beanMethod.getMethod().invoke(beanMethod.getBean(), request.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoke;
    }
}
