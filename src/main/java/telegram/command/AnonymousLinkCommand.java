package telegram.command;

import model.Client;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;
import utils.RandomUtils;

public class AnonymousLinkCommand extends Command {
    private final static String anonymousLinkPrefix = "https://t.me/" +
            BlueAnonymousBot.getInstance().getBotUsername() + "?start=";
    private final String localMessage;


    public AnonymousLinkCommand(Client client) {
        super(client);
        this.localMessage = Property.MESSAGES_P.get("anonymous_link");

    }

    private static String generateAnonymousLink() {
        while (true) {
            String anonymousLink = anonymousLinkPrefix + "sc";
            anonymousLink += "-";
            anonymousLink += RandomUtils.getInstance().generateRandomNumber(5);
            anonymousLink += "-";
            anonymousLink += RandomUtils.getInstance().generateRandomString(8);
            if (ClientService.getInstance().getClientByDeepLink(anonymousLink) == null) {
                return anonymousLink;
            }
        }
    }

    public static String getAnonymousLinkPrefix() {
        return anonymousLinkPrefix;
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        if (!client.hasDeepLink()) {
            ClientService.getInstance().setDeepLink(client, generateAnonymousLink());
        }
        this.sendMessage.setText(localMessage.replace("?name",
                        client.getFirstname())
                .concat("\n" + client.getDeepLink()));
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
