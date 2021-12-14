package telegram.handler;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.command.Command;

public class UpdateHandler {

    public void processUpdate(Update update) throws BadInputException {
        Command command;

        try {
            command = Command.valueOf(update);
        } catch (IllegalArgumentException e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue());
        }
        command.execute();
    }
}

