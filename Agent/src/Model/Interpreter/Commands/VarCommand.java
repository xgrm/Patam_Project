package Model.Interpreter.Commands;

import Model.Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;

public class VarCommand extends Command {
    HashMap<String, Variable> symbolTable;
    
    // gets the sym table and updates it on execute
    public VarCommand(HashMap<String, Variable> symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        this.symbolTable.put(args.get(index+1),new Variable(args.get(index+1),0f));
        return 1;
    }
}
