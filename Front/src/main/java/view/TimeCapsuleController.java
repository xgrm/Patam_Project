package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;
import java.net.URL;
import java.util.ResourceBundle;

public class TimeCapsuleController extends BaseController {

    @FXML
    AnchorPane FeatureList,Joystick,ClockBoard,PlayBoard;

    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        addPane(FeatureList, "FeatureList.fxml",13,1,1,1, "featureListTC");
        addPane(ClockBoard, "ClockBoard.fxml",125,91, 2,2,"clockBoardTC");
        addPane(Joystick, "Joystick.fxml",29,2, 1,1,"staticJoystick");
        addPane(PlayBoard, "PlayBoard.fxml",0,0, 1,1,"playBoard");
        JoystickController js = (JoystickController) controllers.get("JoystickController");
        js.setJoystickDisable(true);
    }

    @Override
    public void updateUi(Object obj) {
        this.controllers.forEach((key,value)->value.updateUi(obj));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setList("ggnjn nn bbb cc");
//        enterLineChartChange("6 5 8.8 4.5 20 100");
//        enterLineChartCorrelation("5 4 4.4 80 100 775");
//        enterRegChart("8 100 4 55.5 14 50 3 30.5 16 85");
    }

    @Override
    public void onTabSelection() {
        this.controllers.forEach((key,value)->value.onTabSelection());
        Platform.runLater(()->{
            this.viewModel.getStage().setMaxWidth(1155);
            this.viewModel.getStage().setMaxHeight(740);
            this.viewModel.getStage().setMinWidth(1155);
            this.viewModel.getStage().setMinHeight(740);
        });
    }
}
