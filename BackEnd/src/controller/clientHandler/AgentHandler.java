package controller.clientHandler;

import controller.activeObject.ActiveObject;

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

    public AgentHandler(Socket agent, int id, ActiveObject ac) {
        try {
            this.id = id;
            this.ac =ac;
            this.agent = agent;
            this.io = new SocketIO(agent.getInputStream(),agent.getOutputStream());
            ac.addToThreadPool(()->{
                outToAgent("ok"); // sending to agent that the connection is ok and the back can receive data from him.
                inFromAgent();  // start getting data from agent in a different thread
            });

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void outToAgent(String command){
        this.io.write(command);
    }
    public void inFromAgent() {
        String[] tokens;
        while (io.hasNext()){
            tokens = io.readLine().split("~");
            ac.execute(tokens[0]+"~"+id+" "+tokens[1]);
        }
        io.close();
        try {
            agent.close();
            ac.execute("removeAgent~"+id); // removing agent from agent map.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
