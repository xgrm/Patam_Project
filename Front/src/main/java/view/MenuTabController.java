package view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import viewModel.ViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MenuTabController implements Initializable, Observer {

    @FXML
    SplitPane monitoring;
    TabPane t;
    HashMap<String,BaseController> controllers;
    public MenuTabController() {
        this.controllers = new HashMap<>();
    }
    public void init(ViewModel vm){
        BaseController.setViewModel(vm);
    }

    private void putController(String name, BaseController controller){
        this.controllers.put(name,controller);
    }
    @Override
    public void update(Observable o, Object arg) {

    }
    public void d(Object arg){
        t = (TabPane) arg;
        Platform.runLater(()->{
            t.getTabs().add(new Tab("s"));
        });
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Joystick.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("err");
        }
        JoystickController j = fxmlLoader.getController();
        j.updateUi(this);
//        putController("Joystick",fxmlLoader.getController());
//        controllers.get("Joystick").updateUi(this);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}