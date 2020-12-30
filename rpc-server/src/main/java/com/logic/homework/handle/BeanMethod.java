package com.logic.homework.handle;

import java.lang.reflect.Method;

/**
 * CopyRright(c)2017-2020 Logic <p>
 * Package com.logic.homework.handle
 * FileName  BeanMethod.java <p>
 * Describe  rpc服务注册包装类 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 16:05 <p>
 */
public class BeanMethod {
    private Object bean;

    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
