package Model.Interpreter.Commands;

import java.util.HashMap;
import Model.Interpreter.Utils.SharedMemory;

public class CommandFactory {
    private HashMap<String,CommandCreator> cmds;

    public CommandFactory(SharedMemory sm){
        sm.setCommandFactory(this);
        this.cmds = new HashMap<>();
        cmds.put("=",()->new AssignCommand(sm));
        cmds.put("bind",()->new BindCommand(sm));
        cmds.put("while",()->new WhileCommand(sm));
        cmds.put("sleep",()->new SleepCommand());
        cmds.put("print",()->new PrintCommand(sm));
        cmds.put("var",()->new VarCommand(sm));
    }

    private interface CommandCreator{
        public Command create();
    }

    public Command getCommnd(String commandName){
        CommandCreator commandCreator = cmds.get(commandName);
        Command command=null;
        if(commandCreator!=null)
            command = commandCreator.create();
      return command;
    }
}
