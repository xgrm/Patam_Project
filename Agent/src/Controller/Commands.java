package Controller;

import Model.*;

import java.util.HashMap;
import java.util.Scanner;

// this class  handles all the commands for the agent.
public class Commands {
    AgentModel model;
    HashMap<String,Command> commandMap;
    public Commands(AgentModel model) {
        this.model = model;
        this.commandMap = new HashMap<>();
        this.commandMap.put("Aileron",new AileronCommand());
        this.commandMap.put("Elevator",new ElevatorCommand());
        this.commandMap.put("Rudder",new RudderCommand());
        this.commandMap.put("Throttle",new ThrottleCommand());
        this.commandMap.put("Interpreter",new InterpreterCommand());

    }

    public void executeCommand(String command){
        String[] tokens = command.split("~");
        if(tokens.length != 2)
            return;
        this.commandMap.get(tokens[0]).execute(tokens[1]);
    }
    public abstract class Command {
        protected String description;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute(String value);
    }

    public class AileronCommand extends Command {

        public AileronCommand() {
            super("Set Aileron");
        }

        @Override
        public void execute(String value) {
            model.setAileron(Float.parseFloat(value));
        }
    }
    public class ElevatorCommand extends Command {

        public ElevatorCommand() {
            super("Set Elevator");
        }

        @Override
        public void execute(String value) {
            model.setElevator(Float.parseFloat(value));
        }
    }
    public class RudderCommand extends Command {

        public RudderCommand() {
            super("Set Rudder");
        }

        @Override
        public void execute(String value) {
            model.setRudder(Float.parseFloat(value));
        }
    }
    public class ThrottleCommand extends Command {

        public ThrottleCommand() {
            super("Set Throttle");
        }

        @Override
        public void execute(String value) {
            model.setThrottle(Float.parseFloat(value));
        }
    }
    public class InterpreterCommand extends Command {

        public InterpreterCommand() {
            super("Start Interpreter");
        }

        @Override
        public void execute(String value) {
            Scanner scanner = new Scanner(value);
            while (scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }
            model.startInterpreter(value);
        }
    }
}
