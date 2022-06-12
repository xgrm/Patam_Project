package Model.Interpreter.Commands;

import Model.Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BindCommand extends Command {

    HashMap<String, Variable> symbolTable;
    ConcurrentHashMap<String, Variable> bindTable;
    
    // binds code var to fg var
    public BindCommand(HashMap<String, Variable> symbolTable, ConcurrentHashMap<String, Variable> bindTable) {
        this.symbolTable = symbolTable;
        this.bindTable = bindTable;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        String token = args.get(index+1);
        bindTable.put(token.substring(1,token.length()-1),symbolTable.get(args.get(index-2)));
        return 2;
    }
}
