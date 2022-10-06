package com.proxy;

import com.springLearning.proxy.Calculator;
import com.springLearning.proxy.impl.CalculatorImpl;
import com.springLearning.proxy.proxy.CalculatorStaticProxy;
import com.springLearning.proxy.proxy.ProxyFactory;
import org.junit.Test;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-06
 */
public class ProxyTest {
    @Test
    public void testProxy(){
        CalculatorStaticProxy calculatorStaticProxy = new CalculatorStaticProxy(new CalculatorImpl());
        int res = calculatorStaticProxy.add(1, 2);
        System.out.println(res);
    }

    @Test
    public void testProxyFactory(){
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());
        // 不知道动态代理的类，但是知道动态实现的类，因此向上转型
        Calculator proxy = (Calculator) proxyFactory.getProxy();
        proxy.add(1, 2);
    }
}
