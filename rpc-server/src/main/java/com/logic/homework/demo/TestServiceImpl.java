package com.logic.homework.demo;


import com.logic.homework.handle.RpcService;
import org.springframework.stereotype.Service;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework
 * FileName  TestServiceImpl <p>
 * Describe  <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 14:23 <p>
 */

@Service
@RpcService
public class TestServiceImpl implements TestService {

    @Override
    public void test() {
        System.out.println("TestServiceImpl. test Method");
    }

    @Override
    public String queryAll() {
        return "TestServiceImpl. queryAll Method";
    }
}
