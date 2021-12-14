package telegram.command;

import dao.ClientDao;
import model.Client;
import menu.MainMenu;
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

    public StartCommand(String chatId) {
        super(chatId);
    }

    public StartCommand(String chatId, String optionalCommand) {
        super(chatId, optionalCommand);
    }

    @Override
    public void execute() {
        if (optionalCommand.isEmpty()) {
            // first state
            this.sendMessage.setText(localMessage);
            this.sendMessage.setReplyMarkup(MainMenu.getInstance());
            BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
            System.out.println(sendMessage.getChatId());
            return;
        }
        // second state
        String deepLink = optionalCommand.get();
        Client client = ClientDao.getInstance().searchByDeepLink(deepLink);
        sendMessage.setText(localMessage2.replace("?name",
                client.getTelegramUser().getFirstName()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        BlueAnonymousBot.getInstance().setReadyForGetMessage(true);
        sendMessage.setChatId(client.getChatId().toString());
        sendMessage.setText(BlueAnonymousBot.getInstance().getSavedMessage());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
        sendMessage.setText("پیام شما ارسال شد \uD83D\uDE0A\n" +
                "\n" +
                "چه کاری برات انجام بدم؟");
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }

}
