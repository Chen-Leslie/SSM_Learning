package com.SpringLearning.xml;

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
public class ValidateAspect {

    public void beforeAdviceMethod(){
        System.out.println("ValidateAspect-->前置方法");
    }

}
