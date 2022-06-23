package model.Player;


import IO.TelnetIO;
import view.SerializableCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

public class Play extends Observable {
    String path;
    volatile boolean start;
    int port;
    String ip;
    String[] csvData;
    volatile int timeStep;
    TelnetIO io;
    Socket socket;
    public Play(String data) {
        String[] tokens = data.split(" ");
        port = Integer.parseInt(tokens[1]);
        ip = tokens[0];
        this.start = false;
        this.timeStep = 1;
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
            this.path = path;
            return csvData.length;
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    public void play() {
        if(!start) {
            try {
                socket = new Socket(ip, port);
                io = new TelnetIO(socket.getInputStream(), socket.getOutputStream());
                start = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setTimeStep(int timeStep) {
        if(this.csvData==null)
            return;
        this.timeStep = timeStep;
        String line = csvData[timeStep];
        io.write(line);
        setChanged();
        notifyObservers(new SerializableCommand("agentData",line));
    }
    public void stop(){
        try {
            start = false;
            socket.close();
            io.close();
            timeStep = 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
