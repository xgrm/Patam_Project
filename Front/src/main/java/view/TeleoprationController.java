package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import view.Charts.TabController;
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
        addPane(Joystick, "Joystick.fxml", 155,1,1,1,"movingJoystick");
        addPane(ClockBoard, "ClockBoard.fxml", 189,87,2,2,"clock");

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
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                viewModel.exe("getData~ ");
            }
        }, 0, 100);
        this.controllers.forEach((key,value)->value.onTabSelection());
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
        timer.cancel();
    }
}
