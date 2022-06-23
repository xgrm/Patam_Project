package Model.Interpreter.Commands;

import java.util.ArrayList;
import Model.Interpreter.Utils.SharedMemory;
import Model.Interpreter.Utils.Variable;

public class VarCommand extends Command {

    SharedMemory sm;
    // gets the sym table and updates it on execute
    public VarCommand(SharedMemory sm) {
        this.sm = sm;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        this.sm.getSymTable().put(args.get(index+1),new Variable(args.get(index+1),0f));
        return 1;
    }
}
