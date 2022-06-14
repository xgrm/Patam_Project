package view;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import view.Charts.RegChart;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TimeCapsuleController extends BaseController{

    @FXML
    ListView featureListTC;

    @FXML
    LineChart changeChart;

    @FXML
    LineChart correlationChart;

    @FXML
    ScatterChart regChart;

    public void  setList(String features){
        String[] str=features.split(" ");
        Set<String> s= new HashSet<>();
        for(String string:str){
            s.add(string);
        }
        featureListTC.getItems().addAll(s);
    }

    @Override
    public void updateUi(Object obj) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList("ggnjn nn bbb cc");
        enterLineChartChange("6 5 8.8 4.5 20 100");
        enterLineChartCorrelation("5 4 4.4 80 100 775");
        enterRegChart("8 100 4 55.5 14 50 3 30.5 16 85");
    }
    public void enterLineChartChange(String data){
        XYChart.Series setLineChart=new XYChart.Series<>();
        String[] str=data.split(" ");
        System.out.println("enter line chart");
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
        System.out.println("enter line chart");
        int i=0;
        for(String s:str){
            setLineChart.getData().add(new XYChart.Data<>("7",Double.parseDouble(str[i])));
            i++;
        }
        correlationChart.getData().add(setLineChart);
    }

    public void enterRegChart(String data) {
        String[] str=data.split(" ");
        System.out.println("enter to reg chart");
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


}
