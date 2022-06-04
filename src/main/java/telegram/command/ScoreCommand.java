package telegram.command;

import model.Client;

public class ScoreCommand extends Command {
    public ScoreCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() {
        addBaseLog();
    }
}
