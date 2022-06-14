package controller;

import controller.Server.Server;
import controller.clientHandler.AgentHandler;
import controller.clientHandler.ClientHandler;
import controller.activeObject.ActiveObject;
import controller.clientHandler.FrontHandler;
import controller.clientHandler.SocketIO;
import model.BackendModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String[] tokens = in.readLine().split("~");  // getting the command from the clinet
            int id;
            if(tokens[0].equals("agent")){ // ex for command: "agent~Name"
                id = model.addFlight(tokens[1],"yes",-1f);  // addind a new flight to db and gets the flight id
                AgentHandler ag = new AgentHandler(client,id,this.ac); // creating a new agent handler with the flight id
                this.agents.put(id,ag); // adding the agent into the agent map.
            }
            else{
                new FrontHandler(client,ac);
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
