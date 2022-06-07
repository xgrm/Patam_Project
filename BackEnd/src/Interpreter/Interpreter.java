package Interpreter;

import java.util.Queue;

import Interpreter.Commands.Command;

public class Interpreter {

    Parser parser;

    public Interpreter() {
        parser = new Parser();
    }

    public Queue<Command> getCommands(String code) {
        return null;
    }
}
