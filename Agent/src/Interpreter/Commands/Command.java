package Interpreter.Commands;

import java.util.ArrayList;

public abstract class Command {



    // list of the args needed in order to execute the command
    public abstract int execute(ArrayList<String> args, int index);

}
