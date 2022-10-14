package com.SpringLearning.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.SprintLearning.transaction.pojo.User;

import java.util.List;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
// 指定当前测试类在Spring的测试环境中执行，此时就可以通过注入的方式直接获取
@RunWith(SpringJUnit4ClassRunner.class)
// 设置Spring测试环境的配置文件
@ContextConfiguration("classpath:spring-jdbc.xml")
public class TestJdbcTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testInsert(){
        String sql = "INSERT INTO user VALUES (null, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, "root", "123456", 23, "女", "123456@126.com");
    }

    @Test
    public void testUpdateById(){
        String sql = "UPDATE user SET password=? WHERE id=?";
        jdbcTemplate.update(sql, "12345", 3);
    }

    @Test
    public void testSelectById(){
        String sql = "SELECT * FROM user WHERE id=?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), 1);
        System.out.println(user);
    }

    @Test
    public void testSelectAll(){
        String sql = "SELECT * FROM user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        users.forEach(System.out::println);
    }

}
