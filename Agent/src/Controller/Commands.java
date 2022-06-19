package Controller;

import Model.*;
import view.SerializableCommand;

import java.io.IOException;
import java.util.HashMap;

// this class  handles all the commands for the agent.
public class Commands {
    AgentModel model;
    HashMap<String,Command> commandMap;
    Controller controller;
    public Commands(AgentModel model,Controller controller) {
        this.model = model;
        this.controller = controller;
        this.commandMap = new HashMap<>();
        this.commandMap.put("Aileron",new AileronCommand());
        this.commandMap.put("Elevator",new ElevatorCommand());
        this.commandMap.put("Rudder",new RudderCommand());
        this.commandMap.put("Throttle",new ThrottleCommand());
        this.commandMap.put("Interpreter",new InterpreterCommand());
        this.commandMap.put("addRow",new addRowCommand());
        this.commandMap.put("updateFlight",new updateFlightCommand());

    }

    public void executeCommand(SerializableCommand command){
        this.commandMap.get(command.getCommandName()).execute(command);
    }
    public abstract class Command {
        protected String description;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute(SerializableCommand value);
    }

    public class AileronCommand extends Command {

        public AileronCommand() {
            super("Set Aileron");
        }

        @Override
        public void execute(SerializableCommand value) {
            System.out.println(value.getCommandName()+" "+value.getData());
            model.setAileron(Float.parseFloat(value.getData()));
        }
    }
    public class ElevatorCommand extends Command {

        public ElevatorCommand() {
            super("Set Elevator");
        }

        @Override
        public void execute(SerializableCommand value) {
            System.out.println(value.getCommandName()+" "+value.getData());
            model.setElevator(Float.parseFloat(value.getData()));
        }
    }
    public class RudderCommand extends Command {

        public RudderCommand() {
            super("Set Rudder");
        }

        @Override
        public void execute(SerializableCommand value) {
            System.out.println(value.getCommandName()+" "+value.getData());
            model.setRudder(Float.parseFloat(value.getData()));
        }
    }
    public class ThrottleCommand extends Command {

        public ThrottleCommand() {
            super("Set Throttle");
        }

        @Override
        public void execute(SerializableCommand value) {
            System.out.println(value.getCommandName()+" "+value.getData());
            model.setThrottle(Float.parseFloat(value.getData()));
        }
    }
    public class InterpreterCommand extends Command {

        public InterpreterCommand() {
            super("Start Interpreter");
        }

        @Override
        public void execute(SerializableCommand value) {
            model.startInterpreter(value.getData());
        }
    }
    public class addRowCommand extends Command {

        public addRowCommand() {
            super("Send to back the row of data");
        }

        @Override
        public void execute(SerializableCommand value) {
            controller.outToBack(value);
        }
    }
    public class updateFlightCommand extends Command {

        public updateFlightCommand() {
            super("Send to back the end of the flight");
        }

        @Override
        public void execute(SerializableCommand value) {
            try {
            controller.outToBack(value);
            controller.BackEndIO.close();
            controller.backEnd.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
}
