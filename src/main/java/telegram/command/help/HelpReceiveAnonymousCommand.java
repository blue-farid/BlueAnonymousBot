package telegram.command.help;

import model.Client;
import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpReceiveAnonymousCommand extends Command {

    public HelpReceiveAnonymousCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
    }
}
