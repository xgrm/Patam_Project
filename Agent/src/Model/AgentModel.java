package Model;

import IO.*;
import Model.Interpreter.Interpreter;
import Server.ClientHandler;
import Server.Server;
import TimeSeries.TimeSeries;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentModel extends Observable implements Model {
    String[] symbols;
    ConcurrentHashMap<String,Float> symbolMap;

    HashMap<String,String> properties;
    TimeSeries timeSeries;
    IO outToFG;
    Server modelServer;
    Socket FlightGear;
    volatile boolean stop;
    DecimalFormat df;
    ExecutorService threadPool;
    public AgentModel(String propPath,String symbolPath) {
        stop = false;
        this.threadPool = Executors.newFixedThreadPool(2); //TODO: NOT HARD CODED
        this.df = df = new DecimalFormat("#.##");
        this.symbolMap = new ConcurrentHashMap<>();
        this.properties = new HashMap<>();
        this.createMapsFromFiles(propPath,symbolPath);
        timeSeries = new TimeSeries(symbols);
        modelServer = new Server();
        this.startServer();
        this.startClient();
    }

    @Override
    public void setAileron(double x) {
        outToFG.write(properties.get("aileron")+" "+df.format(x));
    }

    @Override
    public void setElevator(double x) {
        outToFG.write(properties.get("elevator")+" "+df.format(x));
    }

    @Override
    public void setRudder(double x) {
        outToFG.write(properties.get("rudder")+" "+df.format(x));
    }

    @Override
    public void setThrottle(double x) {
        outToFG.write(properties.get("throttle")+" "+df.format(x));
    }
    public void startInterpreter(String code){
        Interpreter interpreter = new Interpreter(this,"src/external_files/FlightGearParam.txt");
        this.threadPool.execute(()->interpreter.run(code));
    }
    public void sendToFG(String path, Float value) {
        outToFG.write("set "+path+" "+df.format(value));
    }
    @Override
    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    @Override
    public void handle(Socket client) {
        this.threadPool.execute(()->inFromFG(client));
        this.modelServer.stop();
    }
    private void inFromFG(Socket client){
        TelnetIO inFromFG = null;
        try {
            inFromFG = new TelnetIO(client.getInputStream(),client.getOutputStream());
            String line;
            String[] propValArr;
            while (!stop){
                while (!stop && inFromFG.hasNext()){
                    line = inFromFG.readLine();
                    propValArr = line.split(",");
                    for (int i = 0; i < symbols.length; i++) {
                        symbolMap.put(symbols[i],Float.parseFloat(propValArr[i]));
                    }
                    if(!stop){
                        this.setChanged();
                        this.notifyObservers(line);
                    }
                    timeSeries.addLine(line);
                }
            }
            System.out.println("closing the fg client");  //TODO: DELETE!
            inFromFG.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createMapsFromFiles(String propPath,String symbolPath){
        try {
            Scanner propScanner = new Scanner(new File(propPath));
            Scanner symbolScanner = new Scanner(new File(symbolPath));
            String line;
            String[] tokens;
            while (propScanner.hasNext()){
                line = propScanner.nextLine();
                tokens = line.split(",");
                properties.put(tokens[0],tokens[1]);
            }
            StringBuilder symbolString = new StringBuilder();
            while (symbolScanner.hasNext()){
                line = symbolScanner.nextLine();
                symbolString.append(line+",");
                symbolMap.put(line,0F);
            }
            symbolString.deleteCharAt(symbolString.length()-1);
            symbols = symbolString.toString().split(",");
            propScanner.close();
            symbolScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void startServer(){
        modelServer.start(Integer.parseInt(properties.get("serverPort")),this);
    }
    private void startClient(){
        try {
            Thread.sleep(5*1000);
            this.FlightGear = new Socket(properties.get("fgIp"),Integer.parseInt(properties.get("fgPort")));
            outToFG = new TelnetIO(FlightGear.getInputStream(),FlightGear.getOutputStream());
        } catch (IOException | InterruptedException e) {throw new RuntimeException(e);}
    }

    public void closeModel(){
        try {
            this.stop = true;
            outToFG.close();
            FlightGear.close();
            timeSeries.exportCSV("src/external_files/FlightData.csv");
            this.threadPool.shutdown();
            System.out.println("closed");
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    @Override
    public void finalize(){
        this.closeModel();
    }
}
