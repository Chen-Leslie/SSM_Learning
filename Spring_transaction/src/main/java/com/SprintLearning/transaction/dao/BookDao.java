package com.SprintLearning.transaction.dao;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
public interface BookDao {
    /**
     * 根据图书id查询图书价格
     * @param bookId
     * @return
     */
    Integer getPriceByBookId(Integer bookId);

    /**
     * 更新图书库存
     * @param bookId
     */
    void updateStock(Integer bookId);

    /**
     * 更新用户余额
     * @param userId
     * @param bookPrice
     */
    void updateBalance(Integer userId, Integer bookPrice);
}
