package com.SpringLearning.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author cbr
 * @version 1.0
 * @date 2022-10-13
 */
@Component
public class LoggerAspect {

    public void beforeAdviceMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect, 前置通知. 方法:" + name + ", 参数：" + Arrays.toString(args));
    }

    public void afterAdviceMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 后置通知. 方法:" + signature.getName() + ",执行完毕.");
    }

    public void afterReturningAdviceMethod(JoinPoint joinPoint, Object result){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 返回通知. 方法:" + signature.getName() + ", 结果: " + result);
    }

    public void afterThrowingAdviceMethod(JoinPoint joinPoint, Exception exception){
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect, 异常通知. 方法:" + signature.getName() + ", 异常: " + exception);
    }

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
