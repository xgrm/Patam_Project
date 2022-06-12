package model.AnomalyDetection;

public class CorrelationFeatures {

    String feature1, feature2;
    float corrlation, threshold;
    Line lin_reg;
// get
    public String getFeature1() {return feature1;}
    public String getFeature2() {return feature2;}
    public float getCorrlation() {return corrlation;}
    public float getThreshold() {return threshold;}
    public Line getLin_reg() {return lin_reg;}
// set
    public void setFeature1(String feature1) {this.feature1 = feature1;}
    public void setFeature2(String feature2) {this.feature2 = feature2;}
    public void setCorrlation(float corrlation) {this.corrlation = corrlation;}
    public void setThreshold(float threshold) {this.threshold = threshold;}
    public void setLin_reg(Line lin_reg) {this.lin_reg = lin_reg;}
}
