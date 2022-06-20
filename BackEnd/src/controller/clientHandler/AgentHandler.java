package controller.clientHandler;

import controller.activeObject.ActiveObject;
import view.SerializableCommand;

import java.io.IOException;
import java.net.Socket;

public class AgentHandler{
    Socket agent;
    SocketIO io;
    public int id;
    ActiveObject ac;
    String values;

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public AgentHandler(Socket agent, SocketIO io,int id, ActiveObject ac) {
            this.id = id;
            this.ac =ac;
            this.agent = agent;
            this.io = io;
            ac.addToThreadPool(()->{
                outToAgent(new SerializableCommand("ok"," ")); // sending to agent that the connection is ok and the back can receive data from him.
                inFromAgent();  // start getting data from agent in a different thread
            });
    }

    public void outToAgent(SerializableCommand command){
        this.io.write(command);
    }
    public void inFromAgent() {
        Object command = null;
        while ((command = io.readCommand())!= null){
            SerializableCommand serializableCommand = (SerializableCommand) command;
            serializableCommand.setId(id);
            ac.execute(serializableCommand);
        }
        SerializableCommand serializableCommand = new SerializableCommand("removeAgent","");
        serializableCommand.setId(id);
        ac.execute(serializableCommand); // removing agent from agent map.
        close();
    }
    private void close(){
        try {
            io.close();
            agent.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
