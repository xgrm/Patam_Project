package model;



import model.AnomalyDetection.*;
import model.AnomalyDetection.algorithms.SimpleAnomalyDetector;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MainModel implements Model{
    SimpleAnomalyDetector simpleAnomalyDetector;
    HashMap<String,CorrelatedFeatures> correlatedFeatures;

    public MainModel() {
        this.simpleAnomalyDetector = new SimpleAnomalyDetector();
        TimeSeries ts = new TimeSeries("src/main/java/model/utils/FlightData.csv");
        simpleAnomalyDetector.learnNormal(ts);
        this.correlatedFeatures = simpleAnomalyDetector.getCorrelatedFeaturesHashMap();
    }

    public HashMap<String,CorrelatedFeatures> getCorrelatedFeatures(){
        return this.correlatedFeatures;
    }
    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String,Float> data){
        return simpleAnomalyDetector.detectFromLine(data);
    }
}
