package controller.activeObject;

import controller.clientHandler.AgentHandler;
import model.BackendModel;
import view.SerializableCommand;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActiveObject {

    ExecutorService threadPool;
    public Commands commands;
    public ActiveObject(int maxThreads, BackendModel model, ConcurrentHashMap<Integer, AgentHandler> agents) {

        this.threadPool = Executors.newFixedThreadPool(maxThreads);
        this.commands = new Commands(model,agents);
    }

    public void execute(SerializableCommand command){
        addToThreadPool(()->this.commands.executeCommand(command));
    }
    public void addToThreadPool(Runnable r){
        threadPool.execute(r);
    }
    public void close(){
        threadPool.shutdown();
    }
}
