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
        long startTime,endTime;
        startTime = System.nanoTime();
        this.model = model;
        this.agents = new ConcurrentHashMap<>();
        this.ac = new ActiveObject(5,model,this.agents);
        server = new Server();
        server.start(5500,this);    //TODO: FRON PROP FILE
        endTime = System.nanoTime();
        System.out.println("The total time in nano: "+(endTime-startTime));
    }



    public void testCommands(String commnad){
        ac.execute(commnad);
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void handel(Socket client) {
        ac.addToThreadPool(()->addNewClient(client));
    }

    public void addNewClient(Socket client){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String[] tokens = in.readLine().split("~");
            int id;
            if(tokens[0].equals("agent")){ //agent~TLV-TLV
                id = model.addFlight(tokens[1]);
                AgentHandler ag = new AgentHandler(client,id,this.ac);
                this.agents.put(id,ag);
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
