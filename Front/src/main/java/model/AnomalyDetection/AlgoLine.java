package model.AnomalyDetection;


import java.util.List;

public class AlgoLine implements Algo{

    private TimeSeries ts;

    private  void findCorrelatedFeatures(){}
    @Override
    public void learnNormal(TimeSeries ts) {    }
    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {return null;}
}
