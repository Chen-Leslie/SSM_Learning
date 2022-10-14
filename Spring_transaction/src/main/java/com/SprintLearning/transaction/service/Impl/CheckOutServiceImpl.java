package com.SprintLearning.transaction.service.Impl;

import com.SprintLearning.transaction.service.BookService;
import com.SprintLearning.transaction.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-15
 */
@Service
public class CheckOutServiceImpl implements CheckOutService {
    
    @Autowired
    private BookService bookService;
    
    @Override
    @Transactional
    public void checkout(Integer userId, Integer[] bookIds) {
        for (Integer bookId : bookIds) {
            bookService.buyBook(userId, bookId);
        }
    }
}
