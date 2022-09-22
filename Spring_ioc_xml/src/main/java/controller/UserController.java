package controller;

import service.UserService;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-22
 */
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void saveUser(){
        userService.saveUser();
    }
}
