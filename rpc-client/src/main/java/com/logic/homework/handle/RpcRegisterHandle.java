package com.logic.homework.handle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcHandle <p>
 * Describe  rpc请求注册 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:34 <p>
 */
@Component
public class RpcRegisterHandle implements BeanPostProcessor {

    @Autowired
    private RpcInvocationHandler rpcInvocationHandler;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        for (Field field : bean.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(RpcReference.class)) {
                try {
                    field.setAccessible(true);
                    Object proxyInstance = Proxy.newProxyInstance(field.getType().getClassLoader(), new Class<?>[]{field.getType()}, rpcInvocationHandler);
                    field.set(bean, proxyInstance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
