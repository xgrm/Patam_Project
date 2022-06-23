package Model.Interpreter.Utils;

import java.util.concurrent.ConcurrentHashMap;
import Model.AgentModel;
import Model.Interpreter.Commands.CommandFactory;

public class SharedMemory {
    private ConcurrentHashMap<String,Variable> symTable;
    private ConcurrentHashMap<String, Variable> bindMap;
    private CommandFactory cf;
    public AgentModel model;

    public SharedMemory(AgentModel model){
        this.model = model;
        symTable = new ConcurrentHashMap<>();
        bindMap = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, Variable> getSymTable(){
        return this.symTable;
    }

    public ConcurrentHashMap<String, Variable> getBindMap(){
        return this.bindMap;
    }

    public void setCommandFactory(CommandFactory cf){
        this.cf = cf;
    }

    public CommandFactory getCommands(){
        return this.cf;
    }
}
