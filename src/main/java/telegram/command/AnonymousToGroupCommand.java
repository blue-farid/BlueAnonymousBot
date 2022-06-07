package telegram.command;

import model.Client;

public class AnonymousToGroupCommand extends Command {
    public AnonymousToGroupCommand(Client client) {
        super(client);
    }

    @Override
    public void execute() throws IllegalAccessException {
        super.execute();
        addBaseLog();
    }
}
