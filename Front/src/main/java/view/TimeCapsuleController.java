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
//        addPane(RegChart, "Charts/RegChart.fxml",0,0,1,1, "regChart");
//        addPane(changeLineChart, "Charts/LineChart.fxml",0,0,1,1, "changeChart");
//        addPane(correlationLineChart, "Charts/LineChart.fxml",0,0,1,1, "correlationChart");
        addPane(JoystickClockBoard, "ClockBoard.fxml",43,256, 1,1,"clockBoardTC");
        addPane(JoystickClockBoard, "Joystick.fxml",29,2, 1,1,"staticJoystick");
        addPane(PlayBoard, "PlayBoard.fxml",0,0, 1,1,"playBoard");


    }

    @Override
    public void updateUi(Object obj) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setList("ggnjn nn bbb cc");
//        enterLineChartChange("6 5 8.8 4.5 20 100");
//        enterLineChartCorrelation("5 4 4.4 80 100 775");
//        enterRegChart("8 100 4 55.5 14 50 3 30.5 16 85");
    }
    public void enterLineChartChange(String data){
        XYChart.Series setLineChart=new XYChart.Series<>();
        String[] str=data.split(" ");
        int i=0;
        for(String s:str){
            setLineChart.getData().add(new XYChart.Data<>("7",Double.parseDouble(str[i])));
            i++;
        }
        changeChart.getData().add(setLineChart);
    }
    public void enterLineChartCorrelation(String data){
        XYChart.Series setLineChart=new XYChart.Series<>();
        String[] str=data.split(" ");
        int i=0;
        for(String s:str){
            setLineChart.getData().add(new XYChart.Data<>("7",Double.parseDouble(str[i])));
            i++;
        }
        correlationChart.getData().add(setLineChart);
    }

    public void enterRegChart(String data) {
        String[] str=data.split(" ");
        XYChart.Series series=new XYChart.Series();
        for(int i=0; i< str.length; i+=2){
            series.getData().add(new XYChart.Data(str[i],Double.parseDouble((str[i+1]))));
        }

        regChart.getData().add(series);

        XYChart.Series series2=new XYChart.Series();
        for(int i=0; i< str.length; i+=2){
            series2.getData().add(new XYChart.Data(str[i],Double.parseDouble((str[i+1]))+8));

        }

        regChart.getData().add(series2);
    }
    public void  setList(String features){
        String[] str=features.split(" ");
        Set<String> s= new HashSet<>();
        for(String string:str){
            s.add(string);
        }
        featureListTC.getItems().addAll(s);
    }

    @Override
    public void onTabSelection() {
        this.controllers.forEach((key,value)->value.onTabSelection());
    }
}
