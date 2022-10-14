package com.SprintLearning.transaction.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
public interface BookService {
    /**
     * 买书
     * @param userId
     * @param bookId
     */
    void buyBook(Integer userId, Integer bookId);
}
