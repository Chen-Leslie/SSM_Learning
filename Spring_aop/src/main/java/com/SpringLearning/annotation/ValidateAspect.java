package com.SpringLearning.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-14
 */
@Component
@Aspect
@Order(1)
public class ValidateAspect {

    @Before("com.SpringLearning.annotation.LoggerAspect.PointCut()")
    public void beforeAdviceMethod(){
        System.out.println("ValidateAspect-->前置方法");
    }

}
