package com.SpringMVCLearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-23
 */
@Controller
public class TestViewController {

    @RequestMapping("/test/view/thymeleaf")
    public String testThymeleafView(){
        return "success";
    }

    @RequestMapping("/test/view/forward")
    public String testInternalResourceView(){
        return "forward:/test/model";
    }

    @RequestMapping("/test/view/redirect")
    public String testRedirectView(){
        return "redirect:/test/model";
    }

}
