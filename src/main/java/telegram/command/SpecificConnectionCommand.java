package telegram.command;

import inlineMenu.InlineJoinChannelKeyBoard;
import menu.CancelMenu;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.BlueAnonymousBot;

public class SpecificConnectionCommand extends Command {

    private static final String joinChannelMessage = "ربات \" ناشناس \" کاملا انحصاری فقط برای اعضای محترم کانال « رازکام » طراحی شده ، برای استفاده از ربات ، در کانال زیر عضو شوید \uD83D\uDC47\n" +
            "\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\n" +
            "بعد از عضویت در کانال روی « ✅ تایید عضویت » بزنید \uD83D\uDC47\uD83D\uDC47";
    private static final String localMessage="برای اینکه بتونم به مخاطب خاصت بطور ناشناس وصلت کنم، یکی از این ۲ کار رو انجام بده:\n" +
            "\n" +
            "راه اول \uD83D\uDC48 : Username@ یا همون آی\u200Cدی تلگرام اون شخص رو الان وارد ربات کن!\n" +
            "\n" +
            "راه دوم \uD83D\uDC48 : الان یه پیام متنی از اون شخص به این ربات فوروارد کن تا ببینم عضو هست یا نه!";

    private Client client;

    public SpecificConnectionCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
    }


    @Override
    public void execute() {
        SendMessage jcSendMessage=new SendMessage();
        jcSendMessage.setText(joinChannelMessage);
        jcSendMessage.setReplyMarkup(new InlineJoinChannelKeyBoard());
        jcSendMessage.setChatId(chatId);
        BlueAnonymousBot.getInstance().executeSendMessage(jcSendMessage);
        this.sendMessage.setText(localMessage);
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);

        client.setClientState(ClientState.SENDING_CONTACT_INFO);
    }
}
