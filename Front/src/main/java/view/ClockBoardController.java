package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import eu.hansolo.medusa.*;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class ClockBoardController extends BaseController {
    private static double BORDER_RADIUS;
    private static double THUMBSTICK_RADIUS;

    @FXML
    public Circle border;
    @FXML public Circle thumbStick;
    @FXML public Slider rudder;
    @FXML public Slider throttle;
    @FXML public Gauge height;
    @FXML public Gauge degrees;
    @FXML public Gauge roll;
    @FXML public Gauge pitch;
    @FXML public Gauge yaw;
    @FXML public Gauge airspeed;





    public DoubleProperty aileron = new SimpleDoubleProperty();
    public DoubleProperty elevator = new SimpleDoubleProperty();

    public void setThumbStick() {

        BORDER_RADIUS = border.radiusProperty().doubleValue();
        THUMBSTICK_RADIUS = thumbStick.radiusProperty().doubleValue();

        aileron.addListener(((observable, oldValue, newValue) -> thumbStick.setCenterX(aileron.get() * THUMBSTICK_RADIUS)));
        elevator.addListener((observable -> thumbStick.setCenterY(elevator.get() * THUMBSTICK_RADIUS)));

    }
    public void setClocks() {
        RadialGradient gradient1 = new RadialGradient(0,
                .1,
                10,
                10,
                7,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(1.5, Color.GRAY));

        airspeed.setBackgroundPaint(gradient1);
        height.setBackgroundPaint(gradient1);
        degrees.setBackgroundPaint(gradient1);
        pitch.setBackgroundPaint(gradient1);
        yaw.setBackgroundPaint(gradient1);
        roll.setBackgroundPaint(gradient1);


//
//        String[] feature= settings.keySet().toArray(new String[0]);
//        String featureName;
//        String line;
//        float temp;
//        int min,max;
//
//
//        for (int i=0;i<settings.size();i++) {
//            featureName=feature[i];
//            line=settings.get(featureName);
//            String []split=line.split(":");
//            temp= Float.parseFloat(split[0]);
//            min= (int) (temp);
//            if(temp<0)
//                min--;
//            temp= Float.parseFloat(split[1]);
//            max=(int) (temp);
//            setClock(featureName,min,max);
//        }
    }

    private void setClock(String featureName, double value) {
       /* Height,-13.62:699.26
Speed,0:93.41
Degrees,0:357.95
Yaw,-28.06:89.87
Pitch,-9.503:16.65
Roll,-37.44:40

        */
        switch (featureName) {
            case "Height": {
                height.setTitleColor(Color.WHITE);
                height.setBorderPaint(Color.HONEYDEW);
                height.setForegroundBaseColor(Color.WHITE);
                height.setValue(value);
                height.setAnimated(true);
                height.setAnimationDuration(500);
                break;
            }
            case "Speed": {
                airspeed.setCustomTickLabels("0","","20","","40","","60","","80","100");
                airspeed.setCustomTickLabelFontSize(20);
                airspeed.setCustomTickLabelsEnabled(true);
                airspeed.setTitleColor(Color.WHITE);
                airspeed.setBorderPaint(Color.HONEYDEW);
                airspeed.setForegroundBaseColor(Color.WHITE);
                airspeed.setValue(value);
                airspeed.setAnimated(true);
                airspeed.setAnimationDuration(500);
                break;
            }
            case "Degrees": {
                degrees.setStartAngle(180);
                degrees.setAngleRange(350);
                degrees.setCustomTickLabels("N","","","","","","","","","E","","","","","","","","","S","","","","","","","","","W","","","","","","","","");
                degrees.setCustomTickLabelFontSize(20);
                degrees.setCustomTickLabelsEnabled(true);
                degrees.setTitleColor(Color.WHITE);
                degrees.setBorderPaint(Color.HONEYDEW);
                degrees.setForegroundBaseColor(Color.WHITE);
                degrees.setValue(value);
                degrees.setAnimated(true);
                degrees.setAnimationDuration(500);
                break;
            }
            case "Yaw": {
                yaw.setCustomTickLabels("-30","","0","","","30","","60","","90","");
                yaw.setCustomTickLabelFontSize(20);
                yaw.setCustomTickLabelsEnabled(true);
                yaw.setTitleColor(Color.WHITE);
                yaw.setBorderPaint(Color.HONEYDEW);
                yaw.setForegroundBaseColor(Color.WHITE);
                yaw.setValue(value);
                yaw.setAnimated(true);
                yaw.setAnimationDuration(500);
                break;
            }
            case "Pitch": {
                pitch.setCustomTickLabels("-10","0","10","20");
                pitch.setCustomTickLabelFontSize(20);
                pitch.setCustomTickLabelsEnabled(true);
                pitch.setTitleColor(Color.WHITE);
                pitch.setBorderPaint(Color.HONEYDEW);
                pitch.setForegroundBaseColor(Color.WHITE);
                pitch.setValue(value);
                pitch.setAnimated(true);
                pitch.setAnimationDuration(500);
                break;
            }
            case "Roll": {
                roll.setTitleColor(Color.WHITE);
                roll.setBorderPaint(Color.HONEYDEW);
                roll.setForegroundBaseColor(Color.WHITE);
                roll.setValue(value);
                roll.setAnimated(true);
                roll.setAnimationDuration(500);
                break;
            }


        }
    }

    @Override
    public void updateUi(Object obj) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setClocks();
        setClock("Roll", 20);
        setClock("Pitch", 50);
        setClock("Yaw", 30);
        setClock("Degrees", 40);
        setClock("Speed", 20);
        setClock("Height", 100);


    }
}
