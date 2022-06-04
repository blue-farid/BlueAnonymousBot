package telegram.command.help;

import inlineMenu.InlineHelpKeyBoard;
import model.Client;
import properties.Property;
import telegram.BlueAnonymousBot;
import telegram.command.Command;

/**
 * Help Command Class
 * @author Negar Anabestani
 */
public class HelpCommand extends Command {
    public HelpCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
        sendMessage.setText(Property.MESSAGES_P.get("help"));
        sendMessage.setReplyMarkup(InlineHelpKeyBoard.getInstance());
        BlueAnonymousBot.getInstance().executeSendMessage(sendMessage);
    }
}
