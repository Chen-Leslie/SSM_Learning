package com.SpringLearning.annotation;

import org.springframework.stereotype.Component;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-13
 */
@Component
public interface Calculator {
    int add(int i, int j);
    int sub(int i, int j);
    int mul(int i, int j);
    int div(int i, int j);
}
