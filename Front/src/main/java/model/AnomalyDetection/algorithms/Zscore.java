package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.StatLib;
import model.AnomalyDetection.TimeSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Zscore implements TimeSeriesAnomalyDetector {

    ArrayList<Float> learningFeatures;
    @Override
    public void learnNormal(TimeSeries ts) {
        learningFeatures = new ArrayList<>();
        int size = ts.getSize();
        String[] features = ts.getFeatures();
        for (int i = 0; i < size; i++) {
            learningFeatures.add(calculateMaxColumTreshHold(ts.GetValueByProp(features[i]),features[i].length()-1));
        }
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
        float th = 0;
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
            if(z>learningFeatures.get(i))
                detected.add(new AnomalyReport(" ",features[i],features[i],X.length-1));
        }
        return detected;
    }

}
