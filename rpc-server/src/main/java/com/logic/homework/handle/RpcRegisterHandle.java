package com.logic.homework.handle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcHandle <p>
 * Describe  rpc服务注册类 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:34 <p>
 */
@Component
public class RpcRegisterHandle implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(RpcService.class)) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                BeanMethod bm = new BeanMethod();
                bm.setBean(bean);
                bm.setMethod(method);
                RpcServerRoute.routeMap.put(bean.getClass().getInterfaces()[0].getName()+"."+method.getName(), bm);
            }
        }
        return bean;
    }
}
