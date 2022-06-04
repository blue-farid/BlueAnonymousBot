package telegram.command.help;

import model.Client;
import telegram.command.Command;

/**
 * @author Negar Anabestani
 */
public class HelpWhatForCommand extends Command {

    public HelpWhatForCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
    }
}
