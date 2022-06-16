package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import view.Charts.TabController;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TeleoprationController extends BaseController implements TabController {

    @FXML
    AnchorPane TextFile,Joystick,ClockBoard;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        addPane(TextFile, "TextFile.fxml", 0,0,"textFile");
        addPane(Joystick, "Joystick.fxml", 170,60,"movingJoystick");
        addPane(ClockBoard, "ClockBoard.fxml", -100,-200,"clock");
    }

    @Override
    public void updateUi(Object obj) {
        this.controllers.get("JoystickController").updateUi(obj);
        this.controllers.get("ClockBoardController").updateUi(obj);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onTabSelection() {

    }
}
