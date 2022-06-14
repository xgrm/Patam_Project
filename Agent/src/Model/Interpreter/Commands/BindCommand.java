package Model.Interpreter.Commands;

import java.util.ArrayList;

import Model.Interpreter.Utils.SharedMemory;
import Model.Interpreter.Utils.Variable;

public class BindCommand extends Command {

    SharedMemory sm;
    
    public BindCommand(SharedMemory sm) {
        this.sm = sm;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        String token = args.get(index+1);
        String bindTo = token.substring(1,token.length()-1);
        Variable variable = sm.getSymTable().get(args.get(index-2));
        variable.setBindTo(bindTo);
        variable.setModel(sm.model);
        sm.getBindMap().put(bindTo,variable);
        return 1;
    }
}
