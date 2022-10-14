package com.SpringLearning.Test;

import com.SprintLearning.transaction.controller.BookController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx-annotation.xml")
public class TxByAnnotationTest {
    @Autowired
    private BookController bookController;

    @Test
    public void TestBuyBook(){
        Integer userId = 1;
        Integer bookId = 1;
        //bookController.buyBook(userId, bookId);
        bookController.checkout(userId, new Integer[]{1, 2});
    }



}
