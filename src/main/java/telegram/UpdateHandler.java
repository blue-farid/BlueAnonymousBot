package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateHandler {
    public String processUpdate(Update update) throws BadInputException {
        Message message = update.getMessage();
        if (message.getText().equals(Command.START.getValue()) ||
                message.getText().equals(Command.RESTART.getValue())) {
            return "حله!\n" +
                    "\n" +
                    "چه کاری برات انجام بدم؟";
        } else {
            throw new BadInputException("متوجه نشدم :/\n" +
                    "\n" +
                    "چه کاری برات انجام بدم؟");
        }
    }
}

enum Command {
    START("/start"), RESTART("/restart");

    private final String value;

    Command (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
