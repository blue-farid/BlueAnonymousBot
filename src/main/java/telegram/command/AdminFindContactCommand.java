package telegram.command;

import dao.ClientDao;
import model.Client;
import org.telegram.telegrambots.meta.api.objects.Message;

@Admin
public class AdminFindContactCommand extends FindContactCommand {

    public AdminFindContactCommand(Client client, Message message) {
        super(client, message);
    }

    private Client findWithId(long id) {
        return ClientDao.getInstance().searchById(id);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        sendMessage.setChatId(getChatId());
        Client contact;
        contact = findWithId(Long.parseLong(message.getText()));
        executeHelp(contact, message.getText());
    }
}
