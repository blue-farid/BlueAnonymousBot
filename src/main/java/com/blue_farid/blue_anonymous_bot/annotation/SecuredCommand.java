package com.blue_farid.blue_anonymous_bot.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredCommand {
    String[] roles();
}
