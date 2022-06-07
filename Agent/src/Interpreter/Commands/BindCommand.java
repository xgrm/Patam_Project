package Interpreter.Commands;

import Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BindCommand extends Command {

    HashMap<String, Variable> symbolTable;
    ConcurrentHashMap<String, Variable> bindTable;

    public BindCommand(HashMap<String, Variable> symbolTable, ConcurrentHashMap<String, Variable> bindTable) {
        this.symbolTable = symbolTable;
        this.bindTable = bindTable;
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        bindTable.put(args.get(index+1),symbolTable.get(args.get(index-2)));
        return 2;
    }
}
