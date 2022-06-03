package telegram.command;

import model.Client;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.BlueAnonymousBot;
import utils.FileUtils;

@Admin
public class GetDatabaseCommand extends Command {

    public GetDatabaseCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(sendMessage.getChatId());
        InputFile inputFile = new InputFile(
                FileUtils.getInstance().getDatabaseFile());
        sendDocument.setDocument(inputFile);
        try {
            BlueAnonymousBot.getInstance().execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
