package telegram.command;

import dao.ClientDao;
import log.Console;
import model.Client;
import menu.MainMenu;
import model.ClientState;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;

public class StartCommand extends Command {
    private final String localMessage;
    private final String localMessage2 ;
    private final Client client;

    public StartCommand(String chatId) {
        super(chatId);
        this.client = null;
        localMessage= Property.MESSAGES_P.get("start_1");
        localMessage2= Property.MESSAGES_P.get("start_2");
    }

    public StartCommand(String chatId, String optionalCommand, Client client) {
        super(chatId, optionalCommand);
        this.client = client;
        localMessage= Property.MESSAGES_P.get("start_1");
        localMessage2= Property.MESSAGES_P.get("start_2");
    }

    @Override
    public void execute() {
        if (optionalCommand.equals("")) {
            // first state
            this.sendMessage.setText(localMessage);
            this.sendMessage.setReplyMarkup(MainMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            return;
        }
        // second state
        String deepLink = optionalCommand.get();
        Client contact = ClientDao.getInstance().searchByDeepLink(deepLink);
        if (client.equals(contact)) {
            sendMessage.setText(selfAnonymousMessageString());
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            return;
        }
        sendMessage.setText(localMessage2.replace("?name",
                contact.getTelegramUser().getFirstName()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_TO_CONTACT);
        ClientService.getInstance().setContact(client, contact.getId());
        log.Console.println("- " + this.client + " trying to message to " + contact + "!");

    }

    private String selfAnonymousMessageString() {
        return Property.MESSAGES_P.get("self_anonymous");
    }

}
