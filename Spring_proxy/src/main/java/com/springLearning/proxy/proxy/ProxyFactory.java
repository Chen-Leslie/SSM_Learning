package com.springLearning.proxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-06
 */
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxy(){
    //    jdk动态代理
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("日志，方法："+method.getName()+"，参数："+ Arrays.toString(args));
                // proxy表示代理对象，method表示要执行的方法，args表示要执行的方法的参数列表
                Object result = method.invoke(target, args);
                System.out.println("日志，方法："+method.getName()+"，结果："+ result);
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, h);
    }
}
