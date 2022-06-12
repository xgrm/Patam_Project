package model.AnomalyDetection;

public class AnomalyReport {
    String description;
    long timeStep;

    public String getDescription() {return description;}
    public long getTimeStep() {return timeStep;}
    public void setDescription(String description) {this.description = description;}
    public void setTimeStep(long timeStep) {this.timeStep = timeStep;}
}
