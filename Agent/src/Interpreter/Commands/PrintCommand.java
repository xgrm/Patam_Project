package Interpreter.Commands;

import Interpreter.ShuntingYardAlgorithm;
import Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;

public class PrintCommand extends Command {
    HashMap<String,Variable> symbolTable;

    public PrintCommand(HashMap<String, Variable> symbolTable) {
        this.symbolTable = symbolTable;
    }

    private Float[] calcTheExp(ArrayList<String> args, int index){ //TODO: FIND ELEGANT SOLUTION TO THIS FUNC!
        int argsSize = args.size();
        String token;
        ArrayList<String> list = new ArrayList<>();
        Variable tempVar;
        for (int i = index+1 ;i < argsSize; i++) {
            token =args.get(i);
            if(token.equals("\n"))
                break;
            tempVar = symbolTable.get(token);
            if(tempVar != null){
                token = tempVar.getValue().toString();
                tempVar = null;
            }
            list.add(token);
        }
        Float[] returnValue = new Float[2];
        returnValue[0]=(float) ShuntingYardAlgorithm.calc(list);
        returnValue[1] = (float) list.size();
        return returnValue;
    }
    @Override
    public int execute(ArrayList<String> args, int index) { //TODO: FIX MULTI STRING AND EXECUTE IN GENERAL!
        if(args.get(index+1).contains("'")){
            String line = args.get(index+1);
            System.out.println(line);
            return 2;
        }
        Float[] returnValue = calcTheExp(args,index);
        System.out.println(returnValue[0]);
        return (int) Math.floor(returnValue[1])+1;
    }

}
