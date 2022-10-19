package com.SpringMVCLearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-19
 */
@Controller
public class PortalController {
    @RequestMapping("/")
    public String portal(){
        return "index";
    }
}
