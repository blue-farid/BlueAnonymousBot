package telegram.handler;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.command.Command;

public class UpdateHandler {

    public void processUpdate(Update update) throws BadInputException {
        Command command;
        Message message;
        if (update.hasCallbackQuery())
            message = update.getCallbackQuery().getMessage();
        else
            message = update.getMessage();
        try {
            command = Command.valueOf(update);
            log.Console.printNewRequestInfo(message, command, true);
        } catch (Exception e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue(),
                    message.getChatId().toString());
        }
        command.execute();
    }
}

