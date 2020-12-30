package com.logic.homework.demo;

import com.logic.homework.handle.RpcReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * CopyRright(c)2017-2020 Logic  <p>
 * Package com.logic.homework.demo
 * FileName  TestController <p>
 * Describe  <p>
 * author   logic <p>
 * version  v1.0 <p>
 * CreateDate  2020-12-30 15:13 <p>
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RpcReference
    private TestService testService;

    @RequestMapping("/test")
    public void test(){
        testService.test();
    }

    @ResponseBody
    @RequestMapping("/queryAll")
    public String queryAll(){
        return testService.queryAll();
    }
}
