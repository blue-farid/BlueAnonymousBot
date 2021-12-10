package telegram.handler;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.command.Command;

public class UpdateHandler {

    public String processUpdate(Update update) throws BadInputException {
        Message message = update.getMessage();
        Command command;

        try {
            command = Command.valueOf(update);
        } catch (IllegalArgumentException e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue());
        }
        return command.execute();
    }
}

