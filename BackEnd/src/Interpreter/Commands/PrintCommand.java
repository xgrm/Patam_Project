package Interpreter.Commands;

import java.util.ArrayList;

public class PrintCommand implements Command {

    @Override
    public int execute(ArrayList<String> args) {
        System.out.println(args.get(0));
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
