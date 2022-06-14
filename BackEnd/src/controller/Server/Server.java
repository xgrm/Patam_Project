package controller.Server;

import controller.clientHandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server {
    volatile boolean stop;
    Thread mainThread;
    ServerSocket server;
    public Server() {
        this.stop = true;
    }

    private void startServer(int port, ClientHandler ch) {
        try {
            this.server = new ServerSocket(port);
            this.server.setSoTimeout(1000);
            while (!stop){
                try {
                    Socket client = server.accept();
                    ch.handel(client); // handel the client in a different thread(the controller is the handler)
                }
                catch (SocketTimeoutException r){}
            }
            server.close();
            System.out.println("close server socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(int port,ClientHandler ch){ //run the server in a different thread
        if(stop){
            this.stop = false;
            this.mainThread = new Thread(()->startServer(port,ch));
            this.mainThread.start();
        }

    }
    public void stop(){
        stop = true;
    }
}
