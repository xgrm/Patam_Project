package Interpreter.Commands;

import java.util.ArrayList;

public interface Command {
    // list of the args needed in order to execute the command
    public int execute(ArrayList<String> args);

    // the expected amount of args
    public int numOfArgs();

    // check if the args are valid
    public boolean validArgs(ArrayList<String> args);
}
