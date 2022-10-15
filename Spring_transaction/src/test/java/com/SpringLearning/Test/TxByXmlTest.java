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
 * @date 2022-10-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx-xml.xml")
public class TxByXmlTest {

    @Autowired
    private BookController bookController;

    @Test
    public void TestTxByXml(){
        bookController.buyBook(1, 1);
    }

}
