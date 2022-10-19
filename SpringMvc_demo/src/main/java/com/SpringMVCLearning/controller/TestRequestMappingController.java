package com.SpringMVCLearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-19
 */
@Controller
@RequestMapping("/test")
public class TestRequestMappingController {

    //此时请求映射所映射的请求的请求路径为：/test/testRequestMapping
    @RequestMapping(
            value = {"/testRequestMapping", "/hello"},
            method = {RequestMethod.GET, RequestMethod.POST},
            params = {"username", "!password", "username=admin", "password!='1'"}
    )
    public String testRequestMapping(){
        return "success";
    }

    @RequestMapping("/testRest/{id}/{username}")
    public String testRest(@PathVariable("id") String id, @PathVariable("username")
    String username){
        System.out.println("id:"+id+",username:"+username);
        return "success";
    }
}
