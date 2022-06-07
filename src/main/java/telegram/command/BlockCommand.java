package telegram.command;

import console.ConsoleWriter;
import dao.ClientDao;
import model.Client;
import org.apache.log4j.MDC;
import telegram.BlueAnonymousBot;

public class BlockCommand extends Command {
    private final long contactId;

    public BlockCommand(long contactId, Client client) {
        super(client);
        this.contactId = contactId;
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        Client contact = ClientDao.getInstance().searchById(this.contactId);
        MDC.put("others", ConsoleWriter.readyForLog("contactId: {" + contact.getId() + "}"));
        addBaseLog();
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
