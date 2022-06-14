package Model.Interpreter.Commands;

import java.util.ArrayList;

import Model.Interpreter.Utils.SharedMemory;

public class BindCommand extends Command {

    SharedMemory sm;
    
    public BindCommand(SharedMemory sm) {
        this.sm = sm;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        String token = args.get(index+1);
        sm.getBindMap().put(token.substring(1,token.length()-1),sm.getSymTable().get(args.get(index-2)));
        return 2;
    }
}
