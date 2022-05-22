package Controller;

import IO.BackEndIO;
import Model.AgentModel;
import Server.ClientHandler;
import Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Controller implements Observer, ClientHandler {
    AgentModel model;
    Commands commands;
    HashMap<String,Float> statics;
    HashMap<String,String> properties;
    Socket backEnd;
    Server controllerServer;
    BackEndIO outToBackEnd;
    volatile boolean stop;



    public Controller(AgentModel model,String propertiesPath) {
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
        if(o.equals(model)){
            String line = (String) arg;
            outToBackEnd.write(line);
        }

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
        try {
            outToBackEnd.write("close");
            backEnd.close();
            outToBackEnd.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        this.controllerServer.stop();
        this.stop=true;
    }
}
