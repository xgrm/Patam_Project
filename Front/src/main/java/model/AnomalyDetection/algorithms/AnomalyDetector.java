package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.CorrelatedFeatures;
import model.AnomalyDetection.TimeSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AnomalyDetector {

    SimpleAnomalyDetector simpleAnomalyDetector;
    TimeSeriesAnomalyDetector hybrid;
    TimeSeriesAnomalyDetector Zscore;

    HashMap<String,CorrelatedFeatures> correlatedFeatures;

    public AnomalyDetector(String trainingDataPath) {
        TimeSeries ts = new TimeSeries(trainingDataPath);
        this.simpleAnomalyDetector = new SimpleAnomalyDetector();
        this.Zscore = new Zscore();
        this.hybrid = new Hybrid();
        simpleAnomalyDetector.learnNormal(ts);
        this.correlatedFeatures = simpleAnomalyDetector.getCorrelatedFeaturesHashMap();
        this.hybrid.learnNormal(ts);
        this.Zscore.learnNormal(ts);
    }
    public HashMap<String, CorrelatedFeatures> getCorrelatedFeatures(){
        return this.correlatedFeatures;
    }
    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String,Float> data){
        List<AnomalyReport> list = new ArrayList<>(simpleAnomalyDetector.detectFromLine(data));
        list.addAll(hybrid.detectFromLine(data));
        list.addAll(Zscore.detectFromLine(data));
        return list;
    }
}
