package viewModel.Commands;


import viewModel.ViewModel;
import java.util.concurrent.ConcurrentHashMap;

public class Commands {
    int id = 11;
    ConcurrentHashMap<String,Command> commandsMap;

    ViewModel viewModel;
    public Commands(ViewModel viewModel) {
        this.commandsMap = new ConcurrentHashMap<>();
        this.viewModel = viewModel;
        commandsMap.put("getData",new getDataCommand());
        commandsMap.put("agentData",new agentDataCommand());
        commandsMap.put("setCommand",new setCommand());
    }

    public void executeCommand(String command){
        String[] tokens = command.split("~");
        this.commandsMap.get(tokens[0]).execute(tokens[1]);
    }



    private abstract class Command {
        public Command() {}
        public abstract void execute(String command);
    }

    private class expCommand extends Command{
        @Override
        public void execute(String command) {
            System.out.println(command);
        }
    }

    private class getDataCommand extends Command{

        @Override
        public void execute(String command) {
            viewModel.outToBack("getData~"+id);
        }
    }
    private class agentDataCommand extends Command{

        @Override
        public void execute(String command) {
            viewModel.test(command);
        }
    }
    private class setCommand extends Command{
        @Override
        public void execute(String command) {
            viewModel.outToBack("setCommand~"+id+" "+command);
            System.out.println("setCommand~"+id+" "+command);
        }
    }


}
