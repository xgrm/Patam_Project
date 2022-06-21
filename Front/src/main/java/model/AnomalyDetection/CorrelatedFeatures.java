package model.AnomalyDetection;

import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Line;

public class CorrelatedFeatures {
    public final String feature1,feature2;
    public final float correlation;
    public final Line lin_reg;
    public final float threshold;
    public  Circle circle;

    public CorrelatedFeatures(String feature1, String feature2, float correlation, Line lin_reg, float threshold) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.correlation = correlation;
        this.lin_reg = lin_reg;
        this.threshold = threshold;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
