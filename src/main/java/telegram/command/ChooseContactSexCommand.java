package telegram.command;

import menu.CancelMenu;
import properties.Property;
import telegram.BlueAnonymousBot;

public class ChooseContactSexCommand extends Command {
    public ChooseContactSexCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(Property.MESSAGES_P.get("choose_contact_sex"));
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
