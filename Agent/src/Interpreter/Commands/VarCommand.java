package Interpreter.Commands;

import Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VarCommand extends Command {
    HashMap<String, Variable> symbolTable;

    public VarCommand(HashMap<String, Variable> symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        this.symbolTable.put(args.get(index+1),new Variable(args.get(index+1),0f));
        return 1;
    }
}
