package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.TimeSeries;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface TimeSeriesAnomalyDetector {
    void learnNormal(TimeSeries ts);
    List<AnomalyReport> detect(TimeSeries ts);

}
