package com.SprintLearning.transaction.dao.Impl;

import com.SprintLearning.transaction.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public Integer getPriceByBookId(Integer bookId) {
        String sql = "SELECT price FROM t_book WHERE book_id=?";
        return template.queryForObject(sql, Integer.class, bookId);
    }

    @Override
    public void updateStock(Integer bookId) {
        String sql = "UPDATE t_book SET stock = stock - 1 WHERE book_id=?";
        template.update(sql, bookId);
    }

    @Override
    public void updateBalance(Integer userId, Integer bookPrice) {
        String sql = "UPDATE t_user SET balance = balance - ? WHERE user_id = ?";
        template.update(sql, bookPrice, userId);
    }
}
