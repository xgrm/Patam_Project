package viewModel.Commands;


import javafx.application.Platform;
import viewModel.ViewModel;
import java.util.concurrent.ConcurrentHashMap;

public class Commands {
    int id = 12;
    volatile boolean agentChosen = false;
    ConcurrentHashMap<String,Command> commandsMap;
    public String[] symbols;
    ViewModel viewModel;
    ConcurrentHashMap<String,Float> symbolTable;
    public Commands(ViewModel viewModel) {
        this.commandsMap = new ConcurrentHashMap<>();
        this.viewModel = viewModel;
        this.symbols = viewModel.getSymbols();
        symbolTable = viewModel.getSymbolTable();
        commandsMap.put("getData",new getDataCommand());
        commandsMap.put("agentData",new agentDataCommand());
        commandsMap.put("setCommand",new setCommand());
        commandsMap.put("getFeaturesList",new getFeaturesListCommand());
        commandsMap.put("FeaturesList",new FeaturesListCommand());
        commandsMap.put("getActiveAgents",new getActiveAgentsCommand());
        commandsMap.put("activeAgents",new activeAgentsCommand());
        commandsMap.put("setAgentBind",new setAgentBindCommand());
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
            if(agentChosen)
                viewModel.outToBack("getData~"+id);
        }
    }
    private class agentDataCommand extends Command{

        @Override
        public void execute(String command) {
            String[] data = command.split(",");
            for (int i = 0; i < symbols.length; i++) {
                symbolTable.put(symbols[i],Float.parseFloat(data[i]));
            }
            viewModel.inFromCommand(symbolTable);
        }
    }
    private class setCommand extends Command{
        @Override
        public void execute(String command) {
            if(agentChosen) {
                viewModel.outToBack("setCommand~" + id + " " + command);
                System.out.println("setCommand~" + id + " " + command);
            }
        }
    }
    private class getFeaturesListCommand extends Command{
        @Override
        public void execute(String command) {
            viewModel.outToBack("getFeaturesList~ ");
        }
    }
    private class FeaturesListCommand extends Command{
        @Override
        public void execute(String command) {
            viewModel.inFromCommand("list~"+command);
        }
    }
    private class getActiveAgentsCommand extends Command{
        @Override
        public void execute(String command) {  ///0,1,2,3
            viewModel.outToBack("activeAgents~ ");
        }
    }

    private class activeAgentsCommand extends Command{

        @Override
        public void execute(String command) {  ///0,1,2,3
            String[] agents = command.split(",");
            if(!agents[0].equals("0"))
                viewModel.inFromCommand("agents~"+command);
            else{
                viewModel.inFromCommand("agents~none, ");
            }
        }
    }
    private class setAgentBindCommand extends Command{

        @Override
        public void execute(String command) {
            if(command.intern()!="none".intern()){
                id = Integer.parseInt(command);
                agentChosen = true;
            }
            else agentChosen = false;
        }
    }


}
