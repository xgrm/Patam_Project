package Interpreter.Commands;

import java.util.ArrayList;
import java.util.Map;

public class VarCommand implements Command {

    String varName;
    Map<String, Double> sym;

    public VarCommand(ArrayList<String> args, Map<String, Double> symbolTable) {
        this.varName = args.get(0);
        this.sym = symbolTable;
    }

    @Override
    public int execute(ArrayList<String> args) {
        sym.put(varName, 0.00);
        return 0;
    }

    @Override
    public int numOfArgs() {
        return 1;
    }

    @Override
    public boolean validArgs(ArrayList<String> args) {
        return true;
    }

}
