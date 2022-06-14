package Model.Interpreter.Commands;

import java.util.ArrayList;

public class SleepCommand extends Command {

    @Override
    public int execute(ArrayList<String> args, int index) {
        try {
            Thread.sleep(Long.parseLong(args.get(index+1)));
        } catch (Exception e) {
            System.out.println("error in sleep command");
        }
        return 1;
    }
}
