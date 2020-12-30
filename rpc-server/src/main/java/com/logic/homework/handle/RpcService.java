package com.logic.homework.handle;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.handle
 * FileName  RpcMethod <p>
 * Describe  rpc服务注解 <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:27 <p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {
}
