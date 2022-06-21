package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.Charts.RegChart;
import view.Charts.TabController;
import viewModel.ViewModel;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TimeCapsuleController extends BaseController {

    @FXML
    ListView featureListTC;

    @FXML
    LineChart changeChart;

    @FXML
    LineChart correlationChart;

    @FXML
    ScatterChart regChart;

    @FXML
    AnchorPane FeatureList,RegChart,changeLineChart,correlationLineChart,JoystickClockBoard,PlayBoard;



    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        addPane(FeatureList, "FeatureList.fxml",13,1,1,1, "featureListTC");
        addPane(JoystickClockBoard, "ClockBoard.fxml",43,256, 1,1,"clockBoardTC");
        addPane(JoystickClockBoard, "Joystick.fxml",29,2, 1,1,"staticJoystick");
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
    }
}
