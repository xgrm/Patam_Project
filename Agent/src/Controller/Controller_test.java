package Controller;

import IO.*;
import Model.*;
import Server.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Controller_test implements Observer, ClientHandler {
    AgentModel model;
    Commands commands;
    HashMap<String,Float> statics;
    HashMap<String,String> properties;
    Socket backEnd;
    Server controllerServer;
    BackEndIO outToBackEnd;
    volatile boolean stop;
    boolean back;


    public Controller_test(AgentModel model, String propertiesPath) {
        this.model = model;
        this.model.addObserver(this);
        this.statics = new HashMap<>();
        this.commands = new Commands(model);
        this.properties = new HashMap<>();
        this.controllerServer = new Server();
        stop = false;
        createPropMap(propertiesPath);
        try {
            this.backEnd = new Socket(properties.get("backEndIP"),Integer.parseInt(properties.get("backEndPort")));
            outToBackEnd = new BackEndIO(backEnd.getInputStream(),backEnd.getOutputStream());
        } catch (IOException e) {throw new RuntimeException(e);}
        this.controllerServer.start(Integer.parseInt(properties.get("controllerPort")),this);
        connectToBackEnd();
    }

    public Controller_test(AgentModel model, String propertiesPath, boolean back) {
        this.back = back;
        this.model = model;
        this.model.addObserver(this);
        this.statics = new HashMap<>();
        this.commands = new Commands(model);
        this.properties = new HashMap<>();
    }

    private void createPropMap(String propertiesPath){
        try {
            Scanner propFile = new Scanner(new File(propertiesPath));
            String line;
            String[] tokens;
            while (propFile.hasNext()){
                line = propFile.nextLine();
                tokens = line.split(",");
                if (tokens.length!=2)
                    continue;
                this.properties.put(tokens[0],tokens[1]);
            }
            propFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectToBackEnd(){
        outToBackEnd.write("Socket 127.0.0.1 "+properties.get("controllerPort"));
    }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update!");
        if(o.equals(model)){
            String line = (String) arg;
            System.out.println(line);
            if(!(outToBackEnd==null))
                outToBackEnd.write(line);
        }

    }
    public void exe(String command){
        this.commands.executeCommand(command);
    }
    @Override
    public void handle(InputStream in, OutputStream out) {
        Scanner inFromBackend = new Scanner(in);
        while (!stop){
            while (!stop && inFromBackend.hasNext()){
                this.commands.executeCommand(inFromBackend.nextLine());
            }
        }
        inFromBackend.close();
    }

    public void close(){
        model.closeModel();
        if(!back)
            return;
        try {
            outToBackEnd.write("close");
            backEnd.close();
            outToBackEnd.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        this.controllerServer.stop();
        this.stop=true;
    }
}
