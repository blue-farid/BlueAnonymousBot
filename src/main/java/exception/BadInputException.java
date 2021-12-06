package exception;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BadInputException extends Exception {
    public BadInputException (String message) {
        super(message);
    }

    public static String badInputMessage() {
        return "متوجه نشدم :/\n" +
                "\n" +
                "چه کاری برات انجام بدم؟";
    }
}
