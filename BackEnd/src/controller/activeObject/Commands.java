package controller.activeObject;

import controller.clientHandler.AgentHandler;
import controller.clientHandler.FrontHandler;
import model.BackendModel;
import view.SerializableCommand;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class Commands{
    ConcurrentHashMap<String,Command> commandsMap;
    ConcurrentHashMap<Integer, AgentHandler> agents;
    FrontHandler frontHandler;
    BackendModel model;
    String featuresList=null;
    public Commands(BackendModel model, ConcurrentHashMap<Integer, AgentHandler> agents) {
        this.model = model;
        this.agents = agents;
        this.commandsMap = new ConcurrentHashMap<>();
        this.commandsMap.put("exp",new expCommand());
        this.commandsMap.put("addRow",new addRowCommand());
        this.commandsMap.put("getFlight",new getFlightCommand());
        this.commandsMap.put("setCommand",new setCommand());
        this.commandsMap.put("activeAgents",new activeAgentsCommand());
        this.commandsMap.put("removeAgent",new removeAgentCommand());
//        this.commandsMap.put("getKpi",new getKPICommand());
        this.commandsMap.put("updateFlight",new updateFlightCommand());
        this.commandsMap.put("getData",new getDataCommand());
        this.commandsMap.put("getFeaturesList",new getFeaturesListCommand());
        this.commandsMap.put("Interpreter",new InterpreterCommand());

    }

    public void executeCommand(SerializableCommand command){
        this.commandsMap.get(command.getCommandName()).execute(command);
    }
    public void setFrontHandler(FrontHandler fh) {
        this.frontHandler = fh;
    }
    private abstract class Command {
        public Command() {}
        public abstract void execute(SerializableCommand command);
    }

    private class expCommand extends Command{

        @Override
        public void execute(SerializableCommand command) {
            System.out.println(command);
        }
    }
    private class getDataCommand extends Command{ //

        @Override
        public void execute(SerializableCommand command) {//id
            AgentHandler agentHandler = agents.get(command.getId());
            if(agentHandler != null&&agentHandler.getValues()!=null){
                frontHandler.outToFront(new SerializableCommand("agentData",agentHandler.getValues()));
            }
            else frontHandler.outToFront(new SerializableCommand("noAgent"," "));
        }
    }
    private class addRowCommand extends Command{ // command is: "1 ....flightData..."
        @Override
        public void execute(SerializableCommand command) {
            model.insertRow(command.getId(),command.getData());
            agents.get(command.getId()).setValues(command.getData());
        }
    }
    private class getFlightCommand extends Command{ // command is: "id"
        @Override
        public void execute(SerializableCommand command) {
        System.out.println(model.getFlightById(command.getId()));
        }
    }
    private class setCommand extends Command{ // command is: "id alieron 1"
        @Override
        public void execute(SerializableCommand command) {
            String[] tokens = command.getData().split(" ");
            command.setCommandName(tokens[0]);
            command.setData(tokens[1]);
            agents.get(command.getId()).outToAgent(command);
        }
    }
    private class activeAgentsCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            StringBuilder sb = new StringBuilder();
            Enumeration<Integer> keys = agents.keys();
            if(keys.hasMoreElements()) {
                keys.asIterator().forEachRemaining((key)->sb.append(key+","));
                sb.deleteCharAt(sb.length()-1);
                frontHandler.outToFront(new SerializableCommand("activeAgents",sb.toString()));
            }

            else frontHandler.outToFront(new SerializableCommand("activeAgents","none, "));
        }

    }
    private class removeAgentCommand extends Command{ //id
        @Override
        public void execute(SerializableCommand command) {
            int id = command.getId();
            agents.remove(id,agents.get(id));
        }

    }
    private class updateFlightCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            String[] tokens = command.getData().split(" ");
            model.updateFlight(command.getId(),tokens[0],Float.parseFloat(tokens[1]));
        }

    }
//    private class getKPICommand extends Command{
//        @Override
//        public void execute(SerializableCommand command) {
//            String[] flights = model.getKPI().split(",");
//            int activeFlight = agents.size();
//            int inActiveFlight = flights.length - activeFlight;
//            frontHandler.outToFront("setKpi~Active "+activeFlight+" inActive "+inActiveFlight);
//        }
//
//    }
    private class getFeaturesListCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            if(featuresList==null)
                featuresList = model.getFeaturesList();
            frontHandler.outToFront(new SerializableCommand("FeaturesList",featuresList));
        }

    }
    private class InterpreterCommand extends Command{
        @Override
        public void execute(SerializableCommand command) {
            agents.get(command.getId()).outToAgent(command);
        }
    }

}
