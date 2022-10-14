package com.SpringLearning.test;

import com.SpringLearning.annotation.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-13
 */
public class AopTestByAnnotation {
    @Test
    public void testAopByAnnotation(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("aop-annotation.xml");
        Calculator calculator = ioc.getBean(Calculator.class);
        calculator.add(1,2);
        calculator.div(1,2);
    }
}
