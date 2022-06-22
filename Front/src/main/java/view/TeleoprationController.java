package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class TeleoprationController extends BaseController {

    @FXML
    AnchorPane TextFile,Joystick,ClockBoard;

    Timer timer;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        addPane(TextFile, "TextFile.fxml", 0,0,1,1,"textFile");
        addPane(Joystick, "Joystick.fxml", 75,1,1,1,"movingJoystick");
        addPane(ClockBoard, "ClockBoard.fxml", 103,73,2,2,"clock");

    }

    @Override
    public void updateUi(Object obj) {
        this.controllers.forEach((key,value)->value.updateUi(obj));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onTabSelection() {
        this.controllers.forEach((key,value)->value.onTabSelection());
        Platform.runLater(()->{
            this.viewModel.getStage().setMaxWidth(850);
            this.viewModel.getStage().setMaxHeight(650);
            this.viewModel.getStage().setMinWidth(850);
            this.viewModel.getStage().setMinHeight(650);
        });
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
    }
}
