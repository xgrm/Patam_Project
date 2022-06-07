package Interpreter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import Interpreter.Commands.Command;
import Model.AgentModel;

public class Interpreter implements Observer {

    Parser parser;
    HashMap<String,Variable> symbolTable;

    ConcurrentHashMap<String,Variable> bindTable;
    AgentModel model;

    public Interpreter(AgentModel model) {
        this.symbolTable = new HashMap<>();
        this.bindTable = new ConcurrentHashMap<>();
        this.parser = new Parser(symbolTable,bindTable,model);
        this.model = model;
        this.model.addObserver(this);
    }


    public void run(String code){
        parser.run(code);
    }

    @Override
    public void update(Observable o, Object arg) {
//        String line = (String) arg;
//        bindTable.forEach((key,value)->{
//            model.
//        });
    } //TODO: UPDATE BIND VARS FROM FG!
}
