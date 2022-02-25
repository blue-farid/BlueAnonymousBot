package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import properties.Property;
import telegram.BlueAnonymousBot;


public class FindContactCommand extends Command{

    private final String localMessage ;
    private final String localMessage2 ;
    private final Message message;
    private final Client client;

    public FindContactCommand(String chatId, Client client, Message message){
        super(chatId);
        this.message = message;
        this.client = client;
        this.localMessage= Property.MESSAGES_P.get("find_contact_1");
        this.localMessage2= Property.MESSAGES_P.get("find_contact_2");
    }

    @Override
    public void execute() {
        // case 1 (find with forward)
        User forwardedFrom = message.getForwardFrom();
        Client contact = null;
        if (forwardedFrom != null) {
            contact = ClientDao.getInstance().searchById(forwardedFrom.getId());
        }
        if (contact != null && contact.equals(client))
            contact = null;

        // case 2 (find with username)
        if (contact == null) {
            String text = message.getText();
            String username = text.replaceFirst("@", "");
            if (!username.contains(" "))
                contact = ClientDao.getInstance().searchByUsername(username);
        }
        if (contact == null){
            sendMessage.setChatId(chatId);
            sendMessage.setText(localMessage2);
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            client.setClientState(ClientState.NORMAL);
        } else {
            sendMessage.setChatId(chatId);
            sendMessage.setText(localMessage.replace("?name",
                    contact.getTelegramUser().getFirstName()));
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            client.setClientState(ClientState.SENDING_MESSAGE_WITH_DEEPLINK);
            client.setContact(contact);
        }
    }
}
