package com.test;

import com.controller.UserController;
import com.dao.UserDao;
import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-22
 */
public class IocByAnnotation {
    @Test
    public void IocByAnnotationTest(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("Spring-ioc-annotation.xml");
        UserController userController = ioc.getBean(UserController.class);
        System.out.println(userController);
        UserService userService = ioc.getBean(UserService.class);
        System.out.println(userService);
        UserDao userDao = ioc.getBean(UserDao.class);
        System.out.println(userDao);
        userController.saveUser();
    }
}
