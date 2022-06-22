package controller;

import controller.Server.Server;
import controller.clientHandler.SocketIO;
import view.SerializableCommand;
import controller.clientHandler.AgentHandler;
import controller.clientHandler.ClientHandler;
import controller.activeObject.ActiveObject;
import controller.clientHandler.FrontHandler;
import model.BackendModel;

import java.beans.XMLDecoder;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Controller implements Observer, ClientHandler {
    BackendModel model;
    ActiveObject ac;
    ConcurrentHashMap<Integer, AgentHandler> agents;
    HashMap<String,String> propMap;
    Server server;
    public Controller(String propPath, BackendModel model) {
        this.model = model;
        this.agents = new ConcurrentHashMap<>();
        this.propMap = new HashMap<>();
        createPropMap(propPath);
        this.ac = new ActiveObject(Integer.parseInt(propMap.get("maxThreads")),model,this.agents);
        server = new Server();
        server.start(Integer.parseInt(propMap.get("controllerPort")),this);
    }
    private void createPropMap(String path){
        try {
            Scanner scanner = new Scanner(new File(path));
            String[] tokens;
            while (scanner.hasNext()){
                tokens = scanner.nextLine().split(",");
                this.propMap.put(tokens[0],tokens[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    @Override
    public void update(Observable o, Object arg) {

    }
    @Override
    public void handel(Socket client) {
        ac.addToThreadPool(()->addNewClient(client)); // adding a task to add new client into the activeObject
    }
    public void addNewClient(Socket client){
        try {
            System.out.println("Trying to add a new client");   //TODO: DELETE SOUT
            SocketIO clientIO = new SocketIO(client.getInputStream(),client.getOutputStream());
            System.out.println("Open the input stream");
            SerializableCommand command = (SerializableCommand)clientIO.readCommand();
            System.out.println("read the object!");
            int id;
            if(command.getCommandName().equals("agent")){ // ex for command: "agent~Name"
                String name = command.getData();
                id = model.addFlight(name,"yes",-1f);  // addind a new flight to db and gets the flight id
                AgentHandler ag = new AgentHandler(client,clientIO,id,this.ac); // creating a new agent handler with the flight id
                ag.setName(name);
                this.agents.put(id,ag); // adding the agent into the agent map.
            }
            else{
                new FrontHandler(client,clientIO,ac);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void close(){
        ac.close();
        server.stop();
    }
    @Override
   public void finalize() {
        this.close();
    }
}
