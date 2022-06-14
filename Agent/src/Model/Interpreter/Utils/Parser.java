package Model.Interpreter.Utils;

import Model.Interpreter.Commands.Command;
import Model.Interpreter.Commands.CommandFactory;

import java.util.ArrayList;

public class Parser {
    private CommandFactory commandFactory;

    public Parser(SharedMemory sm){
        this.commandFactory = new CommandFactory(sm);
    }

    // runs the lexer, creates and runs the commands accordingly
    public void run(String code){
        ArrayList<String> tokens = Lexer.getTokens(code);
        Command command;
        for (int i = 0; i < tokens.size(); i++) {
            command = commandFactory.getCommnd(tokens.get(i));
            if(command!=null)
                i+= command.execute(tokens,i);
        }
    }
}
