package Controller;


import IO.IO;
import IO.SocketIO;
import Model.AgentModel;


import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Controller implements Observer {
    AgentModel model;
    Commands commands;
    HashMap<String,Float> statics;
    HashMap<String,String> properties;
    Socket backEnd;
    IO BackEndIO;
    boolean standAlone;



    public Controller(AgentModel model, String propertiesPath, boolean standAlone) {
        this.standAlone = standAlone;
        this.model = model;
        this.model.addObserver(this);
        this.statics = new HashMap<>();
        this.commands = new Commands(model);
        this.properties = new HashMap<>();
        createPropMap(propertiesPath);
        if(!standAlone) {
            try {
                this.backEnd = new Socket(properties.get("backEndIP"), Integer.parseInt(properties.get("backEndPort")));
                BackEndIO = new SocketIO(backEnd.getInputStream(), backEnd.getOutputStream());
                connectToBackEnd();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public Controller(AgentModel model, String propertiesPath){
        this(model,propertiesPath,false);
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
        BackEndIO.write("agent~"+this.properties.get("agentDestination"));
        if(BackEndIO.readLine().equals("ok"))
            new Thread(()->inFromBack()).start();
    }
    private void inFromBack(){
        String line;
        while (BackEndIO.hasNext()){
            line = BackEndIO.readLine();
            System.out.println(line);
            this.exe(line);
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(model)){
            String line = (String) arg;
            if(!this.standAlone)
                BackEndIO.write(line);
            else System.out.println(line);
        }

    }
    public void exe(String command){
        this.commands.executeCommand(command);
    }



    public void close() {
        model.closeModel();
        if (!this.standAlone) {
            BackEndIO.close();
            try {
                backEnd.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
}
