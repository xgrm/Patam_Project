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
                    Socket client = server.accept();
                    ch.handle(client.getInputStream(),client.getOutputStream());
                    client.close();
                }
                catch (SocketTimeoutException e){}
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
