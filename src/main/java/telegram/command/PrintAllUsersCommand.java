package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;

import java.util.Collection;

public class PrintAllUsersCommand extends Command {

    public PrintAllUsersCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        String result = "";
        Collection<Client> clientsCollection = ClientDao.getInstance().getClients();
        for (Client client: clientsCollection) {
            log.Console.printUser(client);
            result = result.concat(client.getTelegramUser().toString().concat("\n"));
        }
        sendMessage.setText(result);
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
