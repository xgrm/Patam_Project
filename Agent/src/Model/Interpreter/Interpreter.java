package Model.Interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public Interpreter(AgentModel model, String paramsPath) {
        this.symbolTable = new HashMap<>();
        this.bindTable = new ConcurrentHashMap<>();
        this.parser = new Parser(symbolTable,bindTable,model);
        this.model = model;
        setProps(paramsPath);
        this.model.addObserver(this);
        threadPool = Executors.newFixedThreadPool(2);
    }

    private void setProps(String paramsPath){
        StringBuilder sb = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(paramsPath)); // TODO: CHECK FOR TAB!
            while (scanner.hasNext()){
                sb.append(scanner.nextLine()+ " ");
            }
            sb.deleteCharAt(sb.length()-1);
            this.props = sb.toString().split(" ");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void run(String code){ //TODO: WHAT TO DO WITH THE MODEL!!
        this.threadPool.execute(()->{parser.run(code);
            model.deleteObserver(this);
            this.threadPool.shutdown();
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        this.threadPool.execute(()->{
            String line = (String) arg;
            String[] lines = line.split(",");
            Variable v=null;
            for (int i = 0; i < props.length; i++) { // iterate on the fg props to find if one of them is bind
                if((v=bindTable.get(props[i]))!=null){  // if the map find prop that bind
                    v.setValue(Float.parseFloat(lines[i])); // we change the prop!(:
                }
            }
        });
    }
}
