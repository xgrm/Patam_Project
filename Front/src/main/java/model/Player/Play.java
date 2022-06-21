package model.Player;


import IO.TelnetIO;
import view.SerializableCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Play extends Observable {
    String path;
    volatile boolean pause;
    volatile boolean stop;
    volatile long speed;
    volatile boolean start;
    ExecutorService thread;
    int port;
    String ip;
    String[] csvData;
    volatile int timeStep;
    public Play(String data) {
        String[] tokens = data.split(" ");
        port = Integer.parseInt(tokens[1]);
        ip = tokens[0];
        this.pause = false;
        this.stop = false;
        this.speed = 1;
        this.timeStep = 1;
        thread = Executors.newFixedThreadPool(1);
    }

    public int setPath(String path) {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNext()){
                stringBuilder.append(scanner.nextLine()+"\n");
            }
            this.csvData = stringBuilder.toString().split("\n");
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.path = path;
        return csvData.length;
    }
    public void play() {
        if(!start){
            System.out.println("start");
            start = true;
            stop = false;
            pause = false;
            thread.execute(()->play_1());
        }
        else if(pause)
            pause();
    }
    private void play_1() {
        try {
            Socket socket = new Socket(ip,port);
            TelnetIO io = new TelnetIO(socket.getInputStream(),socket.getOutputStream());
            while (!this.stop) {
                for (; !this.pause && timeStep < csvData.length; timeStep++) {
                    String line = csvData[timeStep];
                    io.write(line);
                    setChanged();
                    notifyObservers(new SerializableCommand("agentData",line));
                    Thread.sleep(100/speed);
                }
                if(timeStep > csvData.length) this.stop();
            }
            io.close();
            socket.close();
            timeStep = 0;
        } catch (FileNotFoundException e) {throw new RuntimeException(e);
        } catch (IOException e) {throw new RuntimeException(e);
        } catch (InterruptedException e) {throw new RuntimeException(e);}

    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public void setSpeed(long speed){
        this.speed = speed;
    }
    public void pause(){
        this.pause = !this.pause;
    }
    public void stop(){
        this.pause = true;
        this.stop = true;
        this.start = false;
    }
}
