package com.SprintLearning.transaction.service;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-15
 */
public interface CheckOutService {
    /**
     * 结账
     * @param userId
     * @param bookIds
     */
    void checkout(Integer userId, Integer[] bookIds);
}
