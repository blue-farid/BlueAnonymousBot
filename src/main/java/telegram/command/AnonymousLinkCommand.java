package telegram.command;

import dao.ClientDao;
import model.Client;
import telegram.Admin;
import telegram.BlueAnonymousBot;
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

    public AnonymousLinkCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
    }

    @Override
    public void execute() {
        this.sendMessage.setText(localMessage.replace("?name",
                client.getTelegramUser().getFirstName())
                .concat(client.getLongDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }

    public static String generateAnonymousLink(Client client) {
        while (true) {
            String anonymousLink = "https://t.me/BChaattTest_Bot?start=sc";
            if (client.isAdmin()) {
                String username = client.getTelegramUser().getUserName();
                if (username.equals(Admin.Neginanabestani.toString())) {
                    anonymousLink += "-43947-dIIIpQjd";
                } else if (username.equals(Admin.negar_a_23.toString())) {
                    anonymousLink += "-23707-HGBEoDzl";
                } else if (username.equals(Admin.blue_farid.toString())) {
                    anonymousLink += "-73734-cwtxQtVz";
                } else {
                    new Exception("WTF!").printStackTrace();
                }
                return anonymousLink;
            }
            anonymousLink += "-";
            anonymousLink += RandomUtils.getInstance().generateRandomNumber(5);
            anonymousLink += "-";
            anonymousLink += RandomUtils.getInstance().generateRandomString(8);
            if (ClientDao.getInstance().searchByDeepLink(anonymousLink) == null) {
                return anonymousLink;
            }
        }
    }

}
