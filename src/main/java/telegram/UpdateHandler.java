package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import telegram.command.Command;

public class UpdateHandler {

    public SendMessage processUpdate(Update update) throws BadInputException {
        Message message = update.getMessage();
        Command command;

        try {
            command = Command.valueOf(message.getText(),update.getMessage().getFrom());
        } catch (IllegalArgumentException e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue());
        }
        return command;
    }
}

