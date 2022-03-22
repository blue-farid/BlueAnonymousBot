package telegram.command;

import dao.ClientDao;
import model.Client;
import service.ClientService;
import properties.Property;
import telegram.BlueAnonymousBot;
import utils.RandomUtils;

public class AnonymousLinkCommand extends Command {
    private final String localMessage ;

    private final Client client;

    public AnonymousLinkCommand(String chatId, Client client) {
        super(chatId);
        this.client = client;
        localMessage= Property.MESSAGES_P.get("anonymous_link");
    }

    @Override
    public void execute() {
        if (!client.hasDeepLink()) {
            ClientService.getInstance().setDeepLink(client, generateAnonymousLink());
        }
        this.sendMessage.setText(localMessage.replace("?name",
                client.getTelegramUser().getFirstName())
                .concat("\n"+client.getLongDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }

    private static String generateAnonymousLink() {
        while (true) {
            String anonymousLink = "https://t.me/BChaatt_Bot?start=sc";
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
