package telegram.command;

import inlineMenu.InlineAMB;
import menu.MainMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.BlueAnonymousBot;

public class SendMessageToContact extends Command {

    private final Client client;
    private final String message;

    public SendMessageToContact(String chatId, Client client, String message) {
        super(chatId);
        this.client = client;
        this.message = message;
    }

    @Override
    public void execute() {
        SendMessage contactSendMessage = new SendMessage();
        contactSendMessage.setChatId(client.getContact().getChatId().toString());
        contactSendMessage.setText("\uD83D\uDCEC شما یک پیام ناشناس جدید دارید !" + "\n\n" + this.message);
        if (client.getContact().isAdmin()) {
            contactSendMessage.setText(contactSendMessage.getText() + "\n" +
                    "username: " + client.getTelegramUser().getUserName()
                    + "\nfirstname: " + client.getTelegramUser().getFirstName()
                    + "\nlastname: " + client.getTelegramUser().getLastName());
        }
        contactSendMessage.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(contactSendMessage);
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);

        client.setClientState(ClientState.NORMAL);
    }
}