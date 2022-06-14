package view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class JoystickController extends BaseController implements Observer {


    @FXML
    Canvas joystick;
    @FXML
    Slider rudder;
    @FXML
    Slider throttle;

    double mx,my;
    DoubleProperty aileron,elevator;


    public JoystickController() {

    }

    public void paint(double x, double y){
        GraphicsContext gc = joystick.getGraphicsContext2D();
        gc.clearRect(0,0,joystick.getWidth(),joystick.getHeight());
        gc.strokeOval(x-50,y-50,100,100);
        aileron.setValue((x-mx)/mx);
        elevator.setValue((y-my)/my);
    }

    public void onMouseDragged(MouseEvent me){
        paint(me.getX(), me.getY());
    }
    public void onMouseReleased(MouseEvent me){
        paint(mx,my);
    }

    @Override
    public void updateUi(Object obj) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aileron = new SimpleDoubleProperty(0);
        elevator = new SimpleDoubleProperty(0);
        BaseController.viewModel.addObserver(this);
        BaseController.viewModel.elevator.bind(elevator);
        BaseController.viewModel.aileron.bind(aileron);
        BaseController.viewModel.throttle.bind(throttle.valueProperty());
        BaseController.viewModel.rudder.bind(rudder.valueProperty());
        mx = joystick.getWidth()/2;
        my = joystick.getHeight()/2;
        paint(mx,my);
        elevator.setValue(5);
    }

    @Override
    public void update(Observable o, Object arg) {
        Runnable r = ()-> {
            ConcurrentHashMap<String, Float> symbols = (ConcurrentHashMap<String, Float>) arg;
            this.aileron.setValue(symbols.get("aileron"));
            this.elevator.setValue(symbols.get("elevator"));
            paint(mx * (aileron.doubleValue() + 1), my * (elevator.doubleValue() + 1));
            throttle.setValue(symbols.get("throttle"));
            rudder.setValue(symbols.get("rudder"));
        };
        Platform.runLater(r);
    }
}
