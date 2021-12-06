package telegram;

import exception.BadInputException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateHandler {
    public String processUpdate(Update update) throws BadInputException {
        Message message = update.getMessage();
        if (message.getText().equals("/start") || message.getText().equals("/restart")) {
            return "حله!\n" +
                    "\n" +
                    "چه کاری برات انجام بدم؟";
        } else {
            throw new BadInputException("BadInputException!");
        }
    }
}
