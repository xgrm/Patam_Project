package Interpreter;

import Interpreter.Commands.Command;
import Interpreter.Commands.CommandFactory;
import Model.AgentModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Parser {
    private CommandFactory commandFactory;
    public Parser(HashMap<String, Variable> symTable, ConcurrentHashMap<String, Variable> bindTable, AgentModel model) {
        this.commandFactory = new CommandFactory(symTable,bindTable,model);
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
