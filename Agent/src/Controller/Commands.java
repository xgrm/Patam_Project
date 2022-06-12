package Controller;

import Model.*;

import java.util.HashMap;

public class Commands {
    Model model;
    HashMap<String,Command> commandMap;
    public Commands(Model model) {
        this.model = model;
        this.commandMap = new HashMap<>();
        this.commandMap.put("Aileron",new AileronCommand());
        this.commandMap.put("Elevator",new ElevatorCommand());
        this.commandMap.put("Rudder",new RudderCommand());
        this.commandMap.put("Throttle",new ThrottleCommand());
    }

    public void executeCommand(String command){
        String[] tokens = command.split(" ");
        if(tokens.length != 2)
            return;
        this.commandMap.get(tokens[0]).execute(Float.parseFloat(tokens[1]));
    }
    public abstract class Command {
        protected String description;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute(float value);
    }

    public class AileronCommand extends Command {

        public AileronCommand() {
            super("Set Aileron");
        }

        @Override
        public void execute(float value) {
            model.setAileron(value);
        }
    }
    public class ElevatorCommand extends Command {

        public ElevatorCommand() {
            super("Set Elevator");
        }

        @Override
        public void execute(float value) {
            model.setElevator(value);
        }
    }
    public class RudderCommand extends Command {

        public RudderCommand() {
            super("Set Rudder");
        }

        @Override
        public void execute(float value) {
            model.setRudder(value);
        }
    }
    public class ThrottleCommand extends Command {

        public ThrottleCommand() {
            super("Set Throttle");
        }

        @Override
        public void execute(float value) {
            model.setThrottle(value);
        }
    }
}
