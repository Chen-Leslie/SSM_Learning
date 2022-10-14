package com.SpringLearning.xml;

import org.springframework.stereotype.Component;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-27
 */
@Component
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int i, int j) {
        int res = i + j;
        System.out.println("方法内部：result:"+res);
        return res;
    }

    @Override
    public int sub(int i, int j) {
        int res = i - j;
        System.out.println("方法内部：result:"+res);
        return res;
    }

    @Override
    public int mul(int i, int j) {
        int res = i * j;
        System.out.println("方法内部：result:"+res);
        return res;
    }

    @Override
    public int div(int i, int j) {
        int res = i / j;
        System.out.println("方法内部：result:"+res);
        return res;
    }
}
