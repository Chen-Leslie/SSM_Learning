package com.mybatis.mapper;

import com.mybatis.pojo.User;

import java.util.List;


/**
 * @author:Chen
 * @Date:2022/8/29
 * @Description:
 */
public interface UserMapper {

    int insertUser();

    int updateUser();

    int delUser();

    User getUserByID();

    List<User> getAllUser();
}
