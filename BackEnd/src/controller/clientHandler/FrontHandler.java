package controller.clientHandler;

import controller.activeObject.ActiveObject;
import view.SerializableCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class FrontHandler {
    Socket front;
    SocketIO io;
    public ActiveObject ac;

    public FrontHandler(Socket front, SocketIO io , ActiveObject ac) {

            this.ac =ac;
            this.front = front;
            this.io = io;
            ac.commands.setFrontHandler(this);
            ac.addToThreadPool(()->{
                        outToFront(new SerializableCommand("ok"," "));  // sending to front that the connection is ok and the back can receive data from him.
                        inFromFront(); // start getting data from front in a different thread
                    });
    }
    public void outToFront(SerializableCommand command){
        this.io.write(command);
    }
    public void inFromFront() {
        SerializableCommand command = null;
        while ((command = io.readCommand())!=null){
            ac.execute(command);
        }
    }
    private void close(){
        try {
            io.close();
            front.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
