package Model.Interpreter.Commands;

import Model.Interpreter.Utils.SharedMemory;
import Model.Interpreter.Utils.ShuntingYardAlgorithm;
import Model.Interpreter.Utils.Variable;
import java.util.ArrayList;

public class AssignCommand extends Command{
    SharedMemory sm;
    int indexCount;
    public AssignCommand(SharedMemory sm) {
        this.sm = sm;
        this.indexCount = 1;
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
            this.indexCount++;
        }
        return (float) ShuntingYardAlgorithm.calc(list);
    }

    @Override
    public int execute(ArrayList<String> args, int index) {
        String varName= args.get(index-1);
        if(args.get(index+1).equals("bind")){
            indexCount = 0;
        }
        else {
            sm.getSymTable().get(varName).setValue(calcTheExp(args,index));
        }
        return indexCount;
    }
}
