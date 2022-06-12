package controller.activeObject;

import controller.clientHandler.AgentHandler;
import controller.clientHandler.FrontHandler;
import model.BackendModel;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Commands{
    int id;
    ConcurrentHashMap<String,Command> commandsMap;
    ConcurrentHashMap<Integer, AgentHandler> agents;
    FrontHandler frontHandler;
    BackendModel model;
    public Commands(BackendModel model, ConcurrentHashMap<Integer, AgentHandler> agents) {
        this.model = model;
        this.agents = agents;
        this.commandsMap = new ConcurrentHashMap<>();
        this.commandsMap.put("exp",new expCommand());
        this.commandsMap.put("addRow",new addRowCommand());
        this.commandsMap.put("addFlight",new addFlightCommand());
        this.commandsMap.put("getFlight",new getFlightCommand());
        this.commandsMap.put("setCommand",new setCommand());
        this.commandsMap.put("activeAgents",new activeAgentsCommand());
        this.commandsMap.put("removeAgent",new removeAgentCommand());
        this.commandsMap.put("getKpi",new getKPICommand());

    }

    public void executeCommand(String command){
        String[] regx = command.split("~");
        this.commandsMap.get(regx[0]).execute(regx[1]);
    }

    public void setFrontHandler(FrontHandler fh) {
        this.frontHandler = fh;
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
    private class addRowCommand extends Command{ // command is: "1 ....flightData..."
        @Override
        public void execute(String command) {
            String[] tokens = command.split(" ");
            model.insertRow(Integer.parseInt(tokens[0]),tokens[1]);
        }
    }
    private class addFlightCommand extends Command{ // command is: "flightName"
        @Override
        public void execute(String command) {
            id = model.addFlight(command);
        }
    }
    private class getFlightCommand extends Command{ // command is: "id"
        @Override
        public void execute(String command) {
        System.out.println(model.getFlightById(Integer.parseInt(command)));

        }
    }
    private class setCommand extends Command{ // command is: "id alieron 1"
        @Override
        public void execute(String command) {
            String[] tokens = command.split(" ");
            agents.get(Integer.parseInt(tokens[0])).outToAgent(tokens[1]+"~"+tokens[2]);
        }
    }
    private class activeAgentsCommand extends Command{
        @Override
        public void execute(String command) {
            StringBuilder sb = new StringBuilder();
            Enumeration<Integer> keys = agents.keys();
            if(keys.hasMoreElements()) {
                keys.asIterator().forEachRemaining((key)->sb.append(key+","));
                sb.deleteCharAt(sb.length()-1);
              frontHandler.outToFront("activeAgents~"+sb.toString());
            }

            else frontHandler.outToFront("activeAgents~0");
        }

    }
    private class removeAgentCommand extends Command{ //id
        @Override
        public void execute(String command) {
            int id = Integer.parseInt(command);
            agents.remove(id,agents.get(id));
        }

    }

    private class getKPICommand extends Command{
        @Override
        public void execute(String command) {
            String[] flights = model.getKPI().split(",");
            int activeFlight = agents.size();
            int inActiveFlight = flights.length - activeFlight;
            frontHandler.outToFront("setKpi~Active "+activeFlight+" inActive "+inActiveFlight);
        }

    }

}
