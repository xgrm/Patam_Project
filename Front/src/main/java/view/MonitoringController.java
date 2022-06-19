package view;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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
    AnchorPane FeatureList,changeLineChart,correlationLineChart,joystick,clockBoard,scatterc;

    Timer timer;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        viewModel = vm;
        addPane(FeatureList,"FeatureList.fxml",0,0,0,0,"listF");
        addPane(changeLineChart,"Charts/LineChart.fxml",1,35,1,1,"changeChart");
        addPane(correlationLineChart,"Charts/LineChart.fxml",1,31,1,1,"correlationChart");
        addPane(scatterc,"Charts/RegChart.fxml",22,4,1,1,"correlationChart");
        addPane(joystick,"Joystick.fxml",0,0,0,0,"joystick");
        addPane(clockBoard,"ClockBoard.fxml",102,71,2,2,"clockBoard");
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
//        String[] str=data.split(" ");
//        XYChart.Series series=new XYChart.Series();
//        for(int i=0; i< str.length; i+=2){
//            series.getData().add(new XYChart.Data(str[i],Double.parseDouble((str[i+1]))));
//        }
//
//        scatterc.getData().add(series);
    }

    @Override
    public void onTabSelection() {
        this.controllers.forEach((key,value)->value.onTabSelection());
        this.viewModel.exe(new SerializableCommand("getFeaturesList"," "));
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
    }
}