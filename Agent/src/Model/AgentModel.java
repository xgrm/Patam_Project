package Model;

import IO.*;
import Server.ClientHandler;
import Server.Server;
import TimeSeries.TimeSeries;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class AgentModel extends Observable implements Model {
    String[] symbols;
    HashMap<String,Float> symbolMap;
    HashMap<String,String> properties;
    TimeSeries timeSeries;
    IO outToFG;
    Server modelServer;
    Socket FlightGear;
    volatile boolean stop;

    public AgentModel(String propPath,String symbolPath) {
        stop = false;
        this.symbolMap = new HashMap<>();
        this.properties = new HashMap<>();
        this.createMapsFromFiles(propPath,symbolPath);
        timeSeries = new TimeSeries(symbols);
        modelServer = new Server();
        this.startServer();
        this.startClient();
    }

    @Override
    public void setAileron(double x) {
        outToFG.write(properties.get("aileron")+" "+x);
    }

    @Override
    public void setElevator(double x) {
        outToFG.write(properties.get("elevator")+" "+x);
    }

    @Override
    public void setRudder(double x) {
        outToFG.write(properties.get("rudder")+" "+x);
    }

    @Override
    public void setThrottle(double x) {
        outToFG.write(properties.get("throttle")+" "+x);
    }

    @Override
    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    @Override
    public void handle(InputStream in, OutputStream out) {
        TelnetIO inFromFG = new TelnetIO(in,out);
        String line;
        String[] propValArr;
        while (!stop){
            while (!stop && inFromFG.hasNext()){
                line = inFromFG.readLine();
                propValArr = line.split(",");
                for (int i = 0; i < symbols.length; i++) {
                    symbolMap.put(symbols[i],Float.parseFloat(propValArr[i]));
                }
                this.setChanged();
                this.notifyObservers(line);
                timeSeries.addLine(line);
            }
        }
        inFromFG.close();
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
        modelServer.stop();
        this.stop = true;
        outToFG.close();
        try {
            FlightGear.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        timeSeries.exportCSV("FlightData.csv");
    }
    @Override
    public void finalize(){
        this.closeModel();
    }
}
