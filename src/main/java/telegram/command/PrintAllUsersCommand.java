package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;

import java.util.Collection;
import java.util.HashMap;

public class PrintAllUsersCommand extends Command {

    public PrintAllUsersCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        String result = "";
        HashMap<Long, Client> clients = ClientDao.getInstance().getClients();
        Collection<Client> clientsCollection = clients.values();
        for (Client client: clientsCollection) {
            log.Console.printUser(client);
            result = result.concat(client.getTelegramUser().toString().concat("\n"));
        }
        sendMessage.setText(result);
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
