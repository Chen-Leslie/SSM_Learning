package com.SprintLearning.transaction.controller;

import com.SprintLearning.transaction.service.BookService;
import com.SprintLearning.transaction.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CheckOutService checkOutService;

    public void buyBook(Integer userId, Integer bookId){
        bookService.buyBook(userId, bookId);
    }

    public void checkout(Integer userId, Integer[] bookIds){
        checkOutService.checkout(userId, bookIds);
    }
}
