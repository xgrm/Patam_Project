package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    volatile boolean stop;

    public Server() {
        this.stop = true;
    }

    private void startServer(int port,ClientHandler ch) {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop){
                try {
                    Socket client = null;
                    client = server.accept();
                    ch.handle(client);
                }catch (IOException e) {}
            }
            server.close();
        } catch (IOException e) {}
    }
    public void start(int port,ClientHandler ch){
        if(stop){
            this.stop = false;
            new Thread(()->startServer(port,ch)).start();
        }
    }
    public void stop(){
        stop = true;
    }
}
