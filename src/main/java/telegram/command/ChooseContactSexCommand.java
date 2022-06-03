package telegram.command;

import menu.CancelMenu;
import model.Client;
import properties.Property;
import telegram.BlueAnonymousBot;

public class ChooseContactSexCommand extends Command {
    public ChooseContactSexCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
        this.sendMessage.setText(Property.MESSAGES_P.get("choose_contact_sex"));
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
