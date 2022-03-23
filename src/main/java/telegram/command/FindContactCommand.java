package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import service.ClientService;
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
        sendMessage.setChatId(chatId);
        if (contact == null){
            sendMessage.setText(localMessage2);
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            ClientService.getInstance().setClientState(client, ClientState.NORMAL);
        } else {
            sendMessage.setText(localMessage.replace("?name",
                    contact.getTelegramUser().getFirstName()));
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_TO_CONTACT);
            ClientService.getInstance().setContact(client, contact.getId());
        }
    }
}
