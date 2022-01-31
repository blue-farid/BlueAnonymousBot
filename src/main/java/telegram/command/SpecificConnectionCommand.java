package telegram.command;

import menu.MainMenu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram.BlueAnonymousBot;

public class SpecificConnectionCommand extends Command {

    private static final String joinChannelMessage = "ربات \" ناشناس \" کاملا انحصاری فقط برای اعضای محترم کانال « رازکام » طراحی شده ، برای استفاده از ربات ، در کانال زیر عضو شوید \uD83D\uDC47\n" +
            "\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\uD83D\uDD39 @Razcom   \uD83D\uDD39 @Razcom\n" +
            "\n" +
            "بعد از عضویت در کانال روی « ✅ تایید عضویت » بزنید \uD83D\uDC47\uD83D\uDC47";
    public SpecificConnectionCommand(String chatId) {
        super(chatId);
    }


    @Override
    public void execute() {
        SendMessage jcSendMessage=new SendMessage();
        jcSendMessage.setText(joinChannelMessage);
        jcSendMessage.
        this.sendMessage.setText(localMessage);
        this.sendMessage.
        this.sendMessage.setReplyMarkup(MainMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
