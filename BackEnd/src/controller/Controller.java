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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

public class Controller implements Observer, ClientHandler {
    BackendModel model;
    ActiveObject ac;
    ConcurrentHashMap<Integer, AgentHandler> agents;
    Server server;
    public Controller(BackendModel model) {
        this.model = model;
        this.agents = new ConcurrentHashMap<>();
        this.ac = new ActiveObject(5,model,this.agents);
        server = new Server();
        server.start(5500,this);    //TODO: FROm PROP FILE
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
                id = model.addFlight(command.getData(),"yes",-1f);  // addind a new flight to db and gets the flight id
                AgentHandler ag = new AgentHandler(client,clientIO,id,this.ac); // creating a new agent handler with the flight id
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
