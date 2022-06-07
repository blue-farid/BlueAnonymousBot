package telegram.command.help;

import model.Client;
import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpAnonymousToGroupCommand extends Command {
    public HelpAnonymousToGroupCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
    }
}
