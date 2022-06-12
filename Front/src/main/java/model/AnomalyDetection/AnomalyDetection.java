package model.AnomalyDetection;

import java.util.ArrayList;
import java.util.List;

public class AnomalyDetection {
    List<CorrelationFeatures> listCorrelation;
    List<AnomalyReport> listException;
    Float[][] floats;
    float  correlation;
    Algo algo;

    private Float[][] convertToFloat(ArrayList<Float> arr){ return floats;} // not true- just to write the class
    public Point[] getPointsArray(Float[] x, Float[] y){ Point[] p = new Point[0] ; return p; } // not true- just to write the class
    public float maxTH(Line l, Point[] points){ return correlation;} // not true- just to write the class
    public List<CorrelationFeatures> getNormalModel(){ List<CorrelationFeatures> l= new ArrayList<>(); return l;} // not true- just to write the class
    public float getCorrelation(){return this.correlation;}
    public void setCorrelation(float correlation){this.correlation=correlation;}
    public List<AnomalyReport> getListException(){return this.listException;}

    /* function in algo interface - I dont know if we need them here because we have Algo algo*/
    public void learnNormal(TimeSeries ts){}
    public List<AnomalyReport> detect(TimeSeries ts){return listException;}
    //  we can do algo.detect and it will be the same
}
