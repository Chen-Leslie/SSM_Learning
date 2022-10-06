package com.springLearning.proxy.proxy;

import com.springLearning.proxy.Calculator;
import com.springLearning.proxy.impl.CalculatorImpl;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-06
 */
public class CalculatorStaticProxy implements Calculator {
    private CalculatorImpl calculatorImpl;

    public CalculatorStaticProxy(CalculatorImpl calculatorImpl) {
        this.calculatorImpl = calculatorImpl;
    }

    @Override
    public int add(int i, int j) {
        System.out.println("日志，方法：add，参数："+ i +"," + j);
        int res = calculatorImpl.add(i, j);
        System.out.println("日志，方法：add，结果："+ res);
        return res;
    }

    @Override
    public int sub(int i, int j) {
        System.out.println("日志，方法：sub，参数："+ i +"," + j);
        int res = calculatorImpl.sub(i,j);
        System.out.println("日志，方法：sub，结果："+ res);
        return res;
    }

    @Override
    public int mul(int i, int j) {
        System.out.println("日志，方法：mul，参数："+ i +"," + j);
        int res = calculatorImpl.mul(i, j);
        System.out.println("日志，方法：mul，结果："+ res);
        return res;
    }

    @Override
    public int div(int i, int j) {
        try {
            System.out.println("日志，方法：div，参数："+ i +"," + j);
            int res = calculatorImpl.div(i, j);
            System.out.println("日志，方法：div，结果："+ res);
            return res;
        } catch (Exception e){
            System.out.println("日志，方法：div，失败原因"+e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
