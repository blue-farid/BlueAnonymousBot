package com.blue_farid.blue_anonymous_bot.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleUtils {
    @Value("${bot.locale}")
    private String locale;

    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }
}
