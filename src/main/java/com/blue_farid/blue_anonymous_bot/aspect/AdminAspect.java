package com.blue_farid.blue_anonymous_bot.aspect;

import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import com.blue_farid.blue_anonymous_bot.utils.BlueStringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAspect {
    @Before("@annotation(com.blue_farid.blue_anonymous_bot.annotation.AdminApi)")
    public void authorize(JoinPoint joinPoint) throws IllegalAccessException {
        if (!String.valueOf(joinPoint.getArgs()[0]).equals(BlueStringUtils.getRuntimePassword()))
            throw new IllegalAccessException("Api Access Denied!");
    }

    @Before("@annotation(com.blue_farid.blue_anonymous_bot.annotation.AdminCommand))")
    public void isAdmin(JoinPoint joinPoint) throws IllegalAccessException {
        RequestDto requestDto = (RequestDto) joinPoint.getArgs()[0];
        if (!requestDto.client().isAdmin())
            throw new IllegalAccessException("Command Access Denied!");
    }
}
