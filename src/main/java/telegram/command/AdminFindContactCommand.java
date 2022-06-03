package telegram.command;

import dao.ClientDao;
import model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;

@Admin
public class AdminFindContactCommand extends FindContactCommand {

    public AdminFindContactCommand(String chatId, Client client, Message message) {
        super(chatId, client, message);
    }

    private Client findWithId(long id) {
        return ClientDao.getInstance().searchById(id);
    }

    @Override
    public void execute() {
        sendMessage.setChatId(chatId);
        Client contact;
        contact = findWithId(Long.parseLong(message.getText()));
        executeHelp(contact);
    }
}
