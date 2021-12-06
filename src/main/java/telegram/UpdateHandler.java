package telegram;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateHandler {
    public String processUpdate(Update update) {
        Message message = update.getMessage();
        if (message.getText().equals("/start") || message.getText().equals("/restart")) {
            return "start";
        }
    }
}
