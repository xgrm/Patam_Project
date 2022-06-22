package view;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import view.Charts.Chart;
import viewModel.ViewModel;
import java.net.URL;
import java.util.*;


public class MonitoringController extends BaseController {
    @FXML
    ListView listF;

    @FXML
    LineChart changeChart;

    @FXML
    LineChart correlationChart;

    @FXML
    AnchorPane FeatureList,joystick,clockBoard,scatterc;

    Timer timer;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        viewModel = vm;
        addPane(FeatureList,"FeatureList.fxml",0,0,0,0,"listF");
        addPane(joystick,"Joystick.fxml",0,0,0,0,"joystick");
        addPane(clockBoard,"ClockBoard.fxml",101,83,2,2,"clockBoard");
        JoystickController js = (JoystickController) controllers.get("JoystickController");
        js.setJoystickDisable(true);
    }

    @Override
    public void updateUi(Object obj) {
        this.controllers.forEach((key,value)->value.updateUi(obj));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setList("jdfbnhkjd ldjfvdlfjb kdfjlbldkfjbidl maya noy");
//        enterLineChartChange("6 5 8.8 4.5 20 100");
//        enterLineChartCorrelation("5 4 4.4 80 100 775");
//        enterRegChart("8 100 4 55.5 14 50 3 30.5 16 85");

    }


    @Override
    public void onTabSelection() {
        this.controllers.forEach((key,value)->value.onTabSelection());
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
    }
}