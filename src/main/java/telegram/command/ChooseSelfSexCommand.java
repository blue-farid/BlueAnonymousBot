package telegram.command;

import menu.MainMenu;
import model.Client;
import model.ClientState;
import model.Gender;
import org.telegram.telegrambots.meta.api.objects.Message;
import properties.Property;
import service.ClientService;
import telegram.BlueAnonymousBot;

public class ChooseSelfSexCommand extends Command{
    private final String localMessage;
    private final String localMessage2;
    private final Message message;

    public ChooseSelfSexCommand(Client client, Message message){
        super(client);
        this.message = message;
        localMessage = Property.MESSAGES_P.get("confirm_gender");
        localMessage2 = Property.MESSAGES_P.get("settings");
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
        boolean successful = false;
        if (message.hasText()){
            String text = message.getText();
            if (text.equals(Property.COMMANDS_P.get("im_boy"))){
                ClientService.getInstance().setClientGender(client, Gender.BOY);
                successful = true;
            }
            else if (text.equals(Property.COMMANDS_P.get("im_girl"))){
                ClientService.getInstance().setClientGender(client, Gender.GIRL);
                successful = true;
            }
        }
        if (successful){
            sendMessage.setText(localMessage);
            sendMessage.setReplyMarkup(MainMenu.getInstance());
            ClientService.getInstance().setClientState(client, ClientState.NORMAL);
        }
        else {
            sendMessage.setText(localMessage2);
        }
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
