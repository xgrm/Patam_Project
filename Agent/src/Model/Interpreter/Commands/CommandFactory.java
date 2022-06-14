package Model.Interpreter.Commands;

import java.util.HashMap;

import Model.Interpreter.Utils.SharedMemory;

public class CommandFactory {

    private HashMap<String,Command> cmds;

    public CommandFactory(SharedMemory sm){
        this.cmds = new HashMap<>();
        cmds.put("=",new AssignCommand(sm));
        cmds.put("bind",new BindCommand(sm));
        cmds.put("while",new WhileCommand(sm));
        cmds.put("sleep",new SleepCommand());
        cmds.put("print",new PrintCommand(sm));
    }

    public Command getCommnd(String commandName){
      return cmds.get(commandName);
    }
}
