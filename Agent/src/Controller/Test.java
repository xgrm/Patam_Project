package Controller;

import IO.BackEndIO;
import IO.IO;
import IO.TestIO;
import Model.AgentModel;
import Server.ClientHandler;
import Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Test implements Observer {
    AgentModel model;
    Commands commands;
    HashMap<String,Float> statics;
    HashMap<String,String> properties;
    Socket backEnd;
    IO BackEndIO;
    volatile boolean stop;
    boolean back;


    public Test(AgentModel model,String propertiesPath) {
        this.model = model;
        this.model.addObserver(this);
        this.statics = new HashMap<>();
        this.commands = new Commands(model);
        this.properties = new HashMap<>();
        stop = false;
        createPropMap(propertiesPath);
        try {
            this.backEnd = new Socket(properties.get("backEndIP"),Integer.parseInt(properties.get("backEndPort")));
            BackEndIO = new TestIO(backEnd.getInputStream(),backEnd.getOutputStream());
            connectToBackEnd();
        } catch (IOException e) {throw new RuntimeException(e);}

    }

    private void createPropMap(String propertiesPath){
        try {
            Scanner propFile = new Scanner(new File(propertiesPath));
            String line;
            String[] tokens;
            while (propFile.hasNext()){
                line = propFile.nextLine();
                tokens = line.split(",");
                if (tokens.length!=2)
                    continue;
                this.properties.put(tokens[0],tokens[1]);
            }
            propFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectToBackEnd(){
        BackEndIO.write("TLV-JFK");
        new Thread(()->{
            String line;
            while ((line=BackEndIO.readLine()).intern()!=("close").intern()){
                this.exe(line);
            }
            System.out.println("OUT READ WHILE");
            BackEndIO.close();
            try {
                backEnd.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }).start();
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(model)){
            String line = (String) arg;
            BackEndIO.write(line);
        }

    }
    public void exe(String command){
        this.commands.executeCommand(command);
    }



    public void close(){
        model.closeModel();
        BackEndIO.write("close");
        System.out.println("Write close to backend");
        this.stop=true;
    }
}
