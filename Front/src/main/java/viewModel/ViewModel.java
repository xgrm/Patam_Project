package viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.MainModel;
import model.utils.IO.BackEndIO;
import viewModel.Commands.Commands;

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
    BackEndIO backEndIO;
    HashMap<String,String> propMap;
    String[] symbols;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public DoubleProperty rudder;
    public DoubleProperty throttle;
    ExecutorService threadPool;
    Commands commands;
    ConcurrentHashMap<String,Float> symbolTable;
    boolean standAlone;
    public ViewModel(String propPath ,boolean standAlone) {
        this.standAlone=standAlone;
        this.propMap = new HashMap<>();
        this.threadPool = Executors.newFixedThreadPool(4); //TODO: GET THE NUM OF THREADS
        this.aileron = new SimpleDoubleProperty();
        this.elevator = new SimpleDoubleProperty();
        this.rudder = new SimpleDoubleProperty();
        this.throttle = new SimpleDoubleProperty();
        setListeners();
        this.commands = new Commands(this);
        this.symbolTable = new ConcurrentHashMap<>();
        createPropMap(propPath);
        createSymbol(propMap.get("symbolsPath"));
        if(!standAlone) {
            try {
                backend = new Socket(propMap.get("backEndIP"), Integer.parseInt(propMap.get("backEndPort")));
                backEndIO = new BackEndIO(backend.getInputStream(), backend.getOutputStream());
                connectToBackend();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public ViewModel(String propPath){
        this(propPath,false);
    }
    private void setListeners(){
        aileron.addListener((o,ov,nv)-> exe("setCommand~Aileron "+nv));
        elevator.addListener((o,ov,nv)-> exe("setCommand~Elevator "+nv));
        rudder.addListener((o,ov,nv)-> exe("setCommand~Rudder "+nv));
        throttle.addListener((o,ov,nv)-> exe("setCommand~Throttle "+nv));
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
    private void connectToBackend(){
        backEndIO.write("front~ ");
        if(backEndIO.readLine().equals("ok"))
            this.threadPool.execute(()->inFromBack());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                exe("getData~ ");
            }
        }, 0l, 100l);
    }
    private void inFromBack(){
        System.out.println("connect");
        String line;
        while (backEndIO.hasNext()){
            line = backEndIO.readLine();
           this.exe(line); // execute the command from back
        }
    }
    public void exe(String command){
        System.out.println("from exe: "+command);
        threadPool.execute(()->{

            this.commands.executeCommand(command);
        });
    }
    public void outToBack(String command){
        if(!standAlone)
            backEndIO.write(command);
        else System.out.println("outToBack: "+command);
    }
    @Override
    public void update(Observable o, Object arg) {

    }
    public void test(String s){
        String[] data = s.split(",");
        for (int i = 0; i < symbols.length; i++) {
            this.symbolTable.put(symbols[i],Float.parseFloat(data[i]));
        }
        setChanged();
        notifyObservers(this.symbolTable);
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
