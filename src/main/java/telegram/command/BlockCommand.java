package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.BlueAnonymousBot;

public class BlockCommand extends Command {
    private final long contactId;
    private final Client client;

    public BlockCommand(String chatId, long contactId, Client client) {
        super(chatId);
        this.contactId = contactId;
        this.client = client;
    }

    @Override
    public void execute() {
        Client contact = ClientDao.getInstance().searchById(this.contactId);
        if (contact.isAdmin()) {
            this.sendMessage.setText("" + client.getTelegramUser().getFirstName()
            + " tries to block you!");
            this.sendMessage.setChatId(String.valueOf(contactId));
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
        } else {
            // who cares?
        }
    }
}
