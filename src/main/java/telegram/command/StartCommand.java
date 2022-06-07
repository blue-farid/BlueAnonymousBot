package telegram.command;

import console.ConsoleWriter;
import dao.ClientDao;
import menu.CancelMenu;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.apache.log4j.MDC;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class StartCommand extends Command {
    private final String localMessage;
    private final String localMessage2 ;

    public StartCommand(Client client) {
        super(client);
        localMessage= Property.MESSAGES_P.get("start_1");
        localMessage2= Property.MESSAGES_P.get("start_2");
    }

    public StartCommand(Client client, String optionalCommand) {
        super(client, optionalCommand);
        localMessage= Property.MESSAGES_P.get("start_1");
        localMessage2= Property.MESSAGES_P.get("start_2");
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        if (optionalCommand.isEmpty()) {
            // first state
            addBaseLog();
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
        sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_TO_CONTACT);
        ClientService.getInstance().setContact(client, contact.getId());
        MDC.put("others", ConsoleWriter.readyForLog(
                this.client + " trying to message to " + contact + "!"));
        addBaseLog();

    }

    private String selfAnonymousMessageString() {
        return Property.MESSAGES_P.get("self_anonymous");
    }

}
