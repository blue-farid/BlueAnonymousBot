package com.blue_farid.blue_anonymous_bot.utils;

import org.springframework.stereotype.Component;

@Component
public class HttpBasicTokenUtils {
    public String build(String username, String password) {
        return "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
