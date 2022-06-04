package telegram.handler;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import properties.Property;
import telegram.command.Command;

/**
 * Update Handler class.
 *
 * @author Farid Masjedi
 * @author Negar Anabestani
 */
public class UpdateHandler {

    /**
     * process the update
     * @param update the update
     * @throws BadInputException to handle bad inputs from the client
     */
    public void processUpdate(Update update) throws BadInputException {
        Command command;
        Message message;
        if (update.hasCallbackQuery())
            message = update.getCallbackQuery().getMessage();
        else
            message = update.getMessage();
        try {
            command = Command.valueOf(update);
        } catch (Exception e) {
            // handle bad inputs.
            throw new BadInputException(Property.MESSAGES_P.get("bad_input"),
                    message.getChatId().toString());
        }
        command.execute();
    }
}

