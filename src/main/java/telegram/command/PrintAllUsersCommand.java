package telegram.command;

import console.ConsoleWriter;
import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;
import utils.StringUtils;

import java.util.Collection;
import java.util.List;

@Admin
public class PrintAllUsersCommand extends Command {

    public PrintAllUsersCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        String result = "";
        Collection<Client> clientsCollection = ClientDao.getInstance().getClients();
        for (Client client: clientsCollection) {
            ConsoleWriter.printUser(client);
            result = result.concat("\n".concat(client.
                    toString().concat("\n")));
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
