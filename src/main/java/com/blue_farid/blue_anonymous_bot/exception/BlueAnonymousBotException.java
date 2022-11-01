package com.blue_farid.blue_anonymous_bot.exception;


import lombok.NoArgsConstructor;

/**
 * BlueAnonymousBot Exception
 * every custom exception extends this class
 */
@NoArgsConstructor
public abstract class BlueAnonymousBotException extends RuntimeException {
    public BlueAnonymousBotException(String message, Exception e) {
        super(message, e);
    }

    public BlueAnonymousBotException(String message) {
        super(message);
    }
}
