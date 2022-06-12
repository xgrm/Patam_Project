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

    // calcs value in case its an expression
    private float[] calcTheExp(ArrayList<String> args, int index){
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
                token = "" + tempVar.getValue();
                tempVar = null;
            }
            list.add(token);
        }
        float[] returnValue = new float[2];
        returnValue[0]=(float) ShuntingYardAlgorithm.calc(list);
        returnValue[1] = (float) list.size();
        return returnValue;
    }
    @Override
    public int execute(ArrayList<String> args, int index) {
        if(args.get(index+1).charAt(0) == '"'){
            String line = args.get(index+1);
            System.out.println(line.substring(1, line.length()-1));
            return 2;
        }
        float[] returnValue = calcTheExp(args,index);
        System.out.println(returnValue[0]);
        return (int) Math.floor(returnValue[1])+1;
    }
}
