package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DataBoardController extends BaseController{
    @FXML
    PieChart activeFlight;
    @FXML
    BarChart nauticalMiles;
    @FXML
    BarChart nauticalMilesAvg;
    @FXML
    LineChart fleetSize;
    @FXML
    Button refreshBut;

    String[] month= {"Jan","Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    @Override
    public void updateUi(Object obj) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Bar
        enterBarNauticalMiles("1 30 2 49 hghgh 58");
        enterBarNauticalMilesAvg("3.4 5 6.7 8 9 10 40 100 39");
//        XYChart.Series setL=new XYChart.Series<>();
//        setL.getData().add(new XYChart.Data<>("1",13));
//        setL.getData().add(new XYChart.Data<>("2",43));
//        setL.getData().add(new XYChart.Data<>("3",63));
//        nauticalMilesAvg.getData().addAll(setL);

        //Pie Test
        String number="50 23.5";
        enterPieChart(number);

        //Line Test
        enterLineChart("50 22.5 60 75.6");
    }

    public void enterPieChart(String data){
        String[] str=data.split(" ");
        System.out.println("enter pie chart");
        ObservableList<PieChart.Data> pieChartData=FXCollections.observableArrayList(
                new PieChart.Data("active", Double.parseDouble(str[0])),
                new PieChart.Data("not active", Double.parseDouble(str[1])));
        activeFlight.setData(pieChartData);

    }

    public void enterLineChart(String data){
        XYChart.Series setLineChart=new XYChart.Series<>();
        String[] str=data.split(" ");
        System.out.println("enter line chart");
        int i=0;
        for(String s:str){
            setLineChart.getData().add(new XYChart.Data<>(month[i],Double.parseDouble(str[i])));
            i++;
        }
        fleetSize.getData().add(setLineChart);
    }

    public void enterBarNauticalMiles(String data){
        String[] str=data.split(" ");
        System.out.println("enter Bar chart");
        XYChart.Series setL=new XYChart.Series<>();
        for(int i=0; i< str.length; i+=2){
            setL.getData().add(new XYChart.Data<>(str[i],Double.parseDouble((str[i+1]))));
        }
        nauticalMiles.getData().addAll(setL);
    }

    public void enterBarNauticalMilesAvg(String data){
        String[] str=data.split(" ");
        System.out.println("enter Bar chart");
        XYChart.Series setL=new XYChart.Series<>();
        for(int i=0; i < str.length; i++){
            setL.getData().add(new XYChart.Data<>(month[i],Double.parseDouble((str[i]))));
        }
        nauticalMilesAvg.getData().addAll(setL);
    }
    public void clear(){
        fleetSize.getData().clear();
        nauticalMilesAvg.getData().clear();
        nauticalMiles.getData().clear();
        activeFlight.getData().clear();
    }
    public  void refresh(){
        // TODO: get value from server
        clear();
        enterBarNauticalMilesAvg("100.4 5 6.7 8 9 10 40 100 39");
        enterBarNauticalMiles("djgv 5 fjn 7 jjgb 8");
        enterPieChart("30.5 90");
        enterLineChart("10 80 50 10.2 23.5 100 20.6 88 20 55.5 66");
    }
}
