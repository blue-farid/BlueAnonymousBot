package telegram.command;

import model.Client;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import utils.RandomUtils;

public class AnonymousLinkCommand extends Command {
    private final static String localMessage = "سلام" +
            " " +
            "?name" +
            " هستم ✋️\n" +
            "\n" +
            "لینک زیر رو لمس کن و هر حرفی که تو دلت هست یا هر انتقادی که نسبت به من داری رو با خیال راحت بنویس و بفرست. بدون اینکه از اسمت باخبر بشم پیامت به من می\u200Cرسه. خودتم می\u200Cتونی امتحان کنی و از بقیه بخوای راحت و ناشناس بهت پیام بفرستن، حرفای خیلی جالبی می\u200Cشنوی! \uD83D\uDE09\n" +
            "\n" +
            "\uD83D\uDC47\uD83D\uDC47\n";

    private final Client client;

    public AnonymousLinkCommand(Client client) {
        super(localMessage.replace("?name",
                client.getTelegramUser().getFirstName()));
        this.client = client;
    }

    @Override
    public SendMessage execute() {
        if (!client.hasDeepLink()) {
            String deepLink = generateAnonymousLink();
            client.setDeepLink(deepLink);
        }
        this.sendMessage.setText(this.sendMessage.getText().concat(client.getDeepLink()));
        return this.sendMessage;
    }

    public String generateAnonymousLink() {
        String anonymousLink = "https://t.me/BChaatt_Bot?start=sc";
        anonymousLink += "-";
        anonymousLink += RandomUtils.getInstance().generateRandomNumber(5);
        anonymousLink += "-";
        anonymousLink += RandomUtils.getInstance().generateRandomString(8);
        return anonymousLink;
    }

}
