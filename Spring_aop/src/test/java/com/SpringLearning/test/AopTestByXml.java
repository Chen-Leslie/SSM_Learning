package com.SpringLearning.test;

import com.SpringLearning.xml.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
public class AopTestByXml {
    @Test
    public void testAopByXml(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("aop-xml.xml");
        Calculator calculator = ioc.getBean(Calculator.class);
        calculator.add(1, 1);
    }
}
