package Model.Interpreter.Commands;

import Model.Interpreter.Utils.SharedMemory;
import Model.Interpreter.Utils.ShuntingYardAlgorithm;
import Model.Interpreter.Utils.Variable;
import java.util.ArrayList;

public class WhileCommand extends Command{

    SharedMemory sm;
    public WhileCommand(SharedMemory sm) {
        this.sm = sm;
    }

    private boolean checkCondition(ArrayList<String> condition){
        ArrayList<String> parsedCondition = new ArrayList<>();
        for (String token:condition){
            Variable temp = sm.getSymTable().get(token);
            if(temp != null){
                parsedCondition.add("" + temp.getValue());
                continue;
            }
            parsedCondition.add(token);
        }
        return (ShuntingYardAlgorithm.ConditionParser(parsedCondition)==1)?true:false;
    }
    
    @Override
    public int execute(ArrayList<String> args, int index) {
        int startIndex;
        ArrayList<String> condition = new ArrayList<>();
        for (startIndex = index + 1; !args.get(startIndex).equals("{") ; startIndex++){
            condition.add(args.get(startIndex));
        }
        ArrayList<String> whileArgs = new ArrayList<>();
        int i;
        for (i = startIndex+2; !args.get(i).equals("}") ; i++) {
            whileArgs.add(args.get(i));
        }
        Command command;
        while (checkCondition(condition)){
            for (int j = 0; j < whileArgs.size(); j++) {
                command = sm.getCommands().getCommnd(whileArgs.get(j));
                if(command!=null)
                    j+= command.execute(whileArgs,j);
            }
        }
        return i-index;
    }
}
