package Interpreter.Commands;



import Interpreter.Variable;
import Model.AgentModel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class CommandFactory {

    private HashMap<String,Command> cmds;

    public CommandFactory(HashMap<String, Variable> symbolTable, ConcurrentHashMap<String, Variable> bindTable, AgentModel model) {
        this.cmds = new HashMap<>();
        cmds.put("=",new AssignCommand(symbolTable,model));
        cmds.put("bind",new BindCommand(symbolTable,bindTable));
        cmds.put("while",new WhileCommand(this,symbolTable));
        cmds.put("sleep",new SleepCommand());
        cmds.put("print",new PrintCommand(symbolTable));
    }

    public  Command getCommnd(String commandName){
      return cmds.get(commandName);
    }
}
