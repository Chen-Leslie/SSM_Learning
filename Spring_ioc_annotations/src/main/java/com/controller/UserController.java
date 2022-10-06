package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-22
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public void saveUser(){
        userService.saveUser();
    }
}
