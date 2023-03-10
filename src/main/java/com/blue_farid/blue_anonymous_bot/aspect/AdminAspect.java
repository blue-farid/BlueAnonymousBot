package com.blue_farid.blue_anonymous_bot.aspect;

import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAspect {

    @Before("@annotation(com.blue_farid.blue_anonymous_bot.annotation.AdminCommand))")
    public void isAdmin(JoinPoint joinPoint) throws IllegalAccessException {
        RequestDto requestDto = (RequestDto) joinPoint.getArgs()[0];
        if (!requestDto.client().isAdmin())
            throw new IllegalAccessException("Command Access Denied!");
    }
}
