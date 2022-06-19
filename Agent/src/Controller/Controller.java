package Controller;


import IO.IO;
import IO.SocketIO;
import Model.AgentModel;
import TimeSeries.TimeSeries;
import view.SerializableCommand;


import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Observer {
    AgentModel model;
    Commands commands;
    HashMap<String,Float> statistics;
    HashMap<String,String> properties;
    Socket backEnd;
    SocketIO BackEndIO;
    boolean standAlone;
    ExecutorService threadPool;

    public Controller(AgentModel model, String propertiesPath, boolean standAlone) {
        this.threadPool = Executors.newFixedThreadPool(2);
        this.standAlone = standAlone;
        this.model = model;
        this.model.addObserver(this);
        this.statistics = new HashMap<>();
        this.commands = new Commands(model,this);
        this.properties = new HashMap<>();
        createPropMap(propertiesPath);
        if(!standAlone) {
            try {
                this.backEnd = new Socket(properties.get("backEndIP"), Integer.parseInt(properties.get("backEndPort"))); //connect to backend
                BackEndIO = new SocketIO(backEnd.getOutputStream());
                this.threadPool.execute(()->connectToBackEnd()); //handle the connection protocol to the back
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public Controller(AgentModel model, String propertiesPath){
        this(model,propertiesPath,false); // run the agent in standAlone
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
        try {
        BackEndIO.write(new SerializableCommand("agent",this.properties.get("aircraftName"))); //sending to back the aircraftName
        BackEndIO.setInPutStream(backEnd.getInputStream());
        if(BackEndIO.readCommand().getCommandName().equals("ok"))
            inFromBack(); // starting to get data from backend in a different thread
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    private void inFromBack(){
        Object command = null;
        while ((command = BackEndIO.readCommand())!=null){
            this.exe((SerializableCommand) command); // execute the command from back
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(model)){ //getting data from the model
            String line = (String) arg;
            if(!this.standAlone)
                exe(new SerializableCommand("addRow",line)); // sending the data to back
           else System.out.println(line);
        }

    }
    public void outToBack(SerializableCommand command){
        BackEndIO.write(command);
    }
    public void exe(SerializableCommand command){
        threadPool.execute(()->this.commands.executeCommand(command));
    }

    public void close() {
        TimeSeries ts = model.getTimeSeries();
        model.closeModel();
        System.out.println("closed to model");  //TODO: DELETE!
        statistics.put("sumInMiles"
                ,Statistics.sumDistanceInMiles(ts.getProp(properties.get("longitude")),
                        ts.getProp(properties.get("latitude")))); // clac the sum miles with the sumDistanceInMiles() method from the Statistics calss and adding it into the StatisticsMap
        if (!this.standAlone) {
            // sending to back the sum in miles and closing the socket to back
            this.exe(new SerializableCommand("updateFlight","no "+statistics.get("sumInMiles")));
        }
        threadPool.shutdown();
        System.out.println("closed controller");  //TODO: DELETE!
    }
}
