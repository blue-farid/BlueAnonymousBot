package telegram.command;

import console.ConsoleWriter;
import dao.ClientDao;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.apache.log4j.MDC;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;


public class FindContactCommand extends Command{

    protected final String localMessage ;
    protected final String localMessage2 ;
    protected final Message message;

    public FindContactCommand(Client client, Message message){
        super(client);
        this.message = message;
        this.localMessage= Property.MESSAGES_P.get("find_contact_1");
        this.localMessage2= Property.MESSAGES_P.get("find_contact_2");

    }
    private Client findWithForwarded(Message message){
        User forwardedFrom = message.getForwardFrom();
        if (forwardedFrom != null) {
            return ClientDao.getInstance().searchById(forwardedFrom.getId());
        }
        return null;
    }
    private Client findWithUsername(Message message){
        String text = message.getText();
        String username = text.replaceFirst("@", "");
        if (!username.contains(" ")) {
            return ClientDao.getInstance().searchByUsername(username);
        }
        return null;
    }
    private boolean isUsername(String username){
        return username.charAt(0) == '@';
    }

    @Override
    public void execute() {
        sendMessage.setChatId(getChatId());
        Client contact;
        contact = findWithForwarded(message);
        if (contact == null && !isUsername(message.getText())) {
            sendMessage.setText(Property.MESSAGES_P.get("send_forwarded_message"));
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        } else {
            if (contact == null)
                contact = findWithUsername(message);
        }
        executeHelp(contact, message.getText());
    }

    protected void executeHelp(Client contact, String input) {
        if (contact != null) {
            MDC.put("others", ConsoleWriter.readyForLog("input: " + input) +ConsoleWriter.readyForLog("contactId: {" + contact.getId() + "}"));
            if (contact.equals(client)) {
                sendMessage.setText(Property.MESSAGES_P.get("self_anonymous"));
                sendMessage.setReplyMarkup(MainMenu.getInstance());
                BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
                ClientService.getInstance().setClientState(client, ClientState.NORMAL);
            } else {
                sendMessage.setText(localMessage.replace("?name",
                        contact.getTelegramUser().getFirstName()));
                BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
                ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_TO_CONTACT);
                ClientService.getInstance().setContact(client, contact.getId());
            }

        } else {
            MDC.put("others", ConsoleWriter.readyForLog("input: " + input) + ConsoleWriter.readyForLog("contactId: {null}"));
            sendMessage.setText(localMessage2);
            sendMessage.setReplyMarkup(MainMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            ClientService.getInstance().setClientState(client, ClientState.NORMAL);
        }
        addBaseLog();
    }
}
