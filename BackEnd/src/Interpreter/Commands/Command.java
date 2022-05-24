package Interpreter.Commands;

import java.util.List;
import Interpreter.Expressions.Expression;

public interface Command {
    // list of the data needed in order to execute the command
    public int execute(List<Expression> args);

    // the expected amount of args
    public int expectedArgs();
}
