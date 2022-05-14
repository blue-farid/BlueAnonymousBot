package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;
import utils.StringUtils;

import java.util.Collection;
import java.util.List;

@Admin
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
            result = result.concat("\n".concat(client.
                    getTelegramUser().toString().concat("\n")));
        }
        sendMessage.setText(result);
        int res = BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        if (res > 0) {
            // message is too long
            if (res == 400) {
                List<String> subStrings = StringUtils.getInstance().subStrings(result, 4000);
                executeMessages(subStrings);
            }
        }
    }
}
