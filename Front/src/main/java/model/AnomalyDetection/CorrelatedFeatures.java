package model.AnomalyDetection;

import javafx.scene.chart.XYChart;
import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Line;

public class CorrelatedFeatures {
    public final String feature1,feature2;
    public final float correlation;
    public final Line lin_reg;
    public final float threshold;
    public  Circle circle;

    final XYChart.Series<Float,Float> points;

    public CorrelatedFeatures(String feature1, String feature2, float correlation, Line lin_reg, float threshold) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.correlation = correlation;
        this.lin_reg = lin_reg;
        this.threshold = threshold;
        this.points= new XYChart.Series<>();
        if(correlation>=0.95)
            setRegPoint();
    }

    public Circle getCircle() {
        return circle;
    }
    private void setRegPoint(){
        for (int i = 0; i < 50; i++) {
            points.getData().add(new XYChart.Data(i,lin_reg.f(i)));
        }
    }
    public void setCircle(Circle circle) {
        this.circle = circle;
        float jump = 0.2f;
        if(circle.radius>500)
            jump = (int) (circle.radius%100)+10;

        for (float i =  (circle.center.x-circle.radius); i < circle.center.x+circle.radius; i+=jump) {
            Float[] ys = circle.getY(i);
            if(ys[1]==null)
                continue;
            points.getData().add(new XYChart.Data(i,ys[0]));

        }
        for (float i =  circle.center.x+circle.radius; i > circle.center.x-circle.radius; i-=jump) {
            Float[] ys = circle.getY(i);
            if(ys[1]==null)
                continue;
            points.getData().add(new XYChart.Data(i,ys[1]));
        }
    }

    public XYChart.Series<Float, Float> getPoints() {
        return points;
    }
}
