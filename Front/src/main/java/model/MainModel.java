package model;




import model.AnomalyDetection.*;
import model.AnomalyDetection.algorithms.AnomalyDetector;
import model.Player.Play;
import view.SerializableCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MainModel extends Observable implements Model, Observer {
    AnomalyDetector anomalyDetector;
    Play play;
    HashMap<String,String> propMap;

    public MainModel(String path) {
        this.propMap = new HashMap<>();
        createPropMap(path);
        anomalyDetector = new AnomalyDetector(propMap.get("trainingData"));
        play = new Play(propMap.get("localFG"));
        this.play.addObserver(this);
    }
    private void createPropMap(String path){
        try {
            Scanner scanner = new Scanner(new File(path));
            String[] tokens;
            while (scanner.hasNext()){
                tokens = scanner.nextLine().split(",");
                this.propMap.put(tokens[0],tokens[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    public HashMap<String,CorrelatedFeatures> getCorrelatedFeatures(){
        return this.anomalyDetector.getCorrelatedFeatures();
    }
    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String,Float> data){
        return anomalyDetector.detectFromLine(data);
    }
    public void play(){
        play.play();
    }
    public void stop(){
        play.stop();
    }
    public void setTimeStep(int timeStep) {
        play.setTimeStep(timeStep);
    }
    public void setPlayerPath(String path){
        int timeStepsSize = play.setPath(path);
        setChanged();
        notifyObservers(new SerializableCommand("timeStepsSize",String.valueOf(timeStepsSize)));
    }
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
