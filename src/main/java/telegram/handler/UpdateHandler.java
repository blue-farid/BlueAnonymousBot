package telegram.handler;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.command.Command;

public class UpdateHandler {

    public SendMessage processUpdate(Update update) throws BadInputException {
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

