package telegram.command;

import inlineMenu.InlineAMB;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.BlueAnonymousBot;

public class SendMessageWithDeepLinkCommand extends Command {

    private final Client client;
    private final String message;
    public SendMessageWithDeepLinkCommand(String chatId, Client client, String message) {
        super(chatId);
        this.client = client;
        this.message = message;
    }

    @Override
    public void execute() {
        SendMessage sendMessage1=new SendMessage();
        sendMessage1.setChatId(client.getContact().getChatId().toString());
        sendMessage1.setText(message);
        sendMessage1.setReplyMarkup(new InlineAMB(client.getShortDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage1 );
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        client.setClientState(ClientState.NORMAL);
    }
}
