package com.SprintLearning.transaction.service.Impl;

import com.SprintLearning.transaction.dao.BookDao;
import com.SprintLearning.transaction.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyBook(Integer userId, Integer bookId) {
        // 查询图书价格
        Integer bookPrice = bookDao.getPriceByBookId(bookId);
        // 更新图书库存
        bookDao.updateStock(bookId);
        // 更新用户余额
        bookDao.updateBalance(userId, bookPrice);
    }
}
