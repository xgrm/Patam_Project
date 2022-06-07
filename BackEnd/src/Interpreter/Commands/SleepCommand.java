package Interpreter.Commands;

import java.util.ArrayList;

public class SleepCommand implements Command {

    @Override
    public int execute(ArrayList<String> args) {
        try {
            Thread.sleep(Long.parseLong(args.get(0)));
        } catch (Exception e) {
            System.out.println("error in sleep command");
        }
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
