package controller.clientHandler;

import controller.activeObject.ActiveObject;

import java.io.IOException;
import java.net.Socket;

public class FrontHandler {
    Socket front;
    SocketIO io;
    ActiveObject ac;

    public FrontHandler(Socket front, ActiveObject ac) {
        try {
            this.ac =ac;
            this.front = front;
            this.io = new SocketIO(front.getInputStream(),front.getOutputStream());
            ac.commands.setFrontHandler(this);
            ac.addToThreadPool(()->{
                        outToFront("ok");  // sending to front that the connection is ok and the back can receive data from him.
                        inFromFront(); // start getting data from front in a different thread
                    });

        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public void outToFront(String command){
        this.io.write(command);
    }
    public void inFromFront() {
        String line;
        while (io.hasNext()){
            line = io.readLine();
            ac.execute(line);
        }
        io.close();
        try {
            front.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
