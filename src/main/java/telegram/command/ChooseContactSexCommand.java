package telegram.command;

import menu.CancelMenu;
import telegram.BlueAnonymousBot;

public class ChooseContactSexCommand extends Command {
    public ChooseContactSexCommand(String chatId) {
        super(chatId);
    }

    @Override
    public void execute() {
        this.sendMessage.setText(BlueAnonymousBot.getInstance()
                .getProperty("message.choose_contact_sex"));
        this.sendMessage.setReplyMarkup(CancelMenu.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(this.sendMessage);
    }
}
