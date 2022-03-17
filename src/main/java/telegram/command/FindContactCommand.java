package telegram.command;

import dao.ClientDao;
import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import service.ClientService;
import telegram.BlueAnonymousBot;


public class FindContactCommand extends Command{

    private static final String localMessage = "درحال ارسال پيام ناشناس به " +
            "?name" + " هستي.\n"
            +
            "\n" +
            "می\u200Cتونی هر حرف یا انتقادی که تو دلت هست رو بگی چون پیامت به صورت کاملا ناشناس ارسال می\u200Cشه!";

    private static final String localMessage2 = "متاسفانه مخاطبت الان عضو ربات نیست!\n" +
            "\n" +
            "چطوره یه جوری لینک ربات رو بهش برسونی تا بیاد و عضو بشه؟ مثلا لینک خودت رو بهش بفرستی یا اگه جزء دنبال کننده\u200Cهای اینستاگرامته لینکت رو در اینستاگرامت بذاری.\n" +
            "\n" +
            "برای دریافت لینک \uD83D\uDC48 /link";

    private final Message message;
    private final Client client;

    public FindContactCommand(String chatId, Client client, Message message){
        super(chatId);
        this.message = message;
        this.client = client;
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
        if (contact == null){
            sendMessage.setChatId(chatId);
            sendMessage.setText(localMessage2);
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            client.setClientState(ClientState.NORMAL);
        } else {
            sendMessage.setChatId(chatId);
            sendMessage.setText(localMessage.replace("?name",
                    contact.getTelegramUser().getFirstName()));
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            client.setClientState(ClientState.SENDING_MESSAGE_WITH_DEEPLINK);
            ClientService.getInstance().setContact(client, contact.getId());
        }
    }
}
