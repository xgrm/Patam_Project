package view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import model.AnomalyDetection.CorrelatedFeatures;
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
    String feature1,feature2;
    Line regLine;
    float f1,f2;

    XYChart.Series pointForReg1,pointForReg2;

    HashMap<String, CorrelatedFeatures> correlatedFeatures;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
    }

    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern()=="FeaturesList"){
            Platform.runLater(()->{
                list.getItems().clear();
                list.getItems().addAll(command.getData().split(","));
            });
        } else if (command.getCommandName().intern()=="correlatedFeatures") {
            this.correlatedFeatures = (HashMap<String, CorrelatedFeatures>) command.getObject();
        } else if (command.getCommandName().intern()=="agentData") {
            if(feature2!=null){
                System.out.println("agent d");
                f1 = command.getDataMap().get(feature1);
                f2 = command.getDataMap().get(feature2);
                paintChart();
            }
        } else if (command.getCommandName().intern()=="anomalyDetectet") {
            String[] tokens = command.getData().split("-");
            if((feature1!=null)&&tokens[0].intern()==feature1.intern())
                System.out.println("anomly "+ feature1);

        }
    }

    @Override
    public void onTabSelection() {
        super.onTabSelection();
        viewModel.exe(new SerializableCommand("getCorrelatedFeatures"," "));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pointForReg1 = new XYChart.Series<>();
        pointForReg2 = new XYChart.Series<>();
        regLine1.getData().add(pointForReg1);
        regLine2.getData().add(pointForReg2);
        list.setOnMouseClicked((e)->{
            pointForReg1.getData().clear();
            pointForReg2.getData().clear();
            CorrelatedFeatures cf = null;
            this.feature1 = (String) list.getSelectionModel().getSelectedItem();
            System.out.println(feature1);
            if((cf = correlatedFeatures.get(feature1)) != null){
                System.out.println("not");
                this.feature2 =  cf.feature2;
                this.regLine = cf.lin_reg;
            }else{
                feature2 = null;
            }
            paintChart();
        });
    }

    private void paintChart(){
        if(feature2!=null){
            Platform.runLater(()->{
                this.regLine1.setTitle(feature1);
                this.regLine2.setTitle(feature2);
                pointForReg1.getData().add(new XYChart.Data(String.valueOf(f1),this.regLine.f(f1)));
                pointForReg2.getData().add(new XYChart.Data(String.valueOf(f2),this.regLine.f(f2)));
            });
        }else{
            Platform.runLater(()->{
                this.regLine1.setTitle(feature1);
                this.regLine2.setTitle("no correlation feature");
            });
        }
    }
}
