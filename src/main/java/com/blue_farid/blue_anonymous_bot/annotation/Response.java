package com.blue_farid.blue_anonymous_bot.annotation;

import com.blue_farid.blue_anonymous_bot.model.ClientState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Response {
    String value() default "";

    ClientState[] acceptedStates() default ClientState.NORMAL;
    String[] notValues() default "";
}
