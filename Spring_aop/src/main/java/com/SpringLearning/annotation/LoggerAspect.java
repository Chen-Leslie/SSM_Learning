package com.SpringLearning.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-13
 */
@Component
@Aspect
public class LoggerAspect {
    @Pointcut("execution(* com.SpringLearning.annotation.CalculatorImpl.*(..))")

    public void PointCut(){}

    //@Before("execution(public int com.SpringLearning.annotation.CalculatorImpl.add(int, int))")
    //@Before("execution(* com.SpringLearning.annotation.CalculatorImpl.*(..))")
    @Before("PointCut()")
    public void beforeAdviceMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect, 前置通知. 方法:" + name + ", 参数：" + Arrays.toString(args));
    }

    @After("PointCut()")
    public void afterAdviceMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 后置通知. 方法:" + signature.getName() + ",执行完毕.");
    }

    /**
     * 在返回通知中若要获取目标对象方法的返回值，只需要通过@AfterReturning注解中的returning属性
     * 就可以将通知方法的某个参数指定为接受目标对象方法的返回值的参数，即需要在通知方法中的参数设置为同名
     */
    @AfterReturning(value = "PointCut()", returning = "result")
    public void afterReturningAdviceMethod(JoinPoint joinPoint, Object result){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 返回通知. 方法:" + signature.getName() + ", 结果: " + result);
    }

    /**
     * 在异常通知中若要获取目标对象方法的异常，只需要通过@AfterThrowing注解中的throwing属性
     * 就可以将通知方法的某个参数指定为接受目标对象方法的返回值的参数，即需要在通知方法中的参数设置为同名
     */
    @AfterThrowing(value = "PointCut()", throwing = "exception")
    public void afterThrowingAdviceMethod(JoinPoint joinPoint, Exception exception){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 异常通知. 方法:" + signature.getName() + ", 异常: " + exception);
    }

    @Around("PointCut()")
    public Object aroundAdviceMethod(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        try {
            System.out.println("环绕通知-->前置通知");
            // 表示目标对象方法的执行
            result = proceedingJoinPoint.proceed();
            System.out.println("环绕通知-->返回通知");
        } catch (Throwable e) {
            System.out.println("环绕通知-->异常通知");
        } finally {
            System.out.println("环绕通知-->后置通知");
        }
        return result;
    }
}
