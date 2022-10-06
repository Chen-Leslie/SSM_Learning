package com.dao.impl;

import com.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-22
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("save successfully!");
    }
}
