package telegram.handler;

import dao.ClientDao;
import exception.BadInputException;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.command.Command;

public class UpdateHandler {

    public void processUpdate(Update update) throws BadInputException {
        Command command;
        try {
            command = Command.valueOf(update);
            log.Console.printNewRequestInfo(update, command);
        } catch (IllegalArgumentException e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue(),
                    update.getMessage().getChatId().toString());
        }
        command.execute();
    }
}

