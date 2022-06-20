package Model.Interpreter.Commands;

import java.util.ArrayList;

public abstract class Command {
    // gets the tokens list and current command index,
    // returns the amount of args "used"
    public abstract int execute(ArrayList<String> args, int index);
}
