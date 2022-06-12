package Model.Interpreter.Commands;

import Model.Interpreter.ShuntingYardAlgorithm;
import Model.Interpreter.Variable;
import Model.AgentModel;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignCommand extends Command{
    HashMap<String,Variable> symbolTable;
    AgentModel model;
    public AssignCommand(HashMap<String, Variable> symbolTable, AgentModel model) {
        this.symbolTable = symbolTable;
        this.model = model;
    }

    //calculates the value of the input in case its an expressiom/symbol
    private float calcTheExp(ArrayList<String> args, int index){
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
        return (float) ShuntingYardAlgorithm.calc(list);
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        int indexCount = 1;
        String varName= args.get(index-1);
        if(args.get(index+1).equals("bind")){
            indexCount = 0;
           symbolTable.put(varName,new Variable(varName,0f,args.get(index+2),model));
        }
        else {
            symbolTable.put(varName,symbolTable.getOrDefault(varName,new Variable(varName,0f)));
            symbolTable.get(varName).setValue(calcTheExp(args,index));
        }
        return indexCount;
    }
}
