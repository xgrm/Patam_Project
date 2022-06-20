package view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import viewModel.ViewModel;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JoystickController extends BaseController implements Observer {


    @FXML
    Canvas joystick;
    @FXML
    Slider rudder;
    @FXML
    Slider throttle;
    @FXML
    ChoiceBox<String> agents;

    double mx,my;
    DoubleProperty aileron,elevator;
    DecimalFormat df;
    Node root;
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
    public void init(ViewModel vm, Node root) throws Exception {
        this.root = root;
        viewModel = vm;
        aileron = new SimpleDoubleProperty(0);
        elevator = new SimpleDoubleProperty(0);
        mx = joystick.getWidth()/2;
        my = joystick.getHeight()/2;
        paint(mx,my);
        agents.setOnMouseClicked((e)->viewModel.exe(new SerializableCommand("getActiveAgents"," ")));
        agents.setOnAction((e)->setBindAgent());
        this.df = new DecimalFormat("#.##");

    }

    public void setJoystickDisable(boolean disable){
        root.setDisable(disable);
    }
    @Override
    public void onTabSelection() {
        super.onTabSelection();
        viewModel.elevator.bind(elevator);
        viewModel.aileron.bind(aileron);
        viewModel.throttle.bind(throttle.valueProperty());
        viewModel.rudder.bind(rudder.valueProperty());
    }

    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern() == "activeAgents"){
            String[] tokens = command.getData().split(",");
            Platform.runLater(()->{
                agents.getItems().clear();
                agents.getItems().addAll(tokens);
            });
        }
        else if(command.getCommandName().intern() == "agentData"){
                ConcurrentHashMap<String,Float> symbolMap = new ConcurrentHashMap<>(command.getDataMap());
                this.throttle.setValue(Float.parseFloat(df.format(symbolMap.get("throttle"))));
                this.aileron.setValue(Float.parseFloat(df.format(symbolMap.get("aileron"))));
                this.elevator.setValue(Float.parseFloat(df.format(symbolMap.get("elevator"))));
                this.rudder.setValue(Float.parseFloat(df.format(symbolMap.get("rudder"))));
        }
    }

    private void setBindAgent( ){
        if((agents.getValue()!=null)&&agents.getValue().intern()!="none"){
            SerializableCommand command = new SerializableCommand("setAgentBind"," ");
            command.setId(Integer.parseInt(agents.getValue()));
            viewModel.exe(command);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
