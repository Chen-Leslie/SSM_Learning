package com.springLearning.proxy.impl;

import com.springLearning.proxy.Calculator;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-09-27
 */
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int i, int j) {
        // System.out.println("日志，方法：add，参数："+ i +"," + j);
        int res = i + j;
        System.out.println("方法内部：result:"+res);
        // System.out.println("日志，方法：add，结果："+ res);
        return res;
    }

    @Override
    public int sub(int i, int j) {
        //System.out.println("日志，方法：sub，参数："+ i +"," + j);
        int res = i - j;
        System.out.println("方法内部：result:"+res);
        //System.out.println("日志，方法：sub，结果："+ res);
        return res;
    }

    @Override
    public int mul(int i, int j) {
        //System.out.println("日志，方法：mul，参数："+ i +"," + j);
        int res = i * j;
        System.out.println("方法内部：result:"+res);
        //System.out.println("日志，方法：mul，结果："+ res);
        return res;
    }

    @Override
    public int div(int i, int j) {
        //System.out.println("日志，方法：div，参数："+ i +"," + j);
        int res = i / j;
        System.out.println("方法内部：result:"+res);
        //System.out.println("日志，方法：div，结果："+ res);
        return res;
    }
}
