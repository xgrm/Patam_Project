package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.CorrelatedFeatures;
import model.AnomalyDetection.StatLib;
import model.AnomalyDetection.TimeSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Zscore implements TimeSeriesAnomalyDetector {

    HashMap<String,Float> tx;
    TimeSeries timeSeries;
    TimeSeries liveTimeSeries;
    String[] features;
    @Override
    public void learnNormal(TimeSeries ts) {
        this.timeSeries = ts;
        this.tx = new HashMap<>();
        String feature1,feature2;
        float[] feature1Val,feature2Val;
        StringBuilder sb = new StringBuilder();
        for (CorrelatedFeatures c : ts.getCorrelatedFeatures()) {
            if (Math.abs(c.threshold) < 0.5 ) {
                feature1 = c.feature1;
                feature2 = c.feature2;
                feature1Val = ts.GetValueByProp(feature1);
                feature2Val = ts.GetValueByProp(feature2);
                this.tx.put(feature1,calculateMaxColumTreshHold(feature1Val,feature1Val.length-1));
                this.tx.put(feature2,calculateMaxColumTreshHold(feature2Val,feature2Val.length-1));
                sb.append(feature1+" ").append(feature2+" ");
            }
        }
        features = sb.toString().trim().split(" ");
        liveTimeSeries = new TimeSeries(features);
    }

    private float calculateMaxColumTreshHold(float[] col, int lastIndex) {
        float th = 0;
        float z;
        for (int i = 0; i <= lastIndex; i++) {
            float var = (float) Math.sqrt(StatLib.var(col,i));
            float avg = StatLib.avg(col,i);
            z = (Math.abs(col[i] - avg) / var);
            if (th < z)
                th = z;
        }
        return th;
    }

    private float calculateXTreshHold(float[] col,int index) {
        float z;
        int size = index-1;
        float var = (float) Math.sqrt(StatLib.var(col,size));
        float avg = StatLib.avg(col,size);
        z = (Math.abs(col[index] - avg) / var);
        return z;
    }
    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        List<AnomalyReport> detected = new ArrayList<>();
        int size = ts.getSize();
        String[] features = ts.getFeatures();
        float z;
        float[] X;
        for (int i = 0; i < size; i++) {
            X = ts.GetValueByProp(features[i]);
            z = calculateXTreshHold(X,X.length-1);
            if(z>tx.get(features[i]))
                detected.add(new AnomalyReport(" ",features[i],features[i],X.length-1));
        }
        return detected;
    }

    @Override
    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String, Float> data) {
        liveTimeSeries.addLine(data);
        return detect(liveTimeSeries);
    }

}
