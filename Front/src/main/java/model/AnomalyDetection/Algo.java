package model.AnomalyDetection;

import java.util.List;

public interface Algo {
    public void learnNormal(TimeSeries ts);
    public List<AnomalyReport> detect(TimeSeries ts);
}
