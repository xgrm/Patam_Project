package Model.Interpreter.Commands;

import Model.Interpreter.ShuntingYardAlgorithm;
import Model.Interpreter.Variable;

import java.util.ArrayList;
import java.util.HashMap;

public class WhileCommand extends Command{

    CommandFactory cf;
    HashMap<String, Variable> symbolTable;
    public WhileCommand(CommandFactory cf, HashMap<String, Variable> symbolTable) {
        this.cf = cf;
        this.symbolTable = symbolTable;
    }

    private boolean checkCondition(ArrayList<String> condition){
        ArrayList<String> parsedCondition = new ArrayList<>();
        for (String token:condition){
            Variable temp = symbolTable.get(token);
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
                command = cf.getCommnd(whileArgs.get(j));
                if(command!=null)
                    j+= command.execute(whileArgs,j);
            }
        }
        return i-index;
    }
}
