package model.AnomalyDetection;

public class AnomalyReport {
    public final String description;
    public final String feature1;
    public final String feature2;
    public final  long timeStep;
    public AnomalyReport(String description, String feature1,String feature2,long timeStep){
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.description=description;
        this.timeStep=timeStep;
    }
}
