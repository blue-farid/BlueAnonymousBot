package telegram.command;

import menu.GenderSettingsMenu;
import model.Client;
import model.ClientState;
import model.Gender;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class SettingsCommand extends Command{
    private final String localMessage;
    private final String localMessage2;

    public SettingsCommand(Client client){
        super(client);
        localMessage = Property.MESSAGES_P.get("settings");
        localMessage2 = Property.MESSAGES_P.get("gender_selected_before");
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        if (client.getClientGender() != Gender.NOT_SPECIFIED){
            this.sendMessage.setText(localMessage2);
            BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
            return;
        }
        this.sendMessage.setText(localMessage.replace("?name",
                client.getTelegramUser().getFirstName()));
        this.sendMessage.setReplyMarkup(GenderSettingsMenu.getInstance());

        ClientService.getInstance().setClientState(client, ClientState.CHOOSING_ITS_SEX);
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);


    }
}
