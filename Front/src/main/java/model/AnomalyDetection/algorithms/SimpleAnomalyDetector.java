package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.*;
import model.AnomalyDetection.utils.Line;
import model.AnomalyDetection.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {

    private List<CorrelatedFeatures> correlatedFeatures;
    private HashMap<String,CorrelatedFeatures> correlatedFeaturesHashMap;
    private float threshold;

    public SimpleAnomalyDetector() {
        this.correlatedFeatures =new ArrayList<>();
        this.correlatedFeaturesHashMap = new HashMap<>();
        this.threshold = 0.8F;
    }

    @Override
    public void learnNormal(TimeSeries ts) {
        for (int i = 0; i< ts.getSize();i++){
            String feature1 = ts.getFeatures()[i];
            String feature2 = null;
            float person = 0;
            float tempPerson;
            for (int j = i+1; j< ts.getSize();j++){
                tempPerson = Math.abs(StatLib.pearson(ts.GetValueByProp(ts.getFeatures()[i]),ts.GetValueByProp(ts.getFeatures()[j])));
                if(person < tempPerson) {
                    person = tempPerson;
                    feature2 = ts.getFeatures()[j];
                }
            }
            if(feature2!=null && person>threshold)
                AddCorrelatedFeatures(ts,feature1,feature2,person);
        }
    }

    public void AddCorrelatedFeatures(TimeSeries ts, String feature1, String feature2,float person){
        float threshold =0;
        float[] arr1 = ts.GetValueByProp(feature1);
        float[] arr2 = ts.GetValueByProp(feature2);
        Point[] points = StatLib.CreatePointArr(arr1,arr2);
        Line lin_reg = StatLib.linear_reg(points);
        for (Point p : points){
            float dev = StatLib.dev(p,lin_reg);
            if(threshold<dev)
                threshold = dev;
        }
        threshold*=1.1;
        CorrelatedFeatures cf = new CorrelatedFeatures(feature1,feature2,person,lin_reg,threshold);
        this.correlatedFeaturesHashMap.put(feature1,cf);
        this.correlatedFeaturesHashMap.put(feature2,cf);
        this.correlatedFeatures.add(cf);
    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        List<AnomalyReport> lst = new LinkedList<>();
        for(CorrelatedFeatures corFeat : correlatedFeatures){
            String feature1 = corFeat.feature1;
            String feature2 = corFeat.feature2;
            Line lin_reg = corFeat.lin_reg;
            float threshold = corFeat.threshold;
            Point[] points = StatLib.CreatePointArr(ts.GetValueByProp(feature1),ts.GetValueByProp(feature2));
            int i =1;
            for (Point p : points) {
                if(StatLib.dev(p,lin_reg)>threshold){
                    lst.add(new AnomalyReport(feature1+"-"+feature2,feature1,feature2,i));
                }
                i++;
            }
        }
        return  lst;
    }

    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String,Float> data) {

        List<AnomalyReport> lst = new LinkedList<>();
        for(CorrelatedFeatures corFeat : correlatedFeatures){
            String feature1 = corFeat.feature1;
            String feature2 = corFeat.feature2;
            Line lin_reg = corFeat.lin_reg;
            float threshold = corFeat.threshold;
            if(StatLib.dev(new Point(data.get(feature1),data.get(feature2)),lin_reg)>threshold){
                    lst.add(new AnomalyReport(feature1+"-"+feature2,feature1,feature2,1));
            }
        }
        return  lst;
    }

    public List<CorrelatedFeatures> getNormalModel(){
        return correlatedFeatures;
    }

    public HashMap<String, CorrelatedFeatures> getCorrelatedFeaturesHashMap() {
        return correlatedFeaturesHashMap;
    }

}
