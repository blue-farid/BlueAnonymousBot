package com.blue_farid.blue_anonymous_bot.exception;

public class LoginException extends BlueAnonymousBotException {
    public LoginException() {
        super("username or password is incorrect!");
    }
}
