package telegram.command;

import dao.ClientDao;
import model.Client;
import menu.MainMenu;
import model.ClientState;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class StartCommand extends Command {
    private static final String localMessage = "حله!\n" +
            "\n" +
            "چه کاری برات انجام بدم؟";
    private static final String localMessage2 = "درحال ارسال پيام ناشناس به " +
            "?name" + " هستي.\n"
            +
            "\n" +
            "می\u200Cتونی هر حرف یا انتقادی که تو دلت هست رو بگی چون پیامت به صورت کاملا ناشناس ارسال می\u200Cشه!";

    private final Client client;

    public StartCommand(String chatId) {
        super(chatId);
        this.client = null;
    }

    public StartCommand(String chatId, String optionalCommand, Client client) {
        super(chatId, optionalCommand);
        this.client = client;
    }

    @Override
    public void execute() {
        if (optionalCommand.isEmpty()) {
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
        ClientService.getInstance().setClientState(client, ClientState.SENDING_MESSAGE_WITH_DEEPLINK);
        ClientService.getInstance().setContact(client, contact.getId());
        log.Console.println("- " + this.client + " trying to message to " + contact + "!");
    }

    private String selfAnonymousMessageString() {
        return "اینکه آدم گاهی با خودش حرف بزنه خوبه ، ولی اینجا نمیتونی به خودت پیام ناشناس بفرستی ! :)\n" +
                "\n" +
                "چه کاری برات انجام بدم؟";
    }

}
