package com.SpringMVCLearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-17
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/success")
    public String success(){
        return "success";
    }
}
