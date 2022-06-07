package Interpreter.Commands;

import java.util.ArrayList;

public class ConnectCommand implements Command {

    private String ip;
    private int port;

    public ConnectCommand(ArrayList<String> args) {
        if (!validArgs(args))
            return;
        this.ip = args.get(0);
        this.port = Integer.parseInt(args.get(1));
    }

    @Override
    public int execute(ArrayList<String> args) {

        return 0;
    }

    @Override
    public int numOfArgs() {
        return 2;
    }

    @Override
    public boolean validArgs(ArrayList<String> args) {
        try {
            Integer.parseInt(args.get(1));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
