package telegram.command.help;

import model.Client;
import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpFreeVIPCommand extends Command {

    public HelpFreeVIPCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
    }
}
