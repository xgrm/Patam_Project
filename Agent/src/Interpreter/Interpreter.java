package Interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Interpreter.Commands.Command;
import Model.AgentModel;

public class Interpreter implements Observer {

    Parser parser;
    // maps vars to values
    HashMap<String,Variable> symbolTable;
    // code var to fg var map
    ConcurrentHashMap<String,Variable> bindTable;
    AgentModel model;
    String[] props;
    ExecutorService threadPool;
    public Interpreter(AgentModel model) {
        this.symbolTable = new HashMap<>();
        this.bindTable = new ConcurrentHashMap<>();
        this.parser = new Parser(symbolTable,bindTable,model);
        this.model = model;
        setProps();
        this.model.addObserver(this);
        threadPool = Executors.newFixedThreadPool(2);
    }

    private void setProps(){
        StringBuilder sb = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/external_files/t.txt"));
            while (scanner.hasNext()){
                sb.append(scanner.nextLine()+ " ");
            }
            sb.deleteCharAt(sb.length()-1);
            this.props = sb.toString().split(" ");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void run(String code){
        this.threadPool.execute(()->{parser.run(code);
            model.closeModel();
            this.threadPool.shutdown();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        this.threadPool.execute(()->{
            String line = (String) arg;
            String[] lines = line.split(",");
            Variable v=null;
            for (int i = 0; i < props.length; i++) {
                if((v=bindTable.get(props[i]))!=null){
                    v.setValue(Float.parseFloat(lines[i]));
                }
            }
        });
    } //TODO: UPDATE BIND VARS FROM FG!
}
