package com.blue_farid.blue_anonymous_bot.aspect;

import com.blue_farid.blue_anonymous_bot.utils.BlueStringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAspect {
    @Before("@annotation(com.blue_farid.blue_anonymous_bot.annotation.Admin)")
    public void authorize(JoinPoint joinPoint) throws IllegalAccessException {
        if (!joinPoint.getArgs()[0].equals(BlueStringUtils.getRuntimePassword()))
            throw new IllegalAccessException("Access Denied!");
    }
}
