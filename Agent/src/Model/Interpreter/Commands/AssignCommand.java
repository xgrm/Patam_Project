package Model.Interpreter.Commands;

import Model.Interpreter.Utils.SharedMemory;
import Model.Interpreter.Utils.ShuntingYardAlgorithm;
import Model.Interpreter.Utils.Variable;
import java.util.ArrayList;

public class AssignCommand extends Command{
    SharedMemory sm;
    public AssignCommand(SharedMemory sm) {
        this.sm = sm;
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
            tempVar = sm.getSymTable().get(token);
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
           sm.getSymTable().put(varName,new Variable(varName,0f,args.get(index+2),sm.model));
        }
        else {
            sm.getSymTable().put(varName,sm.getSymTable().getOrDefault(varName,new Variable(varName,0f)));
            sm.getSymTable().get(varName).setValue(calcTheExp(args,index));
        }
        return indexCount;
    }
}
