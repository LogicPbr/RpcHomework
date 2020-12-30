package com.logic.homework.handle;

import java.io.Serializable;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * FileName  TestService <p>
 * Describe  <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:24 <p>
 */
public class RpcRequest implements Serializable{

    private String className;
    private String methodName;
    private Object[] args;
    private Class[] types;

    public RpcRequest() {
    }

    public RpcRequest(String className, String methodName, Object[] args, Class[] types) {
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.types = types;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getTypes() {
        return types;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }
}
