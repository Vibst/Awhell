package com.awhell.storyservice.AOPConfiguration;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectClass {

    @Before("execution(* com.awhell.storyservice.controller.*.*(..))")
    public void logBefore() {
        System.out.println("Before controller method");
    }

    @After("execution(* com.awhell.storyservice.controller.*.*(..))")
    public void logAfter() {
        System.out.println("After controller method");
    }

    @AfterThrowing(pointcut = "execution(* com.awhell.storyservice.controller.*.*(..))", throwing = "ex")
    public void logException(Throwable ex) {
        System.out.println("Exception in controller: " + ex.getMessage());
    }

    @Around("execution(* com.awhell.storyservice.controller.*.*(..))")
    public Object logAround(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around before method: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        System.out.println("Around after method: " + joinPoint.getSignature().getName());
        return result;  
}

}
