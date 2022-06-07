package Interpreter.Commands;

import java.util.ArrayList;

public class BindCommand implements Command {

    String value;

    public BindCommand(ArrayList<String> args) {
        this.value = args.get(0);
    }

    @Override
    public int execute(ArrayList<String> args) {
        return 0;
    }

    @Override
    public int numOfArgs() {
        return 1;
    }

    @Override
    public boolean validArgs(ArrayList<String> args) {
        return true;
    }

}
