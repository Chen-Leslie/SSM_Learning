package com.SpringMVCLearning.controller;

import com.SpringMVCLearning.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-22
 */
@Controller
public class TestParamController {

    @RequestMapping("/param/servletAPI")
    public String getParamByServletAPI(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:" + username + "," + "password:" + password);
        return "success";
    }

    @RequestMapping("/param")
    public String getParam(@RequestParam(value = "userName", required = false, defaultValue = "root ") String username,
                           String password,
                           @RequestHeader("referer") String referer,
                           @CookieValue("JSESSIONID") String jsessionId
    ){
        System.out.println("username:" + username + "," + "password:" + password);
        System.out.println("referer:" + referer);
        System.out.println("jsessionId:" + jsessionId);
        return "success";
    }

    @RequestMapping("/param/pojo")
    public String getParamByPojo(User user){
        System.out.println(user);
        return "success";
    }

}
