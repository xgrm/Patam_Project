package Model;

import IO.TelnetIO;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Play {
    String file;
    volatile boolean pause;
    volatile boolean stop;
    volatile int speed;

    public Play() {
        this.pause = false;
        this.stop = false;
        this.speed = 1;
    }

    public void openCSV(String path){
        this.file = path;
    }
    public void play(){
        new Thread(()->play_1()).start();
    }
    private void play_1(){
        try {
            Socket socket= new Socket("127.0.0.1",5403);
            TelnetIO io = new TelnetIO(socket.getInputStream(),socket.getOutputStream());
            Scanner csv = new Scanner(new File(this.file));
            csv.nextLine();
            while (!this.stop){
                while (!this.pause&& csv.hasNext()){
                    String line = csv.nextLine();
                    io.write(line);
                    Thread.sleep(100*speed);
                }
            }
            io.close();
            csv.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void pause(){
        this.pause = !this.pause;
    }
    public void stop(){
        this.pause();
        this.stop = true;
    }
}
