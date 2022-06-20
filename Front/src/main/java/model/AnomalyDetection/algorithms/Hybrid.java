package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.TimeSeries;

import java.util.List;
import java.util.Random;

public class Hybrid implements TimeSeriesAnomalyDetector{

    private Random rand = new Random();
    @Override
    public void learnNormal(TimeSeries ts) {

    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        return null;
    }
}
