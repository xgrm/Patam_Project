package viewModel;

import IO.SocketIO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.MainModel;
import viewModel.Commands.Commands;
import view.SerializableCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel extends Observable implements Observer {
    Socket backend;
    SocketIO backEndIO;
    HashMap<String,String> propMap;
    String[] symbols;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public DoubleProperty rudder;
    public DoubleProperty throttle;
    public ExecutorService threadPool;
    Commands commands;
    ConcurrentHashMap<String,Float> symbolTable;
    boolean standAlone;

    MainModel model;

    public ViewModel(String propPath,MainModel model ,boolean standAlone) {
        this.model = model;
        this.standAlone=standAlone;
        this.propMap = new HashMap<>();
        this.threadPool =  Executors.newFixedThreadPool(4);
        this.aileron = new SimpleDoubleProperty();
        this.elevator = new SimpleDoubleProperty();
        this.rudder = new SimpleDoubleProperty();
        this.throttle = new SimpleDoubleProperty();
        setListeners();
        this.symbolTable = new ConcurrentHashMap<>();
        createPropMap(propPath);
        createSymbol(propMap.get("symbolsPath"));
        this.commands = new Commands(this);
        if(!standAlone) {
            try {
                backend = new Socket(propMap.get("backEndIP"), Integer.parseInt(propMap.get("backEndPort")));
                backEndIO = new SocketIO(backend.getOutputStream());
                connectToBackend();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public ViewModel(String propPath,MainModel model){
        this(propPath,model,false);
    }
    private void setListeners(){
        aileron.addListener((o,ov,nv)-> exe(new SerializableCommand("setCommand","Aileron "+nv)));
        elevator.addListener((o,ov,nv)-> exe(new SerializableCommand("setCommand","Elevator "+nv)));
        rudder.addListener((o,ov,nv)-> exe(new SerializableCommand("setCommand","Rudder "+nv)));
        throttle.addListener((o,ov,nv)-> exe(new SerializableCommand("setCommand","Throttle "+nv)));
    }
    private void createPropMap(String propPath){
        try {
            Scanner scanner = new Scanner(new File(propPath));
            String[] tokens;
            while (scanner.hasNext()){
                tokens = scanner.nextLine().split(",");
                this.propMap.put(tokens[0],tokens[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void createSymbol(String symbolsPath){
        try {
            Scanner scanner = new Scanner(new File(symbolsPath));
            StringBuilder sb =new StringBuilder();
            String token;
            while (scanner.hasNext()){
                token = scanner.nextLine();
                this.symbolTable.put(token,0f);
                sb.append(token+" ");
            }
            this.symbols = sb.toString().trim().split(" ");
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectToBackend() {
        try {
            System.out.println("trying to send object");
            backEndIO.write(new SerializableCommand("front"," "));
            backEndIO.setInPutStream(backend.getInputStream());
            System.out.println("sending the object");
            if (backEndIO.readCommand().getCommandName().equals("ok"))
                this.threadPool.execute(() -> inFromBack());
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    private void inFromBack(){
        System.out.println("connect");
        Object command = null;
        while ((command=backEndIO.readCommand())!=null){
           this.exe((SerializableCommand) command); // execute the command from back
        }
    }
    public void exe(SerializableCommand command){
        this.threadPool.execute(()->this.commands.executeCommand(command));
    }
    public void outToBack(SerializableCommand command){
        if(!standAlone)
            backEndIO.write(command);
        else System.out.println("outToBack: "+command);
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    public void inFromCommand(SerializableCommand obj){
        setChanged();
        notifyObservers(obj);
    }

    public String[] getSymbols() {
        return symbols;
    }

    public ConcurrentHashMap<String, Float> getSymbolTable() {
        return symbolTable;
    }

    public MainModel getModel() {
        return model;
    }

    public void close(){
        if(!standAlone) {
            try {
                backEndIO.close();
                backend.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.threadPool.shutdown();
    }
}
