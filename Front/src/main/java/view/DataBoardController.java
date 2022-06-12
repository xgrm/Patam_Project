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


    @Override
    public void updateUi(Object obj) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series setL=new XYChart.Series<>();
        setL.getData().add(new XYChart.Data<>("1",13));
        setL.getData().add(new XYChart.Data<>("2",43));
        setL.getData().add(new XYChart.Data<>("3",63));
        nauticalMilesAvg.getData().addAll(setL);

        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
                new PieChart.Data("available", 39),
                new PieChart.Data("not available",61));
        activeFlight.setData(pieChartData);
        XYChart.Series setLineChart=new XYChart.Series<>();
        setLineChart.getData().add(new XYChart.Data<>("1",133));
        setLineChart.getData().add(new XYChart.Data<>("2",98));

        fleetSize.getData().add(setLineChart);

    }
}
