package service.impl;

import dao.UserDao;
import service.UserService;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-22
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser() {
        userDao.saveUser();
    }
}
