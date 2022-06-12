package controller.clientHandler;

import controller.activeObject.ActiveObject;

import java.io.IOException;
import java.net.Socket;

public class AgentHandler{
    Socket agent;
    SocketIO io;
    public int id;
    ActiveObject ac;

    public AgentHandler(Socket agent,int id, ActiveObject ac) {
        try {
            this.id = id;
            this.ac =ac;
            this.agent = agent;
            this.io = new SocketIO(agent.getInputStream(),agent.getOutputStream());
            ac.addToThreadPool(()->{
                outToAgent("ok");
                inFromAgent();
            });

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void outToAgent(String command){
        this.io.write(command);
    }
    public void inFromAgent() {
        while (io.hasNext()){
            ac.execute("addRow~"+id+" "+io.readLine());
        }
        io.close();
        try {
            agent.close();
            ac.execute("removeAgent~"+id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}