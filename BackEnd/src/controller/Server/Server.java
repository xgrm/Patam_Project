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
        long startTime,endTime;
        try {
            this.server = new ServerSocket(port);
            this.server.setSoTimeout(1000);
            while (!stop){
                try {
                    Socket client = server.accept();
                    startTime = System.nanoTime();
                    ch.handel(client);
                    endTime = System.nanoTime();
                    System.out.println("The total time in nano: "+(endTime-startTime));
                }
                catch (SocketTimeoutException r){}
            }
            server.close();
            System.out.println("close server socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(int port,ClientHandler ch){
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
