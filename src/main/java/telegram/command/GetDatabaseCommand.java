package telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.BlueAnonymousBot;
import utils.FileUtils;

public class GetDatabaseCommand extends Command {

    public GetDatabaseCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(sendMessage.getChatId());
        InputFile inputFile = new InputFile(
                FileUtils.getInstance().getBotClientsFile());
        sendDocument.setDocument(inputFile);
        try {
            BlueAnonymousBot.getInstance().execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
