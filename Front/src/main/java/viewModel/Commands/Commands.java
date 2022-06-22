package viewModel.Commands;


import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.CorrelatedFeatures;
import view.SerializableCommand;
import viewModel.ViewModel;

import java.awt.image.VolatileImage;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Commands {
    int id;
    int mapData;
    volatile boolean agentChosen = false;
    ConcurrentHashMap<String,Command> commandsMap;
    String[] symbols;
    ViewModel viewModel;
    ConcurrentHashMap<String,Float> symbolTable;
    public Commands(ViewModel viewModel) {
        this.commandsMap = new ConcurrentHashMap<>();
        this.viewModel = viewModel;
        mapData = 1;
        symbols = viewModel.getSymbols();
        symbolTable = viewModel.getSymbolTable();
        commandsMap.put("getData",new getDataCommand());
        commandsMap.put("agentData",new agentDataCommand());
        commandsMap.put("setCommand",new setCommand());
        commandsMap.put("getFeaturesList",new getFeaturesListCommand());
        commandsMap.put("FeaturesList",new FeaturesListCommand());
        commandsMap.put("getActiveAgents",new getActiveAgentsCommand());
        commandsMap.put("activeAgents",new activeAgentsCommand());
        commandsMap.put("setAgentBind",new setAgentBindCommand());
        commandsMap.put("noAgent",new noAgentCommand());
        commandsMap.put("Interpreter",new InterpreterCommand());
        commandsMap.put("getCorrelatedFeatures",new getCorrelatedFeaturesCommand());
        commandsMap.put("play",new playCommand());
        commandsMap.put("stop",new stopCommand());
        commandsMap.put("setPath",new setPathCommand());
        commandsMap.put("setMapData",new setMapDataCommand());
        commandsMap.put("mapData",new mapDataCommand());
    }

    public void executeCommand(SerializableCommand command){
        this.commandsMap.get(command.getCommandName()).execute(command);
    }



    private abstract class Command {
        public Command() {}
        public abstract void execute(SerializableCommand command);
    }

    private class expCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            System.out.println(command.getData());
        }
    }

    private class getDataCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            if(agentChosen) {
                if(mapData==1)
                    command.setObject(1);
                else command.setObject(0);
                command.setId(id);
                viewModel.outToBack(command);
            }
        }
    }
    private class agentDataCommand extends Command{
        @Override
        public void execute(SerializableCommand command) { //TODO: CHECK IF TO SEND THE MAP FROM AGENT
            String[] data = command.getData().split(",");
            if (data.length == symbols.length) {
                    for (int i = 0; i < symbols.length; i++) {
                        symbolTable.put(symbols[i], Float.parseFloat(data[i]));
                    }
                    command.setDataMap(new HashMap<>(symbolTable));
                    viewModel.inFromCommand(command);
                viewModel.threadPool.execute(()->{
                    List<AnomalyReport> anomalyReport = viewModel.getModel().detectFromLine(symbolTable);
                    if(!anomalyReport.isEmpty()){
                        for (AnomalyReport ar : anomalyReport){
                            viewModel.inFromCommand(new SerializableCommand("anomalyDetectet",ar.description));
                        }
                    }
                });
            }
        }
    }
    private class setCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            if(agentChosen) {
                command.setId(id);
                viewModel.outToBack(command);
            }
        }
    }
    private class getFeaturesListCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            viewModel.outToBack(command);
        }
    }
    private class FeaturesListCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            viewModel.inFromCommand(command);
        }
    }
    private class getActiveAgentsCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {///0,1,2,3
            command.setCommandName("activeAgents");
            viewModel.outToBack(command);
        }
    }
    private class activeAgentsCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {  ///0,1,2,3
                viewModel.inFromCommand(command);
        }
    }
    private class setAgentBindCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            if(command.getData().intern()!="none"){
                id = command.getId();
                agentChosen = true;
            }
            else agentChosen = false;
        }
    }
    private class noAgentCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            viewModel.inFromCommand(new SerializableCommand("agents","none, "));
            viewModel.inFromCommand(command);
            agentChosen = false;
        }
    }
    private class InterpreterCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            command.setId(id);
            viewModel.outToBack(command);
        }
    }
    private class getCorrelatedFeaturesCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            HashMap<String, CorrelatedFeatures> correlatedFeatures =  viewModel.getModel().getCorrelatedFeatures();
            command.setCommandName("correlatedFeatures");
            command.setObject(correlatedFeatures);
            viewModel.inFromCommand(command);
        }
    }
    private class playCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            viewModel.getModel().play();
        }
    }
    private class stopCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            viewModel.getModel().stop();
        }
    }
    private class setPathCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            viewModel.getModel().setPlayerPath(command.getData());
        }
    }
    private class setMapDataCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            mapData = command.getId();
        }
    }
    private class mapDataCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            viewModel.inFromCommand(command);
        }
    }
}
