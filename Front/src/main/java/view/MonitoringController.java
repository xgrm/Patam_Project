package view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static javafx.geometry.Orientation.VERTICAL;

public class MonitoringController extends BaseController {
    @FXML
    ListView listF;

    @FXML
    LineChart changeChart;

    @FXML
    LineChart correlationChart;

    @FXML
    ScatterChart scatterc;

    @FXML
    AnchorPane FeatureList,changeLineChart,correlationLineChart,joystick,clockBoard;

    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        viewModel = vm;
        addPane(FeatureList,"FeatureList.fxml",0,0,"listF");
        addPane(changeLineChart,"Charts/LineChart.fxml",0,0,"changeChart");
        addPane(correlationLineChart,"Charts/LineChart.fxml",0,0,"correlationChart");
        addPane(joystick,"Joystick.fxml",90,70,"correlationChart");
        addPane(clockBoard,"ClockBoard.fxml",-200,-200,"correlationChart");
    }

    @Override
    public void updateUi(Object obj) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setList("jdfbnhkjd ldjfvdlfjb kdfjlbldkfjbidl maya noy");
//        enterLineChartChange("6 5 8.8 4.5 20 100");
//        enterLineChartCorrelation("5 4 4.4 80 100 775");
//        enterRegChart("8 100 4 55.5 14 50 3 30.5 16 85");

    }
    public void  setList(String features){
        String[] str=features.split(" ");
        Set<String> s= new HashSet<>();
        for(String string:str){
            s.add(string);
        }

        listF.getItems().addAll(s);
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

        scatterc.getData().add(series);
    }

}