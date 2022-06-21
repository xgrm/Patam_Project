package view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.AnomalyDetection.CorrelatedFeatures;
import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Line;
import model.AnomalyDetection.utils.Point;
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
    String feature1,feature2;
    Line regLine;
    Circle circle;
    float f1,f2;

    XYChart.Series<Float,Float> pointForReg1,pointForReg2,pointForVisRegLine;

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
                list.getItems().addAll(command.getData().split(" "));
            });
        } else if (command.getCommandName().intern()=="correlatedFeatures") {
            this.correlatedFeatures = (HashMap<String, CorrelatedFeatures>) command.getObject();
        } else if (command.getCommandName().intern()=="agentData") {
            if(feature2!=null){
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
        pointForVisRegLine = new XYChart.Series<>();
        regLine1.getData().add(pointForReg1);
        regLine2.getData().add(pointForReg2);
        visRegLine.getData().add(pointForVisRegLine);
        list.setOnMouseClicked((e)->clickedItem(e));

    }
    private void clickedItem(MouseEvent e){
        pointForReg1.getData().clear();
        pointForReg2.getData().clear();
        pointForVisRegLine.getData().clear();
        CorrelatedFeatures cf = null;
        this.feature1 = (String) list.getSelectionModel().getSelectedItem();
        cf = correlatedFeatures.get(feature1);
        this.feature2 =  cf.feature2;
        this.regLine = cf.lin_reg;
        if(Math.abs(cf.correlation) >= 0.5 && Math.abs(cf.correlation) <= 0.95) {
            System.out.println("in cir");
            this.circle = cf.getCircle();
            paintCircle();
        } else if (cf.correlation>0.95) {
            paintRegLine();
        } else{
            this.feature2 =  null;
        }
        //paintChart();
    }

    private void paintCircle(){
        circle.radius=2;
        circle.center.x= -1;
        circle.center.y = -2;
        XYChart.Data data = new XYChart.Data(circle.center.x,circle.center.y);
        float newX = (float) xAxis.getDisplayPosition(circle.center.x);
        float newY = (float) yAxis.getDisplayPosition(circle.center.y);

        Point center = new Point(circle.center.x,circle.center.y);
        Point newCenter = new Point(newX,newY);
        System.out.println("the center is:"+center);
        System.out.println("the new center is:"+newCenter);
        Float[] ys = circle.getY(circle.center.x);

        Point point1 = new Point(center.x,ys[0]);

        float newPoint1x = (float) xAxis.getDisplayPosition(circle.center.x);
        float newPoint1y = (float) yAxis.getDisplayPosition(ys[0]);
        Point newPoint1 = new Point(newPoint1x,newPoint1y);
        System.out.println("the point1 is "+ point1);
        System.out.println("the new point1 is "+newPoint1);

        System.out.println("the orignal dis is: "+ center.distance(point1));
        System.out.println(newPoint1.x+" "+newPoint1.y);
        System.out.println("the new dis is: "+ newCenter.distance(newPoint1));

        Float[] xs = circle.getX(center.y);
        Point point2 = new Point(xs[0],center.y);

        float newPoint2x = (float) xAxis.getDisplayPosition(xs[0]);
        float newPoint2y = (float) yAxis.getDisplayPosition(circle.center.y);
        Point newPoint2 = new Point(newPoint2x,newPoint2y);
        System.out.println("the point2 is "+ point2);
        System.out.println("the new point2 is "+newPoint2);
        System.out.println("the orignal dis is: "+ center.distance(point2));

        System.out.println("the new dis is: "+ newCenter.distance(newPoint2));

//        System.out.println(circle.center.x/nx+" "+ys[0]/ny);
//        System.out.println(circle.center.x/x+" "+circle.center.y/y);
        javafx.scene.shape.Circle pointCircle = new javafx.scene.shape.Circle
                (xAxis.getDisplayPosition((Number) data.getXValue()),yAxis.getDisplayPosition((Number) data.getYValue()),circle.radius);
//        pointCircle.setFill(Color.rgb(255, 255, 255, 1));
//        pointCircle.setStrokeWidth(1);
        data.setNode(pointCircle);
        pointForVisRegLine.getData().add(data);
        pointForVisRegLine.getNode().setStyle(" -fx-stroke: transparent;  ");
    }
    private void paintRegLine(){

    }
    private void paintChart(){
        Platform.runLater(()->{
            this.regLine1.setTitle(feature1);
            pointForReg1.getData().add(new XYChart.Data(f1,this.regLine.f(f1)));
            if(feature2!=null){
                pointForReg2.getData().add(new XYChart.Data(f2,this.regLine.f(f2)));
                this.regLine2.setTitle(feature2);
                pointForVisRegLine.getData().add(new XYChart.Data(f1,this.regLine.f(f1)));
            }
        });
    }
}
