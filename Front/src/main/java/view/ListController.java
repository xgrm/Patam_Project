package view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.AnomalyDetection.CorrelatedFeatures;
import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Line;
import viewModel.ViewModel;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class ListController extends BaseController {
    @FXML
    ListView list;
    @FXML
    LineChart regLine1,regLine2,visRegLine;
    @FXML
    NumberAxis xAxis,yAxis;
    @FXML
    Label anomalyLabel;
    @FXML
    Button ignore;
    String feature1,feature2;
    Line regLine;
    Circle circle;
    float f1,f2;
    long lastUpdate,updateRate;

    XYChart.Series<Float,Float> pointForReg1,pointForReg2,pointForVisRegLine,livePointForVisRegLine;

    HashMap<String, CorrelatedFeatures> correlatedFeatures;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        this.updateRate = viewModel.getUpdateRate()*1000000;
    }

    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern()=="agentData"){
            setFeaturesData(command.getDataMap());
        } else if (command.getCommandName().intern()=="anomalyDetectet") {
            anomalyDetected(command.getData());
        } else if (command.getCommandName().intern()=="FeaturesList") {
            addFeaturesToList(command.getData().split(" "));
        } else if (command.getCommandName().intern()=="correlatedFeatures") {
            this.correlatedFeatures = (HashMap<String, CorrelatedFeatures>) command.getObject();
        }
    }

    @Override
    public void onTabSelection() {
        super.onTabSelection();
        lastUpdate = System.nanoTime();
        viewModel.exe(new SerializableCommand("getCorrelatedFeatures"," "));
        this.viewModel.exe(new SerializableCommand("getFeaturesList"," "));
        Platform.runLater(()->{
            anomalyLabel.setText("Everything is fine");
            anomalyLabel.setStyle("-fx-background-color: green;");
            ignore.setDisable(true);
        });

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visRegLine.axisSortingPolicyProperty().setValue(LineChart.SortingPolicy.NONE);
        regLine1.axisSortingPolicyProperty().setValue(LineChart.SortingPolicy.NONE);
        regLine2.axisSortingPolicyProperty().setValue(LineChart.SortingPolicy.NONE);
        pointForReg1 = new XYChart.Series<>();
        pointForReg2 = new XYChart.Series<>();
        pointForVisRegLine = new XYChart.Series<>();
        livePointForVisRegLine = new XYChart.Series<>();
        regLine1.getData().add(pointForReg1);
        regLine2.getData().add(pointForReg2);
        visRegLine.getData().add(pointForVisRegLine);
        visRegLine.getData().add(livePointForVisRegLine);
        list.setOnMouseClicked((e)->clickedItem((String) list.getSelectionModel().getSelectedItem()));
        lastUpdate = System.nanoTime();
        ignore.setOnAction((e)->{
            anomalyLabel.setText("Everything is fine");
            anomalyLabel.setStyle("-fx-background-color: green;");
            ignore.setDisable(true);
        });
    }
    private void addFeaturesToList(String[] features){
        Platform.runLater(()->{
            list.getItems().clear();
            list.getItems().addAll(features);
            list.requestFocus();
            clickedItem(features[0]);
        });
    }
    private void anomalyDetected(String anomaly){
        String[] tokens = anomaly.split("-");
        if(tokens[0].intern()==feature1.intern())
            Platform.runLater(()->{
                anomalyLabel.setText("anomaly "+ feature1);
                anomalyLabel.setStyle("-fx-background-color: red;");
                ignore.setDisable(false);
            });


    }
    private void setFeaturesData(HashMap<String,Float> FeaturesData){
        if((System.nanoTime()-lastUpdate)>updateRate){
            f1 = FeaturesData.get(feature1);
            f2 = FeaturesData.get(feature2);
            paintChart();
            lastUpdate = System.nanoTime();
        }
    }
    private void clickedItem(String feature){
        CorrelatedFeatures cf = null;
        this.feature1 = feature;
        cf = correlatedFeatures.get(feature1);
        this.feature2 =  cf.feature2;
        this.regLine = cf.lin_reg;
        this.regLine1.setTitle(feature1);
        this.regLine2.setTitle(feature2);
        pointForReg1.getData().clear();
        pointForReg2.getData().clear();
        livePointForVisRegLine.getData().clear();
        if(Math.abs(cf.correlation) >= 0.5 && Math.abs(cf.correlation) <= 0.95) {
            this.circle = cf.getCircle();
            paintCircle(cf.getPoints());
        } else if (cf.correlation>0.95)
            paintRegLine(cf.getPoints());
        else
            pointForVisRegLine.getData().clear();
        paintChart();
    }
    private void paintCircle(XYChart.Series points){
        Platform.runLater(()->{
            pointForVisRegLine.getData().clear();
            pointForVisRegLine.getData().addAll(points.getData());
        });
//        pointForVisRegLine.getNode().setStyle(" -fx-stroke:red; -fx-stroke: transparent ");
    }
    private void paintRegLine(XYChart.Series points){
        Platform.runLater(()->{
            pointForVisRegLine.getData().clear();
            pointForVisRegLine.getData().addAll(points.getData());
        });
    }
    private void paintChart(){
        XYChart.Data f1Point = new XYChart.Data(f1,this.regLine.f(f1));
        XYChart.Data f2Point = new XYChart.Data(f2,this.regLine.f(f2));
        System.out.println(f1+" "+this.regLine.f(f1));
        Platform.runLater(()->{
            pointForReg1.getData().add(f1Point);
            pointForReg2.getData().add(f2Point);
            if(feature1.intern()!=feature2.intern())
                livePointForVisRegLine.getData().add(f1Point);
        });

    }
}
