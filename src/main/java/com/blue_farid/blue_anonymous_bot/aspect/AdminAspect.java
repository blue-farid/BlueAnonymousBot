package com.blue_farid.blue_anonymous_bot.aspect;

import com.blue_farid.blue_anonymous_bot.annotation.SecuredCommand;
import com.blue_farid.blue_anonymous_bot.dto.RequestDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdminAspect {

    @Before(value = "@annotation(com.blue_farid.blue_anonymous_bot.annotation.SecuredCommand))")
    public void authorize(JoinPoint joinPoint) throws IllegalAccessException {
        RequestDto requestDto = (RequestDto) joinPoint.getArgs()[0];
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SecuredCommand securedCommand = methodSignature.getMethod().getAnnotation(SecuredCommand.class);
        if (requestDto.client().getRoles().stream().noneMatch(
                r -> Arrays.asList(securedCommand.roles()).contains(r.getValue())
        ))
            throw new IllegalAccessException("Command Access Denied!");
    }
}
