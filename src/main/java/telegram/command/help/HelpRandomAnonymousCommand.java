package telegram.command.help;

import model.Client;
import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpRandomAnonymousCommand extends Command {

    public HelpRandomAnonymousCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
    }
}
