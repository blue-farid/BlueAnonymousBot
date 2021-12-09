package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
public class UpdateHandler {
    private final ArrayList<Command> commands;

    public UpdateHandler() {
        Command[] commands = Command.values();
        this.commands = (ArrayList<Command>) Arrays.asList(commands);
    }

    public String processUpdate(Update update) throws BadInputException {
        Message message = update.getMessage();
        Command command;

        try {
            command = Command.valueOf(message.getText());
        } catch (IllegalArgumentException e) {
            // handle bad inputs.
            throw new BadInputException(telegram.Message.BAD_INPUT.getValue());
         }
    }
}

